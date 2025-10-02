package com.nabiji.ecommerce.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                // კომპონენტების სექციაში დავამატე უსაფრთხოების სქემა და ღილაკიც გამომჩნდება ტოკენის ჩასაწერად
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP) // უსაფრთხოების ტიპი: HTTP
                                                .scheme("bearer") // სქემის ტიპი: Bearer
                                                .bearerFormat("JWT") // ტოკენის ფორმატი: JWT
                                )
                )
                // დავამატე გლობალური მოთხოვნა, რომ ყველა ენდფოინთმა გამოიყენოს ეს სქემა, სათითაოდ როომ არ დამჭირდეს
                //ყველა ენდფოინთზე ჩავწერო ტოკეენი
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // API-ს ზოგადი ინფორმაცია
                .info(new Info().title("4Nabiji E-commerce API").version("v1.0"));
    }
}