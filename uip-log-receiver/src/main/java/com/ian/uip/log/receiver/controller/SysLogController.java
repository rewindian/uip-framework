package com.ian.uip.log.receiver.controller;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ian.uip.core.exception.CustomException;
import com.ian.uip.core.model.PageQO;
import com.ian.uip.core.model.ResultBean;
import com.ian.uip.core.model.SysLog;
import com.ian.uip.core.util.ExcelUtils;
import com.ian.uip.log.receiver.model.SysLogEO;
import com.ian.uip.log.receiver.service.SysLogService;
import com.ian.uip.log.receiver.util.PageUtils;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ian
 * @since 2018-11-22
 */
@RestController
@RequestMapping("/sys/log")
@Slf4j
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @GetMapping("/excel/export")
    public void excelExport(HttpServletResponse response) {
        try {
            List<SysLog> list = sysLogService.selectList(new EntityWrapper<SysLog>().orderBy("create_time"));
            List<SysLogEO> eoList = list.stream().map(sysLog -> {
                SysLogEO sysLogEO = new SysLogEO();
                BeanUtils.copyProperties(sysLog, sysLogEO);
                return sysLogEO;
            }).collect(Collectors.toList());
            ExcelUtils.exportExcel(response, eoList, SysLogEO.class);
        } catch (Exception e) {
            log.error("excel导出异常", e);
        }
    }

    @GetMapping
    public ResultBean listByPage(SysLog sysLog, PageQO pageQO) {
        if (StringUtils.isEmpty(pageQO.getSort())) {
            pageQO.setSort("createTime");
            pageQO.setOrder("desc");
        }
        return new ResultBean(sysLogService.selectPage(PageUtils.pageQOToPage(pageQO), new EntityWrapper<>(sysLog)));
    }

    @PostMapping("/excel/import")
    public ResultBean importExcel(@RequestParam(value = "excelFile") MultipartFile file) throws IOException {
        //判断文件是否为空
        if (file == null) {
            return new ResultBean(new CustomException("上传文件为空"));
        }
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size = file.getSize();
        if (name == null || size == 0) {
            return new ResultBean(new CustomException("上传文件或文件名为空"));
        }
        @Cleanup
        InputStream in = file.getInputStream();
        return new ResultBean(ExcelUtils.importExcel(in, SysLogEO.class));
    }

    @PostMapping("/excel/importLarge")
    public ResultBean importLargeExcel(@RequestParam(value = "excelFile") MultipartFile file) throws IOException {
        //判断文件是否为空
        if (file == null) {
            return new ResultBean(new CustomException("上传文件为空"));
        }
        //获取文件名
        String name = file.getOriginalFilename();
        //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
        long size = file.getSize();
        if (name == null || size == 0) {
            return new ResultBean(new CustomException("上传文件或文件名为空"));
        }
        @Cleanup
        InputStream in = file.getInputStream();
        EasyExcelFactory.readBySax(in, new Sheet(1, 1, SysLogEO.class), new AnalysisEventListener() {
            List<SysLog> logList = new ArrayList<>();

            @Override
            public void invoke(Object object, AnalysisContext context) {
                if (logList.size() < 30) {
                    SysLog sysLog = new SysLog();
                    BeanUtils.copyProperties(object, sysLog);
                    logList.add(sysLog);
                } else {
                    add();
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                if (logList.size() > 0) {
                    add();
                }
            }

            private void add() {
                logList.forEach(sysLog -> log.debug(sysLog.toString()));
                logList.clear();
            }
        });
        return new ResultBean("");
    }
}
