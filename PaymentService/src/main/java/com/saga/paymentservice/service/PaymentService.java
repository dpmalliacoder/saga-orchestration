package com.saga.paymentservice.service;

import org.springframework.stereotype.Service;

import com.saga.paymentservice.dto.PaymentRequestDto;
import com.saga.paymentservice.dto.PaymentResponseDto;
import com.saga.paymentservice.dto.PaymentStatus;
import com.saga.paymentservice.repository.UserBalanceRepository;
import com.saga.paymentservice.repository.UserTransactionRepository;
import com.saga.paymentservice.entity.*;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {

    private final UserBalanceRepository balanceRepository;
    private final UserTransactionRepository transactionRepository;

    public PaymentService(UserBalanceRepository balanceRepository, UserTransactionRepository transactionRepository){
        this.balanceRepository = balanceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public PaymentResponseDto debit(final PaymentRequestDto requestDTO){
        
        PaymentResponseDto responseDTO = new PaymentResponseDto();
        responseDTO.setAmount(requestDTO.getAmount());
        responseDTO.setUserId(requestDTO.getUserId());
        responseDTO.setOrderId(requestDTO.getOrderId());
        responseDTO.setStatus(PaymentStatus.PAYMENT_REJECTED);

         return this.balanceRepository.findById(requestDTO.getUserId())
            .filter(ub -> ub.getBalance() >= requestDTO.getAmount())
                .map(ub -> {
                    ub.setBalance(ub.getBalance() - requestDTO.getAmount());  
                    responseDTO.setStatus(PaymentStatus.PAYMENT_APPROVED);                  
                    transactionRepository.findById(requestDTO.getOrderId())
                            .ifPresentOrElse(userTranscation -> {
                                userTranscation.setAmount(
                                    userTranscation.getAmount() + requestDTO.getAmount());                                    
                                transactionRepository.save(userTranscation);
                            }, () -> {
                                this.transactionRepository.save(UserTransaction.of(requestDTO.getOrderId(), requestDTO.getUserId(), requestDTO.getAmount()));
                            });
                    
                    return responseDTO;
                })
                .orElse(responseDTO);
    }

    @Transactional
    public void credit(final PaymentRequestDto requestDTO){

         transactionRepository.findById(requestDTO.getOrderId())
         .ifPresent(userTranscation -> {
            balanceRepository.findById(userTranscation.getUserId())
                .ifPresent(userBalance -> {
                    userBalance.setBalance(
                                userTranscation.getAmount() + userBalance.getBalance());
                });
             transactionRepository.delete(userTranscation);
         });
    }

}
