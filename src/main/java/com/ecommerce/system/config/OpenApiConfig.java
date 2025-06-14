package com.ecommerce.system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do SpringDoc OpenAPI para documentação da API.
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema E-commerce API")
                        .version("1.0.0")
                        .description("API REST para sistema de e-commerce com padrões de design")
                        .contact(new Contact()
                                .name("Desenvolvedor")
                                .email("dev@ecommerce.com")));
    }
}

