package com.mykart.seller.dto;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="Seller")
public class Seller {
	
	@Id
	private String sellerId;
	private String sellerName;
	private String sellerLogo;
	private List<String> sellerProductList;
	
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String sellerPassword;
	
    private String sellerPhoneNo;
	private String sellerEmail;

    // Constructors
    public Seller() {}

    public Seller(String sellerId, String sellerName, String sellerEmail) {
        this.sellerId = sellerId;
        this.sellerName = sellerName;
        this.sellerEmail = sellerEmail;
    }

    // Getters and Setters
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public String getSellerLogo() {
		return sellerLogo;
	}
	public void setSellerLogo(String sellerLogo) {
		this.sellerLogo = sellerLogo;
	}
	public List<String> getSellerProductList() {
		return sellerProductList;
	}
	public void setSellerProductList(List<String> sellerProductList) {
		this.sellerProductList = sellerProductList;
	}
	public String getSellerPassword() {
		return sellerPassword;
	}
	public void setSellerPassword(String sellerPassword) {
		this.sellerPassword = sellerPassword;
	}
	public String getSellerPhoneNo() {
		return sellerPhoneNo;
	}
	public void setSellerPhoneNo(String sellerPhoneNo) {
		this.sellerPhoneNo = sellerPhoneNo;
	}
	public String getSellerEmail() {
		return sellerEmail;
	}
	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	@Override
	public String toString() {
		return "Seller [sellerId=" + sellerId + ", sellerName=" + sellerName + ", sellerLogo=" + sellerLogo
				+ ", sellerProductList=" + sellerProductList + ", sellerPassword=" + sellerPassword + ", sellerPhoneNo="
				+ sellerPhoneNo + ", sellerEmail=" + sellerEmail + "]";
	}
}