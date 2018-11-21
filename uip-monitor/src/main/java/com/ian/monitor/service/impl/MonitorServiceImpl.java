package com.ian.monitor.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ian.monitor.dao.MonitorMapper;
import com.ian.monitor.entity.Monitor;
import com.ian.monitor.service.MonitorService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xhd
 * @since 2018-11-12
 */
@Service
public class MonitorServiceImpl extends ServiceImpl<MonitorMapper, Monitor> implements MonitorService {

}
