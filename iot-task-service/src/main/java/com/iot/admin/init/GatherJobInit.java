package com.iot.admin.init;

import com.iot.common.model.entity.task.GatherJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化 GatherJob
 */
@Slf4j
@Component
public class GatherJobInit implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<GatherJob> gatherJobList = new ArrayList<>();

    }
}
