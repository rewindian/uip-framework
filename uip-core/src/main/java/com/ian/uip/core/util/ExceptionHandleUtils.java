package com.ian.uip.core.util;

import com.ian.uip.core.exception.AuthException;
import com.ian.uip.core.exception.CustomException;
import com.ian.uip.core.model.ResultBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public final class ExceptionHandleUtils {

    private ExceptionHandleUtils() {

    }

    public static ResultBean handle(Throwable e) {
        if (e instanceof CustomException) {
            return new ResultBean(e);
        } else if (e instanceof AuthException) {
            return new ResultBean(ResultBean.NEED_LOGIN, "token缺失或过期，请重新登录");
        } else if (e instanceof DuplicateKeyException) {
            return new ResultBean(ResultBean.FAIL, "数据库已经存在该记录");
        } else if (e instanceof DataIntegrityViolationException) {
            if (null != e.getMessage() && e.getMessage().contains("Data too long for column")) {
                String column = e.getMessage().substring(
                        e.getMessage().lastIndexOf("Data too long for column '") + 26
                        , e.getMessage().lastIndexOf("' at row"));
                return new ResultBean(ResultBean.FAIL, column + "字段过长");
            } else {
                return new ResultBean(e);
            }
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            if (!violations.isEmpty()) {
                StringBuilder msg = new StringBuilder();
                for (ConstraintViolation<?> constraint : violations) {
                    msg.append(constraint.getMessage()).append("<br>");
                }
                return new ResultBean(ResultBean.FAIL, msg.toString());
            } else {
                return new ResultBean(e);
            }
        } else {
            return new ResultBean(e);
        }
    }
}
