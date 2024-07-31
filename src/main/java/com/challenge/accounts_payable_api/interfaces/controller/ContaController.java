package com.challenge.accounts_payable_api.interfaces.controller;

import com.challenge.accounts_payable_api.application.dto.ContaDTO;
import com.challenge.accounts_payable_api.application.service.ContaApplicationService;
import com.challenge.accounts_payable_api.domain.model.StatusConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaApplicationService contaService;

    @PostMapping
    public ResponseEntity<ContaDTO> criarConta(@RequestBody ContaDTO contaDTO) {
        ContaDTO novaConta = contaService.criarConta(contaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> atualizarConta(@PathVariable Long id, @RequestBody ContaDTO contaDTO) {
        ContaDTO contaAtualizada = contaService.atualizarConta(id, contaDTO);
        return ResponseEntity.ok(contaAtualizada);
    }

    @PatchMapping("/{id}/situacao")
    public ResponseEntity<ContaDTO> alterarSituacaoConta(@PathVariable Long id, @RequestBody StatusConta situacao) {
        ContaDTO contaAtualizada = contaService.alterarSituacaoConta(id, situacao);
        return ResponseEntity.ok(contaAtualizada);
    }

    @GetMapping
    public ResponseEntity<List<ContaDTO>> listarContas(
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimento) {
        List<ContaDTO> contas = contaService.listarContas(descricao, dataVencimento);
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> obterContaPorId(@PathVariable Long id) {
        ContaDTO conta = contaService.obterContaPorId(id);
        return ResponseEntity.ok(conta);
    }

    @GetMapping("/total-pago")
    public ResponseEntity<BigDecimal> obterValorTotalPago(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        BigDecimal valorTotal = contaService.calcularTotalPago(inicio, fim);
        return ResponseEntity.ok(valorTotal);
    }

    @PostMapping("/importar")
    public ResponseEntity<Void> importarContas(@RequestParam("file") MultipartFile file) {
        contaService.importarContasViaCSV(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
