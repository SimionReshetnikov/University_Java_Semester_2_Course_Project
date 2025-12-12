package com.example.webshop.service;

import com.example.webshop.entity.Product;
import com.example.webshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findWithFilter(String category,
                                        BigDecimal minPrice,
                                        BigDecimal maxPrice,
                                        String search) {

        return productRepository.findAll().stream()
                .filter(p -> category == null || category.isEmpty()
                        || (p.getCategory() != null
                        && p.getCategory().equalsIgnoreCase(category)))

                .filter(p -> minPrice == null
                        || p.getPrice().compareTo(minPrice) >= 0)

                .filter(p -> maxPrice == null
                        || p.getPrice().compareTo(maxPrice) <= 0)

                .filter(p -> search == null || search.isBlank()
                        || p.getName().toLowerCase().contains(search.toLowerCase()))

                .toList();
    }

    public List<Product> findWithFilter(String category,
                                        BigDecimal minPrice,
                                        BigDecimal maxPrice) {
        return findWithFilter(category, minPrice, maxPrice, null);
    }

    public List<String> getAllCategories() {
        return productRepository.findDistinctCategories();
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}