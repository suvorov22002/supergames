package com.pyramid.dev.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse {

    @Builder.Default
    boolean success = true;

    @Builder.Default
    Object data = null;

    @Builder.Default
    HttpStatus status = HttpStatus.OK;

    @Builder.Default
    String message = "";

}
