package com.ian.uip.sys.model;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.ian.uip.core.exception.CustomException;
import com.ian.uip.core.model.PageQO;
import com.ian.uip.core.model.ResultBean;
import com.ian.uip.core.validator.ValidatorUtils;
import com.ian.uip.core.validator.group.AddGroup;
import com.ian.uip.core.validator.group.UpdateGroup;
import com.ian.uip.sys.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class BaseController<S extends IService<T>, T> {

    @Autowired
    protected S baseService;

    @PostMapping
    public ResultBean insert(@RequestBody T t) {
        ValidatorUtils.validateEntity(t, AddGroup.class);
        return new ResultBean(baseService.insert(t));
    }

    @PostMapping("/batchAdd")
    public ResultBean insertBatch(@RequestBody List<T> list) {
        list.forEach(item -> ValidatorUtils.validateEntity(item, AddGroup.class));
        return new ResultBean(baseService.insertBatch(list));
    }

    @PutMapping
    public ResultBean update(@RequestBody T t) throws InvocationTargetException, IllegalAccessException {
        ValidatorUtils.validateEntity(t, UpdateGroup.class);
        return new ResultBean(baseService.updateById(t));
    }

    @DeleteMapping
    public ResultBean deleteBatch(@RequestBody List<String> ids) {
        if (null == ids || ids.size() == 0) {
            throw new CustomException("待删除数据的id不能为空");
        }
        return new ResultBean(baseService.deleteBatchIds(ids));
    }

    @GetMapping
    public ResultBean listByPage(T t, PageQO pageQO) {
        return new ResultBean(baseService.selectPage(PageUtils.pageQOToPage(pageQO), new EntityWrapper<>(t)));
    }

    @GetMapping("/getById")
    public ResultBean getById(@RequestParam("id") String id) {
        return new ResultBean(baseService.selectOne(new EntityWrapper<T>().eq("id", id)));
    }
}
