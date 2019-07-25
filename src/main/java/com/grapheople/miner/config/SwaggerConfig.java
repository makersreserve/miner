package com.grapheople.miner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!production")
public class SwaggerConfig {
    @Bean
    public Docket adminApi() {
        return getDocket("auth-api", "/api/**");
    }

    private Docket getDocket(String groupName, String pathPattern) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.grapheople"))
                .paths(PathSelectors.ant(pathPattern))
                .build()
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true);

        return docket;
    }
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Comarket API Document")
                .description("Comarket API based on Spring Boot")
                .version("1.0")
                .contact(new Contact("Pax", "", "grapheople@gmail.com"))
                .build();
    }
}
