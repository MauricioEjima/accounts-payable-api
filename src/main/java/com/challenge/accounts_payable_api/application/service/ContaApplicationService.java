package com.challenge.accounts_payable_api.application.service;

import com.challenge.accounts_payable_api.application.dto.ContaDTO;
import com.challenge.accounts_payable_api.domain.model.Conta;
import com.challenge.accounts_payable_api.domain.model.StatusConta;
import com.challenge.accounts_payable_api.domain.repository.ContaRepository;
import com.challenge.accounts_payable_api.interfaces.mapper.ContaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaApplicationService {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaMapper contaMapper;

    public ContaDTO criarConta(ContaDTO contaDTO) {
        Conta conta = contaMapper.toEntity(contaDTO);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    public ContaDTO atualizarConta(Long id, ContaDTO contaDTO) {
        Conta contaExistente = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        contaExistente.setDataVencimento(contaDTO.getDataVencimento());
        contaExistente.setDataPagamento(contaDTO.getDataPagamento());
        contaExistente.setValor(contaDTO.getValor());
        contaExistente.setDescricao(contaDTO.getDescricao());
        contaExistente.setSituacao(contaDTO.getSituacao());

        contaExistente = contaRepository.save(contaExistente);
        return contaMapper.toDto(contaExistente);
    }

    public ContaDTO alterarSituacaoConta(Long id, StatusConta novaSituacao) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setSituacao(novaSituacao);
        conta = contaRepository.save(conta);
        return contaMapper.toDto(conta);
    }

    public List<ContaDTO> listarContas(String descricao, LocalDate dataVencimento) {
        List<Conta> contas = contaRepository.findByDescricaoAndDataVencimento(descricao, dataVencimento);
        return contas.stream().map(contaMapper::toDto).collect(Collectors.toList());
    }

    public ContaDTO obterContaPorId(Long id) {
        Conta conta = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        return contaMapper.toDto(conta);
    }

    public BigDecimal calcularTotalPago(LocalDate inicio, LocalDate fim) {
        return contaRepository.findTotalPaidBetweenDates(inicio, fim);
    }

    public void importarContasViaCSV(MultipartFile file) {
        //TODO Implementação da lógica de importação de CSV
    }
}
