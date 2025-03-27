package com.cartmanager.service;

import com.cartmanager.controller.CreateProductDto;
import com.cartmanager.controller.UpdateProductDto;
import com.cartmanager.entity.Product;
import com.cartmanager.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public UUID CriarProduto(CreateProductDto dto) {

        if(productRepository.findByName(dto.name()) != null){
            throw new RuntimeException("Produto com este nome ja existente");
        }

        var entity = new Product(
                UUID.randomUUID(),
                "Disponível",
                dto.name(),
                dto.value()
        );

        var ProdSave = productRepository.save(entity);
        return ProdSave.getId();
    }

    public List<Product> ListarProdutos() {
        return productRepository.findAll();
    }

    public void AtualizarProduto(String prodId, UpdateProductDto dto) {
        var id = UUID.fromString(prodId);
        var ProdEntity = productRepository.findById(id);

        if (ProdEntity.isPresent()) {
            var prod = ProdEntity.get();

            if(dto.status() != null) {
                prod.setStatus(dto.status());
            }
            if(dto.name() != null) {
                prod.setName(dto.name());
            }
            if(dto.value() != null) {
                prod.setValue(dto.value());
            }

            productRepository.save(prod);
        } else {
            throw new RuntimeException("Produto com ID " + prodId + " não encontrado no carrinho.");
        }

    }

    public void DeletarProduto(String prodId){

        var id = UUID.fromString(prodId);
        var ProdutoExiste = productRepository.existsById(id);

        if(ProdutoExiste){
            productRepository.deleteById(id);
        }else{
            throw new RuntimeException("Produto com ID " + prodId + " não encontrado no carrinho.");
        }
    }

}
