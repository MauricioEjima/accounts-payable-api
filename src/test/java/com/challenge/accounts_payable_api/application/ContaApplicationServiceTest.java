package com.challenge.accounts_payable_api.application;

import com.challenge.accounts_payable_api.application.service.ContaApplicationService;
import com.challenge.accounts_payable_api.domain.repository.ContaRepository;
import com.challenge.accounts_payable_api.interfaces.mapper.ContaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContaApplicationServiceTest {

    @InjectMocks
    private ContaApplicationService contaApplicationService;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ContaMapper contaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportarContasViaCSV() {
        // Configurar os mocks e testar o método importarContasViaCSV
    }
}
