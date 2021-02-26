package com.devwue.spring.api.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Configuration
@EnableSwagger2
@Profile({"local", "dev", "qa", "stage"})
public class SwaggerConfiguration {

    private ApiInfo apiInfo(String version) {
        return new ApiInfo(
                "API Spec",
                "devwue-spring-api",
                version,
                "Devwue",
                new Contact("Devwue", "https://devwue.com", ""),
                " ",
                " ",
                new ArrayList<>());
    }

    private Docket baseDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponse())
                .globalResponseMessage(RequestMethod.POST, globalResponse())
                .globalResponseMessage(RequestMethod.PUT, globalResponse())
                .globalResponseMessage(RequestMethod.DELETE, globalResponse())
                .produces(new HashSet<>(Collections.singletonList(MediaType.APPLICATION_JSON_VALUE)))
                .directModelSubstitute(LocalDate.class, String.class);
    }

    @Bean
    public Docket test() {
        return baseDocket()
                .groupName("test")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.devwue.spring.api.controller.test"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("1.0"));
    }

    @Bean
    public Docket service() {
        return baseDocket()
                .groupName("service")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.devwue.spring.api.controller.service"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo("1.0"));
    }

    /**
     * global 응답 설정
     */
    private List<ResponseMessage> globalResponse() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(200).message("OK").build(),
                new ResponseMessageBuilder().code(400).message("클라이언트 실패 (유효성검사, 파라미터 누락, JSON 실패 등)").build(),
                new ResponseMessageBuilder().code(401).message("Need Authentication").build(),
                new ResponseMessageBuilder().code(403).message("Need Authorization").build(),
                new ResponseMessageBuilder().code(404).message("Not Found").build(),
                new ResponseMessageBuilder().code(500).message("Server Error").build()
        );
    }
}
