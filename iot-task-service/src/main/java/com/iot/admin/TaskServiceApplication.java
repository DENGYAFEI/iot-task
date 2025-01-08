package com.iot.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TaskServiceApplication {

    public static void main( String[] args ) {
        SpringApplication.run( TaskServiceApplication.class, args );
        log.info( "\n    ____      __     ____        __       \n" +
            "   /  _/___  / /_   / __ \\____ _/ /_____ _\n" +
            "   / // __ \\/ __/  / / / / __ `/ __/ __ `/\n" +
            " _/ // /_/ / /_   / /_/ / /_/ / /_/ /_/ / \n" +
            "/___/\\____/\\__/  /_____/\\__,_/\\__/\\__,_/  \n" +
            "\n" );
    }

}
