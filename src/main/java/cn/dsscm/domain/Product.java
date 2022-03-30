package cn.dsscm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Product implements Serializable {
	private Integer id;// ID
	private String name;// 商品名
	private String description;// 描述
	private BigDecimal price;// 单价
	private String placement;// 摆放位置
	private BigDecimal stock;// 数量
	private Integer categoryLevel1Id;// 一级分类
	private Integer categoryLevel1IdCopy;// 一级分类复制版
	private Integer categoryLevel2Id;// 二级分类
	private Integer categoryLevel3Id;// 三级分类
	private String fileName;// 图片名称
	private int isDelete; // 是否删除(1：删除 0：未删除)
	private Integer createdBy; // 创建者
	private Date creationDate; // 创建时间
	private Integer modifyBy; // 更新者
	private Date modifyDate;// 更新时间

	private String pc1name;// 一级分类名称
	private String pc2name;// 二级分类名称
	private String pc3name;// 三级分类名称

	private Integer quantity;
	private List<Order> orders;

	public Product() {
	}

	public Product(Integer id, String name, String description, BigDecimal price, String placement, BigDecimal stock, Integer categoryLevel1Id, Integer categoryLevel1IdCopy, Integer categoryLevel2Id, Integer categoryLevel3Id, String fileName, int isDelete, Integer createdBy, Date creationDate, Integer modifyBy, Date modifyDate, String pc1name, String pc2name, String pc3name, Integer quantity, List<Order> orders) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.placement = placement;
		this.stock = stock;
		this.categoryLevel1Id = categoryLevel1Id;
		this.categoryLevel1IdCopy = categoryLevel1IdCopy;
		this.categoryLevel2Id = categoryLevel2Id;
		this.categoryLevel3Id = categoryLevel3Id;
		this.fileName = fileName;
		this.isDelete = isDelete;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
		this.pc1name = pc1name;
		this.pc2name = pc2name;
		this.pc3name = pc3name;
		this.quantity = quantity;
		this.orders = orders;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPlacement() {
		return placement;
	}

	public void setPlacement(String placement) {
		this.placement = placement;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public Integer getCategoryLevel1Id() {
		return categoryLevel1Id;
	}

	public void setCategoryLevel1Id(Integer categoryLevel1Id) {
		this.categoryLevel1Id = categoryLevel1Id;
	}

	public Integer getCategoryLevel1IdCopy() {
		return categoryLevel1IdCopy;
	}

	public void setCategoryLevel1IdCopy(Integer categoryLevel1IdCopy) {
		this.categoryLevel1IdCopy = categoryLevel1IdCopy;
	}

	public Integer getCategoryLevel2Id() {
		return categoryLevel2Id;
	}

	public void setCategoryLevel2Id(Integer categoryLevel2Id) {
		this.categoryLevel2Id = categoryLevel2Id;
	}

	public Integer getCategoryLevel3Id() {
		return categoryLevel3Id;
	}

	public void setCategoryLevel3Id(Integer categoryLevel3Id) {
		this.categoryLevel3Id = categoryLevel3Id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getPc1name() {
		return pc1name;
	}

	public void setPc1name(String pc1name) {
		this.pc1name = pc1name;
	}

	public String getPc2name() {
		return pc2name;
	}

	public void setPc2name(String pc2name) {
		this.pc2name = pc2name;
	}

	public String getPc3name() {
		return pc3name;
	}

	public void setPc3name(String pc3name) {
		this.pc3name = pc3name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", price=" + price +
				", placement='" + placement + '\'' +
				", stock=" + stock +
				", categoryLevel1Id=" + categoryLevel1Id +
				", categoryLevel1IdCopy=" + categoryLevel1IdCopy +
				", categoryLevel2Id=" + categoryLevel2Id +
				", categoryLevel3Id=" + categoryLevel3Id +
				", fileName='" + fileName + '\'' +
				", isDelete=" + isDelete +
				", createdBy=" + createdBy +
				", creationDate=" + creationDate +
				", modifyBy=" + modifyBy +
				", modifyDate=" + modifyDate +
				", pc1name='" + pc1name + '\'' +
				", pc2name='" + pc2name + '\'' +
				", pc3name='" + pc3name + '\'' +
				", quantity=" + quantity +
				", orders=" + orders +
				'}';
	}
}
