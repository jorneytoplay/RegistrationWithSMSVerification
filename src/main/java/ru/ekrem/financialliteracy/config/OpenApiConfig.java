package ru.ekrem.financialliteracy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.security.access.annotation.Secured;

@OpenAPIDefinition(
        info = @Info(
                title = "Financial Literacy",
                description = "Everything for your finances",
                version = "alpha 0.1",
                contact = @Contact(
                        email = "jorneytoplay@gmail.com",
                        name = "Memedlyaev Ekrem",
                        url = "https://github.com/jorneytoplay"
                )
        )
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
