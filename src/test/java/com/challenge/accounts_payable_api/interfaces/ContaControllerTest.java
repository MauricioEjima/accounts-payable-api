package com.challenge.accounts_payable_api.interfaces;

import com.challenge.accounts_payable_api.application.service.ContaApplicationService;
import com.challenge.accounts_payable_api.interfaces.controller.ContaController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ContaControllerTest {

    @InjectMocks
    private ContaController contaController;

    @Mock
    private ContaApplicationService contaApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConta() {
        // Testar o endpoint de busca de conta
    }

    @Test
    void testCreateConta() {
        // Testar o endpoint de criação de conta
    }
}
