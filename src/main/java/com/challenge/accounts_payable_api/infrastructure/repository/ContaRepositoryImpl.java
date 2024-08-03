package com.challenge.accounts_payable_api.infrastructure.repository;

import com.challenge.accounts_payable_api.domain.model.Conta;
import com.challenge.accounts_payable_api.domain.repository.ContaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@Transactional
public class ContaRepositoryImpl implements ContaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Conta save(Conta conta) {
        if (conta.getId() == null) {
            entityManager.persist(conta);
        } else {
            conta = entityManager.merge(conta);
        }
        return conta;
    }

    @Override
    public Optional<Conta> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Conta.class, id));
    }

    @Override
    public List<Conta> findAll() {
        return entityManager.createQuery("FROM Conta", Conta.class).getResultList();
    }

    @Override
    public List<Conta> findByDescricaoAndDataVencimento(String descricao, LocalDate dataVencimento) {
        return entityManager.createQuery("FROM Conta c WHERE c.descricao = :descricao AND c.dataVencimento = :dataVencimento", Conta.class)
                .setParameter("descricao", descricao)
                .setParameter("dataVencimento", dataVencimento)
                .getResultList();
    }

    @Override
    public BigDecimal findTotalPaidBetweenDates(LocalDate startDate, LocalDate endDate) {
        return entityManager.createQuery("SELECT SUM(c.valor) FROM Conta c WHERE c.dataPagamento BETWEEN :startDate AND :endDate", BigDecimal.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }

    @Override
    public void saveAll(List<Conta> contas) {
        for (Conta conta : contas) {
            if (conta.getId() == null) {
                entityManager.persist(conta);
            } else {
                entityManager.merge(conta);
            }
        }
    }
}
