package com.myKart.userActivity.dto;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="UserActivity")
public class UserActivity {
	
	private String userId;
	private List<String> wishList;
	private List<String> cartList;
	private List<String> orderList;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getWishList() {
		return wishList;
	}
	public void setWishList(List<String> wishList) {
		this.wishList = wishList;
	}
	public List<String> getCartList() {
		return cartList;
	}
	public void setCartList(List<String> cartList) {
		this.cartList = cartList;
	}
	public List<String> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<String> orderList) {
		this.orderList = orderList;
	}

}

