package com.challenge.accounts_payable_api.application.service;

import com.challenge.accounts_payable_api.application.dto.ContaDTO;
import com.challenge.accounts_payable_api.domain.model.Conta;
import com.challenge.accounts_payable_api.domain.model.StatusConta;
import com.challenge.accounts_payable_api.domain.repository.ContaRepository;
import com.challenge.accounts_payable_api.interfaces.mapper.ContaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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

        if (file.isEmpty()) {
            throw new RuntimeException("O arquivo CSV está vazio.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            List<Conta> contas = new ArrayList<>();

            // Pular o cabeçalho se existir
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                Conta conta = new Conta();

                conta.setId(Long.valueOf(data[0]));
                conta.setDescricao(data[1]);
                conta.setDataVencimento(LocalDate.parse(data[2]));
                conta.setDataPagamento(LocalDate.parse(data[3]));
                conta.setValor(new BigDecimal(data[4]));
                conta.setSituacao(StatusConta.valueOf(data[5]));

                contas.add(conta);
            }

            contaRepository.saveAll(contas);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }
}
