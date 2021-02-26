package com.devwue.spring.api.config;

import feign.Retryer;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;

public class FeignConfiguration {

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000,3);
    }

    @Bean
    public FeignFormatterRegistrar localDateFeginFormatterRegister() {
        return registry -> {
            DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
            registrar.setUseIsoFormat(true);
            registrar.registerFormatters(registry);
        };
    }
}
