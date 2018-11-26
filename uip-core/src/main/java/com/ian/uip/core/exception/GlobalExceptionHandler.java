package com.ian.uip.core.exception;

import com.ian.uip.core.annotation.SysLogIgnore;
import com.ian.uip.core.model.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * 与SysLogAOP冲突 故放弃使用 可以在项目自行继承该类实现全局异常处理
 */
//@RestControllerAdvice
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
    @SysLogIgnore
    public ResultBean handleAuthorizationException(AuthException e) {
        log.error(e.getMessage(), e);
        return new ResultBean(ResultBean.NEED_LOGIN, "token缺失或过期，请重新登录");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @SysLogIgnore
    public ResultBean handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        if (!violations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<?> constraint : violations) {
                msg.append(constraint.getMessage()).append("<br>");
            }
            return new ResultBean(ResultBean.FAIL, msg.toString());
        } else {
            return new ResultBean(e);
        }
    }

    @ExceptionHandler(Exception.class)
    @SysLogIgnore
    public ResultBean handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResultBean(e);
    }
}
