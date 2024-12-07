package com.moses.code.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@Schema(hidden = true)
public class ErrorResponse {

    @Schema(hidden = true)
    private String message;

    @Schema(hidden = true)
    private String description;

}
