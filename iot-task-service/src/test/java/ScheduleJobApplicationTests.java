import javax.annotation.Resource;

import cn.hutool.core.util.ObjectUtil;
import com.iot.common.enums.ScheduleJobEnum;
import com.iot.common.model.entity.task.GatherJob;
import com.iot.task.TaskServiceApplication;
import com.iot.task.job.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@SpringBootTest(classes = TaskServiceApplication.class)
public class ScheduleJobApplicationTests {
    
    @Resource
    private TaskService taskService;
    
    @Test
    public void testInsertTask() {
        GatherJob scheduleJob = new GatherJob();
        // 此处beanName, methodName对应定时任务执行类中定义的beanName、方法名
        scheduleJob.setBeanName("jobTaskTest");
        scheduleJob.setMethodName("upsertTask");
        // 方法参数由于定时任务Runnable接口包装类中定义为字符串类型, 如果为其他类型，注意转换
        scheduleJob.setMethodParams("params");
        scheduleJob.setStatus(ScheduleJobEnum.ENABLE.getCode());
        String cron = "*/30 * * * * ?";
        scheduleJob.setCronExpression(cron);
        scheduleJob.setRemark("定时任务新增");

        GatherJob scheduleTask = taskService.insertTaskJob(scheduleJob);
        if (ObjectUtil.isNull(scheduleTask)) {
            log.error("定时任务新增失败");
        }
    }
    
    @Test
    public void testUpdateTask() {
        GatherJob scheduleJob = new GatherJob();
        scheduleJob.setId("1");
        // 此处beanName, methodName对应定时任务执行类中定义的beanName、方法名
        scheduleJob.setBeanName("jobTaskTest");
        scheduleJob.setMethodName("upsertTask");
        // 方法参数由于定时任务Runnable接口包装类中定义为字符串类型, 如果为其他类型，注意转换
        scheduleJob.setMethodParams("params");
        scheduleJob.setStatus(ScheduleJobEnum.ENABLE.getCode());
        String cron = "*/60 * * * * ?";
        scheduleJob.setCronExpression(cron);
        scheduleJob.setRemark("定时任务更新");

        boolean updateFlag = taskService.updateTaskJob(scheduleJob);
        if (!updateFlag) {
            log.error("定时任务更新失败");
        }
    }
    
    @Test
    public void testChangeTaskStatus() {
        boolean changeFlag = taskService.changeStatus("1", 0);
        if (!changeFlag) {
            log.error("定时任务状态更新失败");
        }
    }
    
    @Test
    public void testDeleteTask() {
        boolean deleteFlag = taskService.deleteTaskJob("1");
        if (!deleteFlag) {
            log.error("定时任务删除失败");
        }
    }
}