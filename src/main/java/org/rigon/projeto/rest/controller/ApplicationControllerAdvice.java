package org.rigon.projeto.rest.controller;

import org.rigon.projeto.exception.PedidoNaoEncontradoException;
import org.rigon.projeto.exception.RegraNegocioException;
import org.rigon.projeto.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception){
        String mensagemErro = exception.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException exception){
        return new ApiErrors(exception.getMessage());
    }
}
