package com.peak.training.exceptionhandle;

import com.peak.training.common.annotation.validation.errormessage.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//@RestControllerAdvice
public class MyExceptionHandler {

    @Autowired
    ErrorMessage errorMessage ;

    //@ExceptionHandler({MethodArgumentNotValidException.class, BlackColorException.class})
    @ExceptionHandler
    @ResponseBody
    //public ResponseEntity<List> processUnmergeException(final MethodArgumentNotValidException ex) {
    public ResponseEntity<List> processUnmergeException(final BindException ex){

    List list = ex.getBindingResult().getAllErrors().stream()
                .map(fieldError -> ErrorMessage.toLocale(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    //public ResponseEntity<List> processUnmergeException(final MethodArgumentNotValidException ex) {
    public ResponseEntity<List> customerException(final RuntimeException ex){

        String error = ErrorMessage.toLocale(ex.getMessage()) ;
        ex.printStackTrace() ;
        return new ResponseEntity<>((new ArrayList<String>(Arrays.asList(error))), HttpStatus.NOT_FOUND);
    }

}
