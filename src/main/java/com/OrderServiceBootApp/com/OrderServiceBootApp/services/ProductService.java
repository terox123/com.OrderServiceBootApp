package com.OrderServiceBootApp.com.OrderServiceBootApp.services;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Product;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found, id: " + id));
    }


    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product newData) {
        Product product = getById(id);
        newData.setId(id);
        return productRepository.save(newData);
    }
    @Transactional
    public void delete(Long id) {
        Product product = getById(id);
        productRepository.deleteById(id);
    }
}