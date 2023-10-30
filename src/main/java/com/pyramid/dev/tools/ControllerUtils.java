package com.pyramid.dev.tools;

import com.pyramid.dev.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ControllerUtils {

    public ResponseEntity<ApiResponse> getResponse(ApiResponse response) {
        return ResponseEntity.status(response.getStatus().value()).body(response);
    }

}
