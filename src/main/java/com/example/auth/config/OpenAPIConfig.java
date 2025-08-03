package com.example.auth.config;

import com.example.auth.dto.LoginRequestDto;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // ✅ Automatically generate schema from existing DTO
        Schema<?> loginSchema = ModelConverters.getInstance()
                .read(LoginRequestDto.class)
                .values()
                .iterator()
                .next();
        return new OpenAPI()
                .path("/generate-token", new PathItem().post(
                        new Operation()
                                .security(List.of())
                                .summary("Generate JWT Token")
                                .description("Returns JWT for valid credentials")
                                .requestBody(new RequestBody()
                                        .description("LoginRequestDto")
                                        .required(true)
                                        .content(new Content().addMediaType(
                                                "application/json",
                                                new MediaType().schema(new Schema<>().$ref("#/components/schemas/LoginRequestDto"))
                                        )))
                                .addTagsItem("Authentication")
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("Token generated")))))
                .path("/refresh-token", new PathItem().post(
                        new Operation()
                                .security(List.of())
                                .summary("Refresh JWT Token")
                                .description("Returns new JWT using refresh token")
                                .addTagsItem("Authentication")
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("Token refreshed")))))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSchemas("LoginRequestDto", loginSchema)
                        .addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .description("Enter JWT Bearer token")))
                ;
    }
}

