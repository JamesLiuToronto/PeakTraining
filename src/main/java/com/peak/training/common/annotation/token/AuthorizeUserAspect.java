package com.peak.training.common.annotation.token;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import com.peak.training.common.annotation.token.AuthorizeCondition;
import com.peak.training.common.annotation.token.AuthorizeException;
import com.peak.training.common.annotation.token.AuthorizeUser;
import com.peak.training.common.annotation.token.model.CurrentUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
@Conditional(AuthorizeCondition.class)
public class AuthorizeUserAspect {

    @Value("${peak.security.updateuserid_match_check}")
    String checkUpdateuUerIdMatch ;


    //String tokenName ;
    /*public AuthorizeUserAspect(String tokenName){
        this.tokenName = tokenName ;
    }*/

    static String AUTH_ERROR = "AUTHORIZE.INVALID";
    @Around("@annotation(authorizeUser)")
    public Object authorize(ProceedingJoinPoint joinPoint, com.peak.training.common.annotation.token.AuthorizeUser authorizeUser) throws Throwable {
        Object[] args = joinPoint.getArgs() ;
        //BEFORE METHOD EXECUTION

        CurrentUser user = getCurrentUser();

        checkAccessRight(joinPoint, authorizeUser, user);

        //This is where ACTUAL METHOD will get invoke
        Object result = joinPoint.proceed();

        // AFTER METHOD EXECUTION
        //System.out.println(result);
        return result;
    }

    private CurrentUser getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (CurrentUser) auth.getPrincipal() ;
    }

    private void checkAccessRight(ProceedingJoinPoint joinPoint, AuthorizeUser authorizeUser, CurrentUser user){

        List<String> requiredList = Arrays.stream(authorizeUser.requiredRoles().split(","))
                .map(String::new)
                .collect(Collectors.toList());
        List<String> roleList = getUserRoleList(user) ;
        log.info("requiredList=" + requiredList);
        log.info("roleList=" + roleList);

        checkUpdateUserId(joinPoint, user) ;


        if (haveNormalRole(requiredList, roleList)) return ;

        if ((haveSelfRole(requiredList))&&(checkSelfRoleFromInput(joinPoint)))
            return ;
        throw new com.peak.training.common.annotation.token.AuthorizeException(AUTH_ERROR);
    }

    private List<String> getUserRoleList(CurrentUser user){
        return Arrays.stream(user.getRoles().split(","))
                .map(String::new)
                .collect(Collectors.toList());
    }

    private boolean haveNormalRole(List<String> requiredList, List<String> roleList){
        for (String role:requiredList){
            String find = roleList.stream().filter(x-> x.equalsIgnoreCase(role))
                    .findAny().orElse(null) ;
            if (find != null) return true ;
        }
        return false ;
    }

    static final String SELF_ROLE = "SELF" ;
    private boolean haveSelfRole(List<String> requiredList){
        String find = requiredList.stream().filter(x-> x.equalsIgnoreCase(SELF_ROLE))
                .findAny().orElse(null) ;
        if (find == null) return false;
        return true ;
    }

    static final String INPUT_USERID_NAME = "userAccountId" ;
    static final String UPDATE_USERID_NAME = "updateUserId" ;
    private boolean checkSelfRoleFromInput(ProceedingJoinPoint joinPoint){
        Integer userAccountId = (Integer) getParameterByName(joinPoint, INPUT_USERID_NAME);
        Integer updateAccountId = (Integer) getParameterByName(joinPoint, UPDATE_USERID_NAME);
        log.info("userAccountId=" + userAccountId);
        log.info("updateAccountId=" + updateAccountId);
        if (userAccountId == updateAccountId)
            return true ;
        return false ;
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

    private void checkUpdateUserId(ProceedingJoinPoint joinPoint, CurrentUser user){
        if (!Boolean.valueOf(checkUpdateuUerIdMatch)) return ;
        Integer updateAccountId = (Integer) getParameterByName(joinPoint, UPDATE_USERID_NAME);
        if (updateAccountId == null) return ;
        Integer userId = Integer.valueOf(user.getLastName()) ;
        if (userId.intValue() == updateAccountId.intValue())
            return ;

        throw new AuthorizeException("update-userid.validation");

    }

    /* old implementation

    @Autowired
    JWTUtility utility ;

    String tokenName ;
    public AuthorizeUserAspect(String tokenName){
        this.tokenName = tokenName ;
    }

    static String AUTH_ERROR = "AUTHORIZE.INVALID";
    @Around("@annotation(authorizeUser)")
    public Object authorize(ProceedingJoinPoint joinPoint, AuthorizeUser authorizeUser) throws Throwable {
        Object[] args = joinPoint.getArgs() ;
        //BEFORE METHOD EXECUTION
        String token = (String) getParameterByName(joinPoint, tokenName);
        validateToken(token, authorizeUser.pageKey()) ;

        //This is where ACTUAL METHOD will get invoke
        Object result = joinPoint.proceed();

        // AFTER METHOD EXECUTION
        //System.out.println(result);
        return result;
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

    private void validateToken(String token, String methodCode) throws TokenExpiredException {
        //if ((token == null)||(token.equalsIgnoreCase("deny")))
        //    throw new AuthorizeException(AUTH_ERROR) ;
        UserDTO dto = utility.validatetoken(token) ;
        checkAccessRight(dto.getMethodList(), methodCode);
    }

    private void checkAccessRight(String acllist, String methodCode){
        String[] list = acllist.split(",");
        if (list == null)
            throw new AppNoAccessException(" User does not have the access right!");
        String method = Arrays.stream(list).filter(x-> x.equalsIgnoreCase(methodCode))
                .findAny().orElse(null) ;
        if (method == null)
            throw new AppNoAccessException(" User does not have the access right!");
    }

     */
}
