-- V1__criar_tabela_contas_a_pagar.sql
CREATE TABLE contas_a_pagar (
    id SERIAL PRIMARY KEY,
    data_vencimento DATE,
    data_pagamento DATE,
    valor DECIMAL(10, 2),
    descricao VARCHAR(255),
    situacao VARCHAR(50)
);
