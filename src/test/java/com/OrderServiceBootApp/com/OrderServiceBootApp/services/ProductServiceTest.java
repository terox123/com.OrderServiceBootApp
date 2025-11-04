package com.OrderServiceBootApp.com.OrderServiceBootApp.services;

import com.OrderServiceBootApp.com.OrderServiceBootApp.model.Product;
import com.OrderServiceBootApp.com.OrderServiceBootApp.repo.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProductService
 */
class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @InjectMocks private ProductService productService;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getById_found() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product found = productService.getById(1L);
        assertEquals(1L, found.getId());
    }

    @Test
    void getById_notFound() {
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.getById(2L));
    }

    @Test
    void create_success() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.create(product);
        assertEquals(product, result);
    }

    @Test
    void update_success() {
        Product existing = new Product();
        existing.setId(1L);

        Product updated = new Product();
        updated.setName("Updated");

        when(productRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(updated);

        Product result = productService.update(1L, updated);

        assertEquals("Updated", result.getName());
    }

    @Test
    void delete_success() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.delete(1L);
        verify(productRepository).deleteById(1L);
    }
}
