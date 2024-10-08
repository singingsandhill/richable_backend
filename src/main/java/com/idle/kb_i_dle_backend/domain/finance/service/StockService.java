package com.idle.kb_i_dle_backend.domain.finance.service;

import com.idle.kb_i_dle_backend.domain.finance.dto.StockDTO;
import java.text.ParseException;
import java.util.List;

public interface StockService {
    List<StockDTO> getStockList(Integer uid) throws Exception;

    StockDTO addStock(Integer uid, StockDTO stockDTO) throws ParseException;

    StockDTO updateStock(Integer uid, StockDTO stockDTO) throws ParseException;

    StockDTO deleteStock(Integer uid, Integer index) throws ParseException;
}
