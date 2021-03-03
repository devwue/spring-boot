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
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.*;

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
                .apiInfo(apiInfo("1.0"))
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                ;
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

    private ApiKey apiKey() {
        return new ApiKey("JWT", "accessToken", "header");
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "어디서나 접속가능");
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build();
    }
}
