package com.dnd.actuator.component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoScheduledTask {

    @Scheduled(fixedRate = 60_000)
    public void runEveryMinute() {
        System.out.println("Scheduled task ran");
    }
}