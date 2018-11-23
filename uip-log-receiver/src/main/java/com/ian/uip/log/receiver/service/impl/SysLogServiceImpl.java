package com.ian.uip.log.receiver.service.impl;

import com.ian.uip.core.model.SysLog;
import com.ian.uip.log.receiver.dao.SysLogMapper;
import com.ian.uip.log.receiver.service.SysLogService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ian
 * @since 2018-11-22
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

}
