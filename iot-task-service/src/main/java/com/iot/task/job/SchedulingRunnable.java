package com.iot.task.job;

import cn.hutool.extra.spring.SpringUtil;
import com.iot.common.base.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Runnable接口实现类
 * 被定时任务线程池调用, 用来执行指定bean里面的方法
 *
 * @author 星空流年
 * @date 2023/7/5
 */
@Slf4j
@SuppressWarnings("all")
public class SchedulingRunnable implements Runnable {

    private final String beanName;

    private final String methodName;

    private final String params;

    private final String jobId;

    public SchedulingRunnable(String beanName, String methodName, String params, String jobId) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
        this.jobId = jobId;
    }

    @Override
    public void run() {
        log.info("定时任务开始执行 - bean: {}, 方法: {}, 参数: {}, 任务ID: {}", beanName, methodName, params, jobId);
        long startTime = System.currentTimeMillis();
        try {
            Object target = SpringUtil.getBean(beanName);

            Method method;
            if (StringUtils.isNotEmpty(params)) {
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            } else {
                method = target.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotEmpty(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception ex) {
            log.error("定时任务执行异常 - bean: {}, 方法: {}, 参数: {}, 任务ID: {}", beanName, methodName, params, jobId, ex);
        }
        long times = System.currentTimeMillis() - startTime;
        log.info("定时任务执行结束 - bean: {}, 方法: {}, 参数: {}, 任务ID: {}, 耗时: {}毫秒", beanName, methodName, params, jobId, times);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (Objects.isNull(obj) || getClass() != obj.getClass()) {
            return false;
        }

        SchedulingRunnable that = (SchedulingRunnable) obj;
        if (Objects.isNull(params)) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params) &&
                jobId.equals(that.jobId);
    }

    @Override
    public int hashCode() {
        if (Objects.isNull(params)) {
            return Objects.hash(beanName, methodName, jobId);
        }
        return Objects.hash(beanName, methodName, params, jobId);
    }
}