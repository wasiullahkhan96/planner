package com.example.planner.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Planner API")
                        .version("1.0")
                        .description("API for planning activities")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Wasiullah Khan")
                                .url("https://www.linkedin.com/in/wasiullahkhan/")
                                .email("wasiullah.khan96@gmail.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}