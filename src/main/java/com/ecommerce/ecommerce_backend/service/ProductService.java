package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.ProductRequest;
import com.ecommerce.ecommerce_backend.dto.ProductResponse;
import com.ecommerce.ecommerce_backend.exception.ProductNotFoundException;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    private ProductResponse mapToResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory()
        );
    }
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ProductResponse addProduct(ProductRequest request){
        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public ProductResponse getProductById(String id){
        Product product = findProductById(id);
        return mapToResponse(product);
    }

    public ProductResponse updateProduct(String id, ProductRequest request){
        Product existingProduct = findProductById(id);

        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setCategory(request.getCategory());

        Product updatedProduct = productRepository.save(existingProduct);

        return mapToResponse(updatedProduct);

    }

    public void deleteProduct(String id){
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    private Product findProductById(String id){
        return productRepository.findById(id)
                .orElseThrow(()->
                        new ProductNotFoundException(
                                "Product not found with id :" + id
                        ));
    }
}
