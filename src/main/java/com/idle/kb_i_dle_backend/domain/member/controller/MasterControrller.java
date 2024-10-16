package com.idle.kb_i_dle_backend.domain.member.controller;

import com.idle.kb_i_dle_backend.domain.member.service.MasterService;
import com.idle.kb_i_dle_backend.global.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master")
@Slf4j
public class MasterControrller {

    private final MasterService masterService;

    public MasterControrller(MasterService masterService) {
        this.masterService = masterService;
    }

    // 주식 가격 업데이트
    @GetMapping("/update/stock")
    public ResponseEntity<?> updateStockPrice(HttpServletRequest request) {

        try {
            masterService.updateStockPrices();
            SuccessResponseDTO response = new SuccessResponseDTO(true, "stock price update complete");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getAvailableAsset: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve available assets: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    // 주식 가격 업데이트
    @GetMapping("/update/stock/before")
    public ResponseEntity<?> updateStockPricesBefore(HttpServletRequest request) {

        try {
            masterService.updateStockPricesBefore();
            SuccessResponseDTO response = new SuccessResponseDTO(true, "stock price update complete");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getAvailableAsset: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve available assets: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 코인 가격 업데이트

    @GetMapping("/update/coin")
    public ResponseEntity<?> updateCoinPrice(HttpServletRequest request) {

        try {
            masterService.updateCoinPrices();
            SuccessResponseDTO response = new SuccessResponseDTO(true, "coin price update complete");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getAvailableAsset: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve available assets: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
