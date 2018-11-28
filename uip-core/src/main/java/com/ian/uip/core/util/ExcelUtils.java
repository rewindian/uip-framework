package com.ian.uip.core.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import lombok.Cleanup;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class ExcelUtils {

    private ExcelUtils() {

    }

    public static void exportExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                   Class<? extends BaseRowModel> clazz) throws IOException {
        String fileName = URLEncoder.encode(new String((new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
                .getBytes(), "UTF-8"), "UTF-8");
        exportExcel(response, list, clazz, fileName);
    }

    public static void exportExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                   Class<? extends BaseRowModel> clazz, String fileName) throws IOException {
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        @Cleanup
        ServletOutputStream out = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcelFactory.getWriter(out);
        Sheet sheet = new Sheet(1, 1, clazz);
        sheet.setAutoWidth(true);
        excelWriter.write(list, sheet);
        excelWriter.finish();
        out.flush();
    }

    /**
     * 导入小于1000条数据
     * @param in
     * @param clazz
     * @return
     */
    public static List<?> importExcel(InputStream in, Class<? extends BaseRowModel> clazz) {
        return EasyExcelFactory.read(in, new Sheet(1, 1, clazz));
    }

}
