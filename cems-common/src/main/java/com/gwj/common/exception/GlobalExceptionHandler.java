package com.gwj.common.exception;

import com.gwj.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j//输出异常信息到文件加此项注解
public class GlobalExceptionHandler {
    //处理全局异常
    @ExceptionHandler(Exception.class)
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("服务端异常，请稍后再试");
    }

    //自定义异常
    @ExceptionHandler(MyException.class)
    public R error(MyException e){
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
