package com.ian.uip.core.validator;

import com.ian.uip.core.exception.CustomException;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * 数据校验
 */
@UtilityClass
public class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new CustomException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CustomException(message);
        }
    }
}
