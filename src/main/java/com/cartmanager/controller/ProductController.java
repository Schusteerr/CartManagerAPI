package com.cartmanager.controller;

import com.cartmanager.entity.Product;
import com.cartmanager.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> CriarProduto(@RequestBody CreateProductDto dto) {

        try{
            var userId = productService.CriarProduto(dto);
            return ResponseEntity.created(URI.create("/products/" + userId)).body("Produto " + dto.name() + " criado com sucesso. Seu ID é: "+userId);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro de criação de produto: " + e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<List<Product>> ListarProdutos() {

        try{
            var produtos = productService.ListarProdutos();
            return ResponseEntity.ok(produtos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


    }

    @PutMapping("/{prodId}")
    public ResponseEntity<String> AtualizarProduto(@PathVariable("prodId") String prodId, @RequestBody UpdateProductDto dto) {

        try {
            productService.AtualizarProduto(prodId, dto);
            return ResponseEntity.ok("Produto atualizado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao atualizar o produto: " + e.getMessage());
        }

    }

    @DeleteMapping("/{prodId}")
    public ResponseEntity<String> DeletarProduto(@PathVariable("prodId") String prodId) {

        try{
            productService.DeletarProduto(prodId);
            return ResponseEntity.ok("Produto '" + prodId + "' deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao deletar o produto: " + e.getMessage());
        }

    }

}
