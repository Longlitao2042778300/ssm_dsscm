package cn.dsscm.domain;

import java.io.Serializable;

public class OrderDetail implements Serializable {
	private Long id;// ID
	private String orderId;// 订单ID
	private Integer productId;// 商品ID
	private Product product;// 商品
	private long quantity;// 数量
	private Float cost;// 小计

	public OrderDetail() {
		super();
	}

	public OrderDetail(Long id, String orderId, Integer productId, long quantity,
			Float cost) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", orderId=" + orderId
				+ ", productId=" + productId + ", product=" + product
				+ ", quantity=" + quantity + ", cost=" + cost + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

}
