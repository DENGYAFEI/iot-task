package com.iot.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iot.common.model.entity.task.GatherJob;
import com.iot.task.mapper.GatherJobMapper;
import com.iot.task.service.GatherJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* @author 20379
* @description 针对表【gather_job(采集任务表)】的数据库操作Service实现
* @createDate 2024-12-26 20:48:33
*/
@Slf4j
@Service
public class GatherJobServiceImpl extends ServiceImpl<GatherJobMapper, GatherJob>
    implements GatherJobService {
}




