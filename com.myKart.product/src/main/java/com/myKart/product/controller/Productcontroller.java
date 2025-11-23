package com.myKart.product.controller;

import com.myKart.product.dto.ApiResponse;
import com.myKart.product.dto.Product;
import com.myKart.product.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class Productcontroller {

    @Autowired
    private ProductService productService;

    private static final Logger LOGGER = LogManager.getLogger(Productcontroller.class);

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product product) throws Exception {
        LOGGER.info("Incoming request to add product: {}", product.getProductName());
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(new ApiResponse<>(newProduct), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() throws Exception {
        LOGGER.info("Incoming request to get all products");
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(new ApiResponse<>(products), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable String id) throws Exception {
        LOGGER.info("Incoming request to get product with id: {}", id);
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(new ApiResponse<>(product), HttpStatus.OK);
    }
    
    @GetMapping("/getProductByBrand/{brandName}")
    public ResponseEntity<ApiResponse<List<Product>>> getProductByBrandName(@PathVariable String brandName) throws Exception {
        LOGGER.info("Incoming request to get product with id: {}", brandName);
        List<Product> product = productService.getProductByBrandName(brandName);
        return new ResponseEntity<>(new ApiResponse<>(product), HttpStatus.OK);
    }
    
    @GetMapping("/search/{keyword}")
    public ResponseEntity<ApiResponse<List<Product>>> searchProducts(@PathVariable String keyword)throws Exception{
    	 LOGGER.info("Incoming Search request to get product with Key: {}", keyword);
    	 List<Product> product = productService.searchProducts(keyword);
    	 return new ResponseEntity<>(new ApiResponse<>(product), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable String id) throws Exception {
        LOGGER.info("Incoming request to delete product with id: {}", id);
        String response = productService.deleteProduct(id);
        return new ResponseEntity<>(new ApiResponse<>(response), HttpStatus.OK);
    }
}