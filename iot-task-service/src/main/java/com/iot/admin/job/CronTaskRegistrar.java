package com.iot.admin.job;

import javax.annotation.Resource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务注册类
 * <p>
 * 定时任务注册类, 主要用于增加、删除定时任务
 * </p>
 * 
 * @author 星空流年
 * @date 2023/7/5
 */
@Component
@SuppressWarnings("all")
public class CronTaskRegistrar implements DisposableBean {

    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    @Resource
    private TaskScheduler taskScheduler;

    public void addCronTask(Runnable task, String cronExpression) {
        addCronTask(new CronTask(task, cronExpression));
    }

    public void addCronTask(CronTask cronTask) {
        if (Objects.nonNull(cronTask)) {
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)) {
                removeCronTask(task);
            }
            this.scheduledTasks.put(task, scheduleCronTask(cronTask));
        }
    }

    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (Objects.nonNull(scheduledTask)) {
            scheduledTask.cancel();
        }
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        return scheduledTask;
    }

    @Override
    public void destroy() {
        this.scheduledTasks.values().forEach(ScheduledTask::cancel);
        this.scheduledTasks.clear();
    }
}