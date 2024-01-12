package com.adaneinstein.pedidos.constants;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResponseMessages {
    SUCCESS("Operação efetuada com sucesso"),
    ERROR("Ocorreu algum erro");

    private String message;

    @Override
    public String toString() {
        return this.message;
    }
}
