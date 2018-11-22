package com.ian.uip.core.exception;

import com.ian.uip.core.annotation.SysLogIgnore;
import com.ian.uip.core.model.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 自定义异常
     */
    @ExceptionHandler(CustomException.class)
    @SysLogIgnore
    public ResultBean handleRRException(CustomException e) {
        return new ResultBean(e);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @SysLogIgnore
    public ResultBean handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new ResultBean(ResultBean.FAIL, "数据库已经存在该记录");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @SysLogIgnore
    public ResultBean handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        if (null != e.getMessage() && e.getMessage().contains("Data too long for column")) {
            String column = e.getMessage().substring(
                    e.getMessage().lastIndexOf("Data too long for column '") + 26
                    , e.getMessage().lastIndexOf("' at row"));
            return new ResultBean(ResultBean.FAIL, column + "字段过长");
        } else {
            return new ResultBean(e);
        }
    }

    @ExceptionHandler(AuthException.class)
    public ResultBean handleAuthorizationException(AuthException e) {
        log.error(e.getMessage(), e);
        return new ResultBean(ResultBean.NEED_LOGIN, "token缺失或过期，请重新登录");
    }

    @ExceptionHandler(Exception.class)
    @SysLogIgnore
    public ResultBean handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResultBean(e);
    }
}
