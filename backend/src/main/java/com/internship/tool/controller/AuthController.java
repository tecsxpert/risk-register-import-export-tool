package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponseFactory;
import com.internship.tool.dto.ApiSuccessResponse;
import com.internship.tool.dto.AuthLoginRequest;
import com.internship.tool.dto.AuthRefreshRequest;
import com.internship.tool.dto.AuthRegisterRequest;
import com.internship.tool.dto.AuthResponse;
import com.internship.tool.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> register(
        HttpServletRequest httpServletRequest,
        @Valid @RequestBody AuthRegisterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                ApiResponseFactory.success(
                    HttpStatus.CREATED,
                    "User registered successfully",
                    httpServletRequest.getRequestURI(),
                    authService.register(request)
                )
            );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> login(
        HttpServletRequest httpServletRequest,
        @Valid @RequestBody AuthLoginRequest request
    ) {
        return ResponseEntity.ok(
            ApiResponseFactory.success(
                HttpStatus.OK,
                "User logged in successfully",
                httpServletRequest.getRequestURI(),
                authService.login(request)
            )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiSuccessResponse<AuthResponse>> refreshToken(
        HttpServletRequest httpServletRequest,
        @Valid @RequestBody AuthRefreshRequest request
    ) {
        return ResponseEntity.ok(
            ApiResponseFactory.success(
                HttpStatus.OK,
                "Token refreshed successfully",
                httpServletRequest.getRequestURI(),
                authService.refreshToken(request)
            )
        );
    }
}
