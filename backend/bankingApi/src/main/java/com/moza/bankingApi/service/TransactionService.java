package com.moza.bankingApi.service;

import com.moza.bankingApi.dto.request.TransferRequest;
import com.moza.bankingApi.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

    void transfer(TransferRequest request);
    List<TransactionResponse> getBankStatement();
}
