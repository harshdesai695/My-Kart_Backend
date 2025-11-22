package com.myKart.product.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Product")
public class Product {
	@Id
	private String productId;
	private String productName;
	private String productCost;
	private String productQuantity;
	private String productRating;
	private String brandName;
	private Object productDetails;
	private List<String> productImageUrl;
	private String sellerId;
	
	
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCost() {
		return productCost;
	}
	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}
	public String getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}
	public String getProductRating() {
		return productRating;
	}
	public void setProductRating(String productRating) {
		this.productRating = productRating;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Object getProductDetails() {
		return productDetails;
	}
	public void setProductDetails(Object productDetails) {
		this.productDetails = productDetails;
	}
	public List<String> getProductImageUrl() {
		return productImageUrl;
	}
	public void setProductImageUrl(List<String> productImageUrl) {
		this.productImageUrl = productImageUrl;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productCost=" + productCost
				+ ", productQuantity=" + productQuantity + ", productRating=" + productRating + ", brandName="
				+ brandName + ", productDetails=" + productDetails + ", productImageUrl=" + productImageUrl + "]";
	}
	
	
}
