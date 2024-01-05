package org.rigon.projeto.rest.controller;

import jakarta.validation.Valid;
import org.rigon.projeto.domain.entity.Produto;
import org.rigon.projeto.domain.repository.ProdutoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository){this.produtoRepository = produtoRepository;}

    //Cadastrar produto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveProduto(@RequestBody @Valid Produto produto){
        return produtoRepository.save(produto);
    }

    //Buscar produto por ID
    @GetMapping("{id}")
    public Produto getProdutoByID(@PathVariable Integer id ){
        return produtoRepository.findById(id)
                                .orElseThrow(() ->
                                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.")
                                );
    }

    //Deletar produto por ID
    @DeleteMapping("{id}")
    public void deleteProduto(@PathVariable Integer id){
        produtoRepository.findById(id)
                .map(produto -> {
                    produtoRepository.delete(produto);
                    return produto;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.")
                );
    }

    //Atualizar produto por ID
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable Integer id,
                              @RequestBody @Valid Produto produto){
        produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produto.setId(produtoExistente.getId());
                    produtoRepository.save(produto);
                    return produtoExistente;
                }).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado.")
                );

    }

    //Buscar Produto por filtro
    @GetMapping
    public List<Produto> findProduto(Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Produto> example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);
    }

}
