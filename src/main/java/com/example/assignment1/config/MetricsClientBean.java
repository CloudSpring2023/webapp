package com.example.assignment1.config;

import com.timgroup.statsd.NoOpStatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsClientBean {
    @Value("${publish.metrics}")
    private boolean publishMetrics;

    @Value("${metrics.server.hostname}")
    private String metricsServerHost;

    @Value("${metrics.server.port}")
    private int metricsServerPort;


    @Bean
    public com.timgroup.statsd.StatsDClient metricsClient() {
        if(publishMetrics)
            return new NonBlockingStatsDClient(metricsServerHost, metricsServerHost, metricsServerPort);
        return new NoOpStatsDClient();
    }
}
