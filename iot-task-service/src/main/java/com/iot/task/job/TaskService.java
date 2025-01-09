package com.iot.task.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import javax.annotation.Resource;

import com.iot.common.enums.ScheduleJobEnum;
import com.iot.common.model.entity.task.GatherJob;
import com.iot.task.service.GatherJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务动态管理具体实现工具类
 *
 * @author 星空流年
 * @date 2023/7/5
 */
@Component
public class TaskService {

    @Resource
    private CronTaskRegistrar cronTaskRegistrar;

    @Resource
    private GatherJobService gatherJobService;

    /**
     * 添加定时任务
     *
     * @param scheduleJob 定时任务实体类
     * @return boolean
     */
    public GatherJob insertTaskJob(GatherJob scheduleJob) {

        boolean insert = gatherJobService.save(scheduleJob);
        if (!insert) {
            return null;
        }
        // 添加成功, 并且状态是启用, 则直接放入任务器
        if (scheduleJob.getStatus().equals(ScheduleJobEnum.ENABLE.getCode())) {
            SchedulingRunnable task = new SchedulingRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getMethodParams(), scheduleJob.getId());
            cronTaskRegistrar.addCronTask(task, scheduleJob.getCronExpression());
        }
        return scheduleJob;
    }

    /**
     * 更新定时任务
     *
     * @param scheduleJob 定时任务实体类
     * @return boolean
     */
    public boolean updateTaskJob(GatherJob scheduleJob) {
        // 查询修改前任务
        GatherJob existedSysJob = new GatherJob();
        LambdaQueryWrapper<GatherJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GatherJob::getId, scheduleJob.getId());
        existedSysJob = gatherJobService.getOne(queryWrapper);

        // 修改任务
        LambdaUpdateWrapper<GatherJob> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GatherJob::getId, scheduleJob.getId());
        boolean update = gatherJobService.update(updateWrapper);
        if (!update) {
            return false;
        }
        // 修改成功, 则先删除任务器中的任务, 并重新添加
        SchedulingRunnable preTask = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams(), existedSysJob.getId());
        cronTaskRegistrar.removeCronTask(preTask);
        // 如果修改后的任务状态是启用, 就加入任务器
        if (scheduleJob.getStatus().equals(ScheduleJobEnum.ENABLE.getCode())) {
            SchedulingRunnable task = new SchedulingRunnable(scheduleJob.getBeanName(), scheduleJob.getMethodName(), scheduleJob.getMethodParams(), scheduleJob.getId());
            cronTaskRegistrar.addCronTask(task, scheduleJob.getCronExpression());
        }
        return true;
    }

    /**
     * 删除定时任务
     *
     * @param jobId 定时任务id
     * @return boolean
     */
    public boolean deleteTaskJob(String jobId) {
        // 先查询要删除的任务信息
        GatherJob existedJob = new GatherJob();
        LambdaQueryWrapper<GatherJob> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GatherJob::getId, jobId);
        existedJob = gatherJobService.getOne(queryWrapper);

        // 删除
        boolean delete = gatherJobService.remove(queryWrapper);
        if (!delete) {
            return false;
        }
        // 删除成功, 并删除定时任务器中的对应任务
        SchedulingRunnable task = new SchedulingRunnable(existedJob.getBeanName(), existedJob.getMethodName(), existedJob.getMethodParams(), jobId);
        cronTaskRegistrar.removeCronTask(task);
        return true;
    }

    /**
     * 停止/启动定时任务
     *
     * @param jobId     定时任务id
     * @param jobStatus 定时任务状态
     * @return boolean
     */
    public boolean changeStatus(String jobId, Integer jobStatus) {
        // 修改任务状态
        GatherJob scheduleSetting = new GatherJob();
        scheduleSetting.setStatus(jobStatus);
        boolean update = gatherJobService.update(new LambdaUpdateWrapper<GatherJob>().eq(GatherJob::getId, jobId));
        if (!update) {
            return false;
        }
        // 查询修改后的任务信息
        GatherJob existedJob = new GatherJob();
        existedJob = gatherJobService.getOne(new LambdaQueryWrapper<GatherJob>().eq(GatherJob::getId, jobId));
        // 如果状态是启用, 则添加任务
        SchedulingRunnable task = new SchedulingRunnable(existedJob.getBeanName(), existedJob.getMethodName(), existedJob.getMethodParams(), jobId);
        if (existedJob.getStatus().equals(ScheduleJobEnum.ENABLE.getCode())) {
            cronTaskRegistrar.addCronTask(task, existedJob.getCronExpression());
        } else {
            // 反之, 则删除任务
            cronTaskRegistrar.removeCronTask(task);
        }
        return true;
    }
}