package com.cartmanager.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cartmanager.entity.Product;
import com.cartmanager.entity.User;
import com.cartmanager.repository.ProductRepository;
import com.cartmanager.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void AdicionarProdutoAoCarrinho(String email, String productId) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Usuário com o email fornecido não encontrado.");
        }

        Product product = productRepository.findById(UUID.fromString(productId)).orElse(null);

        if (product == null) {
            throw new RuntimeException("Produto com o ID fornecido não encontrado.");
        }

        if ("esgotado".equalsIgnoreCase(product.getStatus())) {
            throw new RuntimeException("Este produto está esgotado e não pode ser adicionado ao carrinho.");
        }

        user.getCart().add(product);
        user.setStatus("comprando");
        userRepository.save(user);
    }

    @Transactional
    public void RemoverProdutoDoCarrinho(String email, String productId) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Usuário com o email fornecido não encontrado.");
        }

        boolean produtoRemovido = user.getCart().removeIf(product -> product.getId().toString().equals(productId));

        if (!produtoRemovido) {
            throw new RuntimeException("Produto com ID " + productId + " não encontrado no carrinho.");
        }

        userRepository.save(user);
    }

    @Transactional
    public void MudarStatus(String email, String newStatus) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("Usuário com o email fornecido não encontrado.");
        }

        if (!"comprou".equalsIgnoreCase(newStatus) && !"cancelou".equalsIgnoreCase(newStatus)) {
            throw new RuntimeException("Status inválido. Apenas 'comprou' ou 'cancelou' são permitidos.");
        }

        user.setStatus(newStatus);

        if ("comprou".equalsIgnoreCase(newStatus)) {
            user.getCart().clear();
        }
        if("cancelou".equalsIgnoreCase(newStatus)){
            user.getCart().clear();
        }

        userRepository.save(user);
    }

}
