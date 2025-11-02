package com.imagicode.agorasoftadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/* Habilita @Async para listeners y servicios no bloqueantes. */
@Configuration
@EnableAsync
public class AsyncConfig {

}
