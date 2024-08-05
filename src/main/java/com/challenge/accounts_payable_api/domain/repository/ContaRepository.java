package com.challenge.accounts_payable_api.domain.repository;

import com.challenge.accounts_payable_api.domain.model.Conta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ContaRepository {
    Conta save(Conta conta);
    Optional<Conta> findById(Long id);
    List<Conta> findAll();
    List<Conta> findByDescricaoAndDataVencimento(String descricao, LocalDate dataVencimento);
    BigDecimal findTotalPaidBetweenDates(LocalDate startDate, LocalDate endDate);
    void saveAll(List<Conta> contas);
}
