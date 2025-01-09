package com.iot.task;

import com.iot.common.base.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class TaskServiceApplication {

    public static void main( String[] args ) {

        ApplicationContext applicationContext = SpringApplication.run(TaskServiceApplication.class, args);
        SpringContextUtils.setApplicationContext(applicationContext);
        log.info( "\n    ____      __     ____        __       \n" +
            "   /  _/___  / /_   / __ \\____ _/ /_____ _\n" +
            "   / // __ \\/ __/  / / / / __ `/ __/ __ `/\n" +
            " _/ // /_/ / /_   / /_/ / /_/ / /_/ /_/ / \n" +
            "/___/\\____/\\__/  /_____/\\__,_/\\__/\\__,_/  \n" +
            "\n" );
    }

}
