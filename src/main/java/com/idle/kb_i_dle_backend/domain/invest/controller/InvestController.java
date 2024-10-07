package com.idle.kb_i_dle_backend.domain.invest.controller;

import com.idle.kb_i_dle_backend.global.dto.ErrorResponseDTO;
import com.idle.kb_i_dle_backend.global.dto.SuccessResponseDTO;
import com.idle.kb_i_dle_backend.domain.invest.dto.*;
import com.idle.kb_i_dle_backend.domain.invest.service.InvestService;
import com.idle.kb_i_dle_backend.domain.member.util.JwtProcessor;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invest")
@Slf4j
@RequiredArgsConstructor
public class InvestController {

    private final InvestService investService;

    // 투자 여유 자산 조회
    @GetMapping("/available")
    public ResponseEntity<?> getAvailableAsset(HttpServletRequest request) {
        Integer uid = (Integer) request.getAttribute("uid");
        log.info("check uid in invest" + uid);
        try {
            AvailableCashDTO availableCashDTO = investService.getAvailableCash(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, availableCashDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getAvailableAsset: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve available assets: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 성향 조회
    @GetMapping("/tendency")
    public ResponseEntity<?> getMaxPercentageCategory(HttpServletRequest request) {
        Integer uid = (Integer) request.getAttribute("uid");
        try {
            MaxPercentageCategoryDTO maxPercentageCategory = investService.getMaxPercentageCategory(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, maxPercentageCategory);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getMaxPercentageCategory: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve max percentage category: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/recommended")
    public ResponseEntity<?> getRecommendedProducts(HttpServletRequest request) {
        Integer uid = (Integer) request.getAttribute("uid");
        try {
            List<RecommendedProductDTO> recommendedProducts = investService.getRecommendedProducts(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, recommendedProducts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getRecommendedProducts: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve recommended products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn/stock")
    public ResponseEntity<?> getHighReturnStock(HttpServletRequest request) {
        Integer uid = (Integer) request.getAttribute("uid");
        try {
            List<HighReturnProductDTO> highReturnProductDTOS = investService.getHighReturnStock(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, highReturnProductDTOS);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error in getHighReturnProduct: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve high return products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn/coin")
    public ResponseEntity<?> getHighReturnCoin(HttpServletRequest request) {
        Integer uid = (Integer) request.getAttribute("uid");
        try {
            List<HighReturnProductDTO> highReturnCoins = investService.getHighReturnCoin(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, highReturnCoins);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getHighReturnCoin: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve high return coins: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/highreturn")
    public ResponseEntity<?> getHighReturnProducts(HttpServletRequest request) {
        Integer uid = (Integer) request.getAttribute("uid");
        try {
            HighReturnProductsDTO highReturnProducts = investService.getHighReturnProducts(uid);
            SuccessResponseDTO response = new SuccessResponseDTO(true, highReturnProducts.getProducts());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getHighReturnProducts: ", e);
            ErrorResponseDTO response = new ErrorResponseDTO(
                    "Failed to retrieve high return products: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
