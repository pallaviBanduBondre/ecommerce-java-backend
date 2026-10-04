package com.ecommerce.ecommerce_backend.controller;
import com.ecommerce.ecommerce_backend.dto.ProductRequest;
import com.ecommerce.ecommerce_backend.dto.ProductResponse;
import com.ecommerce.ecommerce_backend.model.Product;
import com.ecommerce.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController{

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

     @GetMapping("/api/products")
     public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
     }

     @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        ProductResponse createdProduct = productService.addProduct(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
     }

     @GetMapping ("/api/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id){
        return ResponseEntity.ok(productService.getProductById(id));
     }

     @PutMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse>updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest request){
        ProductResponse updatedProduct = productService.updateProduct(id, request);
        return ResponseEntity.ok(updatedProduct);
     }

     @DeleteMapping("/api/products/{id}")
     public ResponseEntity<Void> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
     }
}