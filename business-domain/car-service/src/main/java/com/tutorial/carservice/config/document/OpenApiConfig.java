package com.tutorial.carservice.config.document;

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
        title = "Car Service API",
        version = "1.0.0",
        description = "Car Service API v1",
        termsOfService = "http://swagger.io/terms/",
        license = @License(
            name = "Apache 2.0", 
            url = "http://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8002", 
            description = "Local Environment"
        ),
        @Server(
            url = "http://localhost:30002", 
            description = "Kubernetes Environment"
        )
    }
)
//@formatter:on
public class OpenApiConfig {

}
