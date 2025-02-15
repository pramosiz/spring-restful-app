package com.tutorial.userservice.exceptions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "This model is used to return errors in RFC 7807 format")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiExceptionResponseDTO {

    //@formatter:off
    @Schema(
        description = "The unique URI identifier that categorizes the error", 
        name = "type", 
        requiredMode = Schema.RequiredMode.REQUIRED, 
        example = "/errors/authentication/not-authorized")
    private String type;

    @Schema(
        description = "A brief, human-readable message about the error", 
        name = "title",
        requiredMode = Schema.RequiredMode.REQUIRED, 
        example = "The user doesn't have authorization")
    private String title;

    @Schema(
        description = "The unique error code", 
        name = "code",
        requiredMode = Schema.RequiredMode.NOT_REQUIRED,
        example = "192")
    private String code;

    @Schema(
        description = "A human-readable explanation of the error", 
        name = "detail",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Please contact with the author")
    private String detail;

    @Schema(
        description = "A URI that identifies the specific occurrence of the error",
        name = "instance",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "/errors/authentication/not-authorized/1")
    private String instance;
    //@formatter:on
}
