package com.cartmanager.controller;

import com.cartmanager.entity.Product;
import com.cartmanager.service.ProductService;
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
        var userId = productService.CriarProduto(dto);
        return ResponseEntity.created(URI.create("/products/" + userId)).body("Produto " + dto.name() + " criado com sucesso. Seu ID é: "+userId);
    }

    @GetMapping
    public ResponseEntity<List<Product>> ListarProdutos() {
        var produtos = productService.ListarProdutos();

        return ResponseEntity.ok(produtos);

    }

    @PutMapping("/{prodId}")
    public ResponseEntity<String> AtualizarProduto(@PathVariable("prodId") String prodId, @RequestBody UpdateProductDto dto) {
        productService.AtualizarProduto(prodId, dto);
        return ResponseEntity.ok("Produto atualizado com sucesso.");
    }

    @DeleteMapping("/{prodId}")
    public ResponseEntity<String> DeletarProduto(@PathVariable("prodId") String prodId) {

        productService.DeletarProduto(prodId);

        return ResponseEntity.ok("Produto '" + prodId + "' deletado com sucesso.");
    }

}
