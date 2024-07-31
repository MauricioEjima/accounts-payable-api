package com.challenge.accounts_payable_api.domain.service;

import com.challenge.accounts_payable_api.domain.model.Conta;
import com.challenge.accounts_payable_api.domain.model.StatusConta;
import com.challenge.accounts_payable_api.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ContaDomainService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta alterarSituacao(Long id, StatusConta novaSituacao) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setSituacao(novaSituacao);
        return contaRepository.save(conta);
    }

    public BigDecimal calcularTotalPago(LocalDate inicio, LocalDate fim) {
        return contaRepository.findTotalPaidBetweenDates(inicio, fim);
    }

    //TODO Outros métodos de lógica de negócios
}
