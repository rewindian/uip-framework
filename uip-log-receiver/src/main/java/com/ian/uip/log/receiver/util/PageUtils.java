package com.ian.uip.log.receiver.util;

import com.baomidou.mybatisplus.plugins.Page;
import com.ian.uip.core.model.PageQO;
import com.ian.uip.core.util.StrUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Xu Haidong
 * @date 2018/3/29
 */
public final class PageUtils {

    private PageUtils() {
    }

    public static <T> Page<T> pageQOToPage(PageQO pageQO) {
        return formatPage(pageQO.getCurrent(), pageQO.getSize(), pageQO.getSort(), pageQO.getOrder(), true);
    }

    public static <T> Page<T> pageQOToPageNoChange(PageQO pageQO) {
        return formatPage(pageQO.getCurrent(), pageQO.getSize(), pageQO.getSort(), pageQO.getOrder(), false);
    }

    private static <T> Page<T> formatPage(int page, int rows, String sort, String order, boolean isCamel2Underline) {
        Page<T> plusPage;
        if (!StringUtils.isEmpty(sort)) {
            List<String> list;
            if (isCamel2Underline) {
                //驼峰转下划线
                list = Arrays.stream(sort.split(",")).map(StrUtils::camel2UnderlineLower).collect(Collectors.toList());
            } else {
                list = Arrays.stream(sort.split(",")).collect(Collectors.toList());
            }
            String sorts = StringUtils.collectionToDelimitedString(list, ",");
            plusPage = new Page<>(page, rows, sorts, PageQO.DEFAULT_ORDER.equals(order));
        } else {
            plusPage = new Page<>(page, rows);
        }
        return plusPage;
    }
}
