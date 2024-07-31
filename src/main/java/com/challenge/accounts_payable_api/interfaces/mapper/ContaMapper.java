package com.challenge.accounts_payable_api.interfaces.mapper;


import com.challenge.accounts_payable_api.application.dto.ContaDTO;
import com.challenge.accounts_payable_api.domain.model.Conta;
import org.springframework.stereotype.Component;

@Component
public class ContaMapper {

    public Conta toEntity(ContaDTO dto) {
        Conta conta = new Conta();
        conta.setId(dto.getId());
        conta.setDataVencimento(dto.getDataVencimento());
        conta.setDataPagamento(dto.getDataPagamento());
        conta.setValor(dto.getValor());
        conta.setDescricao(dto.getDescricao());
        conta.setSituacao(dto.getSituacao());
        return conta;
    }

    public ContaDTO toDto(Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setDataVencimento(conta.getDataVencimento());
        dto.setDataPagamento(conta.getDataPagamento());
        dto.setValor(conta.getValor());
        dto.setDescricao(conta.getDescricao());
        dto.setSituacao(conta.getSituacao());
        return dto;
    }
}
