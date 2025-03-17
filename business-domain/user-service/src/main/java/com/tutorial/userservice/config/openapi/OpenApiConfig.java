package com.tutorial.userservice.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

//@formatter:off
@OpenAPIDefinition(
    info = @Info(
        contact = @Contact(
            name = "Pablo Rizqu", 
            email = "ramosizquierdop@gmail.com"
        ),
        title = "User Service API",
        version = "1.0.0",
        description = "User Service API v1",
        termsOfService = "http://swagger.io/terms/",
        license = @License(
            name = "Apache 2.0", 
            url = "http://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8001", 
            description = "Local Environment"
        ),
        @Server(
            url = "http://localhost:30001", 
            description = "Kubernetes Environment"
        )
    }
)
//@formatter:on
public class OpenApiConfig {

}
