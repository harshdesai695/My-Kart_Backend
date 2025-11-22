package com.myKart.product.service;

import com.myKart.infra.exception.BussinessException;
import com.myKart.product.dto.Product;
import com.myKart.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product) throws Exception {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new Exception("Error while adding the product: " + e.getMessage());
        }
    }

    public List<Product> getAllProducts() throws Exception {
        try {
            List<Product> products = productRepository.findAll();
            if (products.isEmpty()) {
                throw new BussinessException("No products found.");
            }
            return products;
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while retrieving products: " + e.getMessage());
        }
    }

    public Product getProductById(String id) throws Exception {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new BussinessException("Product with ID " + id + " not found.");
            }
            return product.get();
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while retrieving product: " + e.getMessage());
        }
    }
    
    public List<Product> getProductByBrandName(String brand) throws Exception {
    	List<Product> products=null;
        try {
            products = productRepository.findByBrandNameContaining(brand);
            if (products.isEmpty()) {
                throw new BussinessException("No products found.");
            }
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while retrieving products: " + e.getMessage());
        }
        return products;
    } 

    @Transactional
    public String deleteProduct(String id) throws Exception {
        try {
            if (!productRepository.existsById(id)) {
                throw new BussinessException("Product with ID " + id + " not found.");
            }
            productRepository.deleteById(id);
            return "Product Deleted Successfully";
        } catch (BussinessException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception("Error while deleting product: " + e.getMessage());
        }
    }
}