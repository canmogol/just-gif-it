package com.fererlab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class JustGifItHealthIndicator implements HealthIndicator {

    @Autowired
    private JustGifItProperties properties;

    @Override
    public Health health() {
        if (!properties.getGifLocation().canWrite()) {
            return Health.down().withDetail("directory-not-wriable", properties.getGifLocation()).build();
        } else {
            return Health.up().withDetail("JustGifIt-Key", "JustGifIt-Key").build();
        }
    }
}
