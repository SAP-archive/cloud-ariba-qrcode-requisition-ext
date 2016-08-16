package com.sap.hcp.ariba.sample.model;

import java.util.Date;
import java.util.List;

public class Requisition {

	private String comment;
	private String name;
	private String requester;
	private String uniqueName;
	private Date needBy;
	private List<Item> items;

	/**
	 * Returns the comment of the requisition header
	 *
	 * @return the comment of the requisition header
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment of the requisition header
	 *
	 * @param comment
	 *            the comment of the requisition header
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Returns the name of the requisition header
	 *
	 * @return the name of the requisition header
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the requisition header
	 *
	 * @param name
	 *            the name of the requisition header
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getRequester() {
		return requester;
	}

	public void setRequester(String requester) {
		this.requester = requester;
	}

	/**
	 * Gets the unique name.
	 *
	 * @return the unique name
	 */
	public String getUniqueName() {
		return uniqueName;
	}

	/**
	 * Sets the unique name.
	 *
	 * @param uniqueName
	 *            the unique name of the requisition header
	 */
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	/**
	 * Returns the need by date of the requisition header
	 *
	 * @return the need by date of the requisition header
	 */
	public Date getNeedBy() {
		return needBy;
	}

	/**
	 * Sets the need by date of the requisition header
	 *
	 * @param needBy
	 *            the need by date of the requisition header
	 */
	public void setNeedBy(Date needBy) {
		this.needBy = needBy;
	}

	/**
	 * Returns the list of items of the requisition header
	 *
	 * @return the items of the requisition header
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * Sets the items of the requisition header
	 *
	 * @param items
	 *            the list of items of the requisition header
	 */
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
