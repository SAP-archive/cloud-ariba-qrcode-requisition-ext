package com.sap.hcp.ariba.sample.model;

import ariba.buyer.vrealm_3.catalog.WSCatalogItem;

public class Item {

	private String commodityCode;
	private String manPartNumber;
	private String description;
	private Double price;
	private String currency;
	private String supplierPartNumber;
	private String unitOfMeasure;
	private Double quantity;
	private String supplier;
	private long numberInCollection;
	private long originatingSystemLineNumber;

	/**
	 * Returns the commodity code of the requisition item
	 *
	 * @return the commodity code of the requisition item
	 */
	public String getCommodityCode() {
		return commodityCode;
	}

	/**
	 * Sets the commodity code of the requisition item
	 *
	 * @param commodityCode
	 *            the commodity code of the requisition item
	 */
	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	/**
	 * Returns the man part number of the requisition item
	 *
	 * @return the man part number of the requisition item
	 */
	public String getManPartNumber() {
		return manPartNumber;
	}

	/**
	 * Sets the man part number of the requisition item
	 *
	 * @param manPartNumber
	 *            the man part number of the requisition item
	 */
	public void setManPartNumber(String manPartNumber) {
		this.manPartNumber = manPartNumber;
	}

	/**
	 * Returns the description of the requisition item
	 *
	 * @return the description of the requisition item
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the requisition item
	 *
	 * @param description
	 *            the description of the requisition item
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the price of the requisition item
	 *
	 * @return the price of the requisition item
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * Sets the price of the requisition item
	 *
	 * @param price
	 *            the price of the requisition item
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * Returns the currency of the requisition item
	 *
	 * @return the currency of the requisition item
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency of the requisition item
	 *
	 * @param currency
	 *            the currency of the requisition item
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * Returns the supplier part number of the requisition item
	 *
	 * @return the supplier part number of the requisition item
	 */
	public String getSupplierPartNumber() {
		return supplierPartNumber;
	}

	/**
	 * Returns the supplier part number of the requisition item
	 *
	 * @param supplierPartNumber
	 *            the supplier part number of the requisition item
	 */
	public void setSupplierPartNumber(String supplierPartNumber) {
		this.supplierPartNumber = supplierPartNumber;
	}

	/**
	 * Returns the unit of measure of the requisition item
	 *
	 * @return the unit of measure of the requisition item
	 */
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	/**
	 * Sets the unit of measure of the requisition item
	 *
	 * @param unitOfMeasure
	 *            the unit of measure of the requisition item
	 */
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	/**
	 * Returns the quantity of the requisition item
	 *
	 * @return the quantity of the requisition item
	 */
	public Double getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity of the requisition item
	 *
	 * @param quantity
	 *            the quantity of the requisition item
	 */
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	/**
	 * Returns the supplier of the requisition item
	 *
	 * @return the supplier of the requisition item
	 */
	public String getSupplier() {
		return supplier;
	}

	/**
	 * Sets the supplier of the requisition item
	 *
	 * @param supplier
	 *            the supplier of the requisition item
	 */
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	/**
	 * Returns the item number in collection of the requisition item
	 * 
	 * @return the item number in collection of the requisition item
	 */
	public long getNumberInCollection() {
		return numberInCollection;
	}

	/**
	 * Sets the item number in collection of the requisition item
	 * 
	 * @param numberInCollection
	 *            the item number in collection of the requisition item
	 */
	public void setNumberInCollection(long numberInCollection) {
		this.numberInCollection = numberInCollection;
	}

	/**
	 * Returns the originating system line number of the requisition item
	 * 
	 * @return the originating system line number of the requisition item
	 */
	public long getOriginatingSystemLineNumber() {
		return originatingSystemLineNumber;
	}

	/**
	 * Sets the originating system line number of the requisition item
	 * 
	 * @param originatingSystemLineNumber
	 *            the originating system line number of the requisition item
	 */
	public void setOriginatingSystemLineNumber(long originatingSystemLineNumber) {
		this.originatingSystemLineNumber = originatingSystemLineNumber;
	}

	public Item toItem(WSCatalogItem wsCatalogItem) {

		Item item = new Item();

		item.setCommodityCode(wsCatalogItem.getClassificationCode().getItem().get(0).getValue());
		item.setCurrency(wsCatalogItem.getPrice().getCurrency());
		item.setDescription(wsCatalogItem.getDescription());
		item.setManPartNumber(wsCatalogItem.getManufacturerPartId());
		item.setPrice(wsCatalogItem.getPrice().getAmount().doubleValue());
		item.setSupplier(wsCatalogItem.getSupplierName());
		item.setSupplierPartNumber(wsCatalogItem.getSupplierPartId());
		item.setUnitOfMeasure(wsCatalogItem.getUnitOfMeasure().getValue());
		item.setNumberInCollection(Long.parseLong(wsCatalogItem.getItemNumber()));
		item.setOriginatingSystemLineNumber(Long.parseLong(wsCatalogItem.getOriginatingLineNumber()));

		return item;
	}

}
