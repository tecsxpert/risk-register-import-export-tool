package com.internship.tool.controller;

import com.internship.tool.dto.ApiResponseFactory;
import com.internship.tool.dto.ApiSuccessResponse;
import com.internship.tool.dto.RiskRegisterRequest;
import com.internship.tool.dto.RiskRegisterResponse;
import com.internship.tool.service.RiskRegisterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/risk-registers")
public class RiskRegisterController {

    private final RiskRegisterService riskRegisterService;

    public RiskRegisterController(RiskRegisterService riskRegisterService) {
        this.riskRegisterService = riskRegisterService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiSuccessResponse<Page<RiskRegisterResponse>>> getAllRiskRegisters(
        HttpServletRequest httpServletRequest,
        @PageableDefault(size = 10, sort = "updatedAt") Pageable pageable
    ) {
        return ResponseEntity.ok(
            ApiResponseFactory.success(
                HttpStatus.OK,
                "Risk registers fetched successfully",
                httpServletRequest.getRequestURI(),
                riskRegisterService.getAllRiskRegisters(pageable)
            )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<ApiSuccessResponse<RiskRegisterResponse>> getRiskRegisterById(
        HttpServletRequest httpServletRequest,
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(
            ApiResponseFactory.success(
                HttpStatus.OK,
                "Risk register fetched successfully",
                httpServletRequest.getRequestURI(),
                riskRegisterService.getRiskRegisterById(id)
            )
        );
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiSuccessResponse<RiskRegisterResponse>> createRiskRegister(
        HttpServletRequest httpServletRequest,
        @Valid @RequestBody RiskRegisterRequest request
    ) {
        RiskRegisterResponse response = riskRegisterService.createRiskRegister(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                ApiResponseFactory.success(
                    HttpStatus.CREATED,
                    "Risk register created successfully",
                    httpServletRequest.getRequestURI(),
                    response
                )
            );
    }
}
