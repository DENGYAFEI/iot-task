package com.iot.admin.job;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

/**
 * 定时任务包装类
 * <p>
 * ScheduledFuture是ScheduledExecutorService定时任务线程池的执行结果
 * </p>
 *
 * @author 星空流年
 * @date 2023/7/5
 */
@SuppressWarnings("all")
public final class ScheduledTask {

    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> scheduledFuture = this.future;
        if (Objects.nonNull(scheduledFuture)) {
            scheduledFuture.cancel(true);
        }
    }
}