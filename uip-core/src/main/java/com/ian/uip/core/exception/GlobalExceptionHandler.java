package com.ian.uip.core.exception;

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
    public ResultBean handleRRException(CustomException e) {
        return new ResultBean(e);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultBean handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new ResultBean(ResultBean.FAIL, "数据库已经存在该记录");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
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

//    @ExceptionHandler(AuthorizationException.class)
//    public R handleAuthorizationException(AuthorizationException e){
//        logger.error(e.getMessage(), e);
//        return R.error("没有权限，请联系管理员授权");
//    }

    @ExceptionHandler(Exception.class)
    public ResultBean handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResultBean(e);
    }
}
