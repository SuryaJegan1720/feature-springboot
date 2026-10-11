package com.dnd.actuator.component;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Endpoint(id = "greeting")
public class GreetingActuatorEndpoint {

    @ReadOperation
    public Map<String, String> greeting() {
        return Map.of(
                "status", "running",
                "message", "This data comes from a custom Actuator endpoint"
        );
    }
}