package org.rigon.projeto.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class ApiErrors {

    private final List<String> errors;

    public ApiErrors(String mensagemErro) {
        this.errors = Arrays.asList(mensagemErro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
