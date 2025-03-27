package com.cartmanager.controller;

import com.cartmanager.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/update")
    public ResponseEntity<String> AdicionarProdutoAoCarrinho(@RequestBody AddRemoveProductDto dto) {
        try {
            cartService.AdicionarProdutoAoCarrinho(dto.email(), dto.productId());
            return ResponseEntity.ok("Produto adicionado ao carrinho com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao adicionar ao carrinho: " + e.getMessage());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<String> RemoverProdutoDoCarrinho(@RequestBody AddRemoveProductDto dto) {
        try {
            cartService.RemoverProdutoDoCarrinho(dto.email(), dto.productId());
            return ResponseEntity.ok("Produto removido com sucesso do carrinho.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro ao remover do carrinho: " + e.getMessage());
        }
    }

    @PostMapping("/finish")
    public ResponseEntity<String> MudarStatus(@RequestBody StatusDto dto) {
        try {
            cartService.MudarStatus(dto.email(), dto.status());
            String mensagem = switch (dto.status().toLowerCase()) {
                case "comprou" ->
                        "Obrigado por sua compra! Um email de confirmação foi enviado para " + dto.email() + ". Conte sempre conosco!";
                case "cancelou" ->
                        "Sentimos muito que tenha cancelado! Se pudermos ajudar de alguma forma, entre em contato.";
                default -> throw new RuntimeException("Status inválido. Tenha certeza de que você 'comprou' ou 'cancelou' o carrinho.");
            };
            return ResponseEntity.ok(mensagem);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Erro: " + e.getMessage());
        }
    }

}
