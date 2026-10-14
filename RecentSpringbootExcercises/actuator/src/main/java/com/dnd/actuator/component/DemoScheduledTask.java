package com.dnd.actuator.component;
import ch.qos.logback.core.CoreConstants;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DemoScheduledTask {

    @Scheduled(fixedRate = 60_000)
    public void runEveryMinute() {
        System.out.print(LocalDateTime.now());
        System.out.println("Scheduled task ran");
    }
}