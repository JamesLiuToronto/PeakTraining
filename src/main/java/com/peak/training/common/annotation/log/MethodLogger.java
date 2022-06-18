package com.peak.training.common.annotation.log;

import com.peak.training.common.annotation.log.logclient.TransactionLogAdapter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Slf4j
@Aspect
@Component
@Conditional(MethodLoggingCondition.class)
public class MethodLogger {

    static final String REQUST = "REQUEST" ;
    static final String RESPONSE = "RESPONSE" ;
    static final String UPDATE_USERID_NAME = "updateUserId" ;

    @Value("${transactionlog.enable}")
    private String logEnable;

    @Autowired
    TransactionLogAdapter logClient ;

    @Around("@annotation(LogMethodData)")
    @SneakyThrows
    public Object logArroundExec(ProceedingJoinPoint pjp) {
        List<String> list = LogUtility.constructLogMsg(pjp) ;
        String uuid = UUID.randomUUID().toString() ;
        Integer updateAccountId = (Integer) getParameterByName(pjp, UPDATE_USERID_NAME);
        log.info("method {} request Parameter {}", list.get(0), list.get(1));


        persistTransactionLog(uuid,
                list.get(0),
                list.get(1),
                REQUST,
                updateAccountId) ;
        Object proceed = pjp.proceed();
        persistTransactionLog(uuid,
                list.get(0),
                proceed.toString(),
                RESPONSE,
                updateAccountId) ;
        return proceed;
    }

    private void persistTransactionLog(String uuid,
                                      String typeCode,
                                      String message,
                                      String statusCode,
                                      int updateUserId) {
        boolean enable = Boolean.parseBoolean(logEnable);
        log.info("log.enabled=" + enable);
        if (!enable) return ;
        log.info("startlog");
        String ret = logClient.persistTransactionLog(uuid, typeCode, message, statusCode, updateUserId) ;
        log.info("finishlog=" + ret);
    }

    public Object getParameterByName(ProceedingJoinPoint proceedingJoinPoint, String parameterName) {
        MethodSignature methodSig = (MethodSignature) proceedingJoinPoint.getSignature();
        Object[] args = proceedingJoinPoint.getArgs();
        String[] parametersName = methodSig.getParameterNames();

        int idx = Arrays.asList(parametersName).indexOf(parameterName);

        if(args.length > idx) { // parameter exist
            return args[idx];
        } // otherwise your parameter does not exist by given name
        return null;

    }
/*
    @Around("@annotation(LogMethodData)")
    @SneakyThrows
    public Object logArroundExec(ProceedingJoinPoint pjp) {
        List<String> list = LogUtility.constructLogMsg(pjp) ;
        log.info("method {} request Parameter {}", list.get(0), list.get(1));

        //log.info("method before {}", constructLogMsg(pjp));
        long start = System.currentTimeMillis();
        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info("method {} response ({} ms) with result: {}", list.get(0), (end-start),  proceed.toString());
        return proceed;
    }



    @AfterThrowing
    public void logAfterException(JoinPoint jp, Exception e) {
        log.error("Method Exception during: {} with ex: {}", constructLogMsg(jp),  e.toString());
    }

    private String constructLogMsg(JoinPoint jp) {
        var args = Arrays.asList(jp.getArgs()).stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        var sb = new StringBuilder("@");
        sb.append(method.getName());
        sb.append(":");
        sb.append(args);
        return sb.toString();
    }

     */
}