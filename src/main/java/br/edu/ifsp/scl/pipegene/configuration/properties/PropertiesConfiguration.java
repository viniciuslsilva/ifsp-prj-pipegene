package br.edu.ifsp.scl.pipegene.configuration.properties;

import br.edu.ifsp.scl.pipegene.configuration.properties.model.ExecutionProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "execution.batch-size")
    public ExecutionProperties executionProperties() {
        return new ExecutionProperties();
    }
}
