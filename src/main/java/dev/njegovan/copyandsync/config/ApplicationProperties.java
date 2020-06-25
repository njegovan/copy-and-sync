package dev.njegovan.copyandsync.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private short dataBufferMaxValue;
    private String inputFile;
    private String outputFile;
}
