package com.iot.task.job.execute;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时任务类
 *
 * @author 星空流年
 * @date 2023/7/5
 */
@Slf4j
@Component("jobTaskTest")
public class JobTask {

    /**
     * 此处为需要执行定时任务的方法, 可以根据需求自行添加对应的定时任务方法
     */
    public void upsertTask(String params) {
        // ...
        log.info("定时任务执行啦...");
    }
}