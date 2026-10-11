package com.dnd.actuator.controller;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DemoController {

    private final Counter greetingCounter;

    public DemoController(MeterRegistry meterRegistry) {
        this.greetingCounter = Counter.builder("demo.greetings")
                .description("Number of greeting requests")
                .register(meterRegistry);
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        greetingCounter.increment();
        return Map.of("message", "Hello from Spring Boot");
    }
}
