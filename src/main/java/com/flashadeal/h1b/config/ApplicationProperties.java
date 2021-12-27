package com.flashadeal.h1b.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to H 1 B Register Application.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {}
