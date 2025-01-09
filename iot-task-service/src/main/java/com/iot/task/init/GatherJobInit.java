package com.iot.task.init;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iot.common.enums.ScheduleJobEnum;
import com.iot.common.model.entity.task.GatherJob;
import com.iot.task.job.CronTaskRegistrar;
import com.iot.task.job.SchedulingRunnable;
import com.iot.task.job.TaskService;
import com.iot.task.service.GatherJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 初始化 GatherJob
 */
@Slf4j
@Component
public class GatherJobInit implements ApplicationRunner {

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Resource
    private GatherJobService gatherJobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化加载数据库中状态为启用的定时任务
        List<GatherJob> jobList = gatherJobService.list(new LambdaQueryWrapper<GatherJob>().eq(GatherJob::getStatus, ScheduleJobEnum.ENABLE.getCode()));
        if (CollUtil.isNotEmpty(jobList)) {
            jobList.forEach(job -> {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams(), job.getId());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            });
            log.info("~~~~~~~~~~~~~~~~~~~~~ 定时任务初始化完成 ~~~~~~~~~~~~~~~~~~~~~");
        }
    }
}
