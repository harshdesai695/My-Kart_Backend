package com.myKart.product.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import com.myKart.infra.exception.BussinessException;
import com.myKart.product.dto.Product;
import com.myKart.product.repository.ProductRepository;

@Service
public class ProductService {
	
	Date date = new Date();
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	
	public String addProduct(Product newProduct) throws Exception{
		try {
			productRepository.save(newProduct);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		return "Added";
	}


	public Product getProduct(String productId) throws Exception{
		Product product;
		try {
			product=productRepository.findByProductId(productId);
			if(product==null) {
				throw new BussinessException("Product Not Found");
			}
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return product;
	}
	
	public List<Product> getAllProduct() throws Exception{
		List<Product> productList;
		try {
			productList = productRepository.findAll();
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return productList;
	}
	
	public List<Product> getRandomProduct() throws Exception{
		List<Product> productList;
		try {
			 Aggregation aggregation = Aggregation.newAggregation(
	                    Aggregation.sample(5) // Fetch a random sample of 5 documents
	            );
	            
	            AggregationResults<Product> results = mongoTemplate.aggregate(aggregation, "Product", Product.class);
	            productList = results.getMappedResults();
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return productList;
	}
	@Cacheable(value = "productsByBrand", key = "#brandName")
	public List<Product> getProductByBrandName(String brandName)throws Exception{
		List<Product> productList;
		try {
			productList=productRepository.findByBrandNameContaining(brandName);
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		return productList;
	}
	
}
