package edu.nnuzb.jiguo.advice;

import edu.nnuzb.jiguo.entity.po.JsonData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public JsonData errorHandler(Exception ex){

        return JsonData.fail(555 ,ex.getMessage());
    }

}
