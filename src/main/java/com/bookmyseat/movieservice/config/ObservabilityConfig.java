package com.bookmyseat.movieservice.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObservabilityConfig {

    @Bean
    public Counter movieRequestCounter(MeterRegistry meterRegistry) {
        return Counter.builder("movie_requests_total")
                .description("Total number of movie requests")
                .register(meterRegistry);
    }

    @Bean
    public Timer movieRequestTimer(MeterRegistry meterRegistry) {
        return Timer.builder("movie_request_duration")
                .description("Time taken to process movie requests")
                .register(meterRegistry);
    }

    @Bean
    public Counter showtimeRequestCounter(MeterRegistry meterRegistry) {
        return Counter.builder("showtime_requests_total")
                .description("Total number of showtime requests")
                .register(meterRegistry);
    }

    @Bean
    public Timer showtimeRequestTimer(MeterRegistry meterRegistry) {
        return Timer.builder("showtime_request_duration")
                .description("Time taken to process showtime requests")
                .register(meterRegistry);
    }
}