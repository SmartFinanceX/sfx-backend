package org.sfx.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.sfx.api.config.SfxResponseCode;
import org.sfx.api.domain.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Component
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseResult globalExceptionHandler(HttpServletRequest req, Exception e){
        log.error("发生业务异常！原因是：{}",e.getMessage());
        return new ResponseResult(SfxResponseCode.UNKNOWN_ERROR,e.getMessage(),null);
    }
}
