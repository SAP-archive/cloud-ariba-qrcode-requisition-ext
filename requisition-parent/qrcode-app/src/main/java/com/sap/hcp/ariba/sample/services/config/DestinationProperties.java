package com.sap.hcp.ariba.sample.services.config;

import javax.servlet.UnavailableException;

public class DestinationProperties {

	private static final String REQUISITION_NAME_DEST_PARAMETER = "requisitionName";
	private static final String REQUISITION_COMMENT_DEST_PARAMETER = "requisitionComment";
	private static final String URL_DEST_PARAMETER = "URL";
	private static final String PASSWORD_DEST_PARAMETER = "Password";
	private static final String USER_DEST_PARAMETER = "User";
	private static final String CURRENCY_DEST_PARAMETER = "currency";
	private static final String COMMODITY_CODE_DEST_PARAMETER = "commodityCode";
	private static final String HEADER_UNIQUE_NAME_DEST_PARAMETER = "headerUniqueName";
	private static final String PASSWORD_ADAPTER_DEST_PARAMETER = "passwordAdapter";
	private static final String ORIGINATING_SYSTEM_ID_DEST_PARAMETER = "originatingSystemId";
	private static final String ORIGINATING_SYSTEM_DEST_PARAMETER = "originatingSystem";
	private static final String DELIVER_TO_DEST_PARAMETER = "deliverTo";
	private static final String BUSINESS_UNIT_DEST_PARAMETER = "businessUnit";
	private static final String SHIP_TO_DEST_PARAMETER = "shipTo";
	private static final String REQUESTER_DEST_PARAMETER = "requester";
	private static final String PREPARER_DEST_PARAMETER = "preparer";
	private static final String BILLING_ADDRESS_DEST_PARAMETER = "billingAddress";
	private static ConnectivityConfig connectivityConfiguration;

	public DestinationProperties() throws UnavailableException {
		if (connectivityConfiguration == null) {
			connectivityConfiguration = new ConnectivityConfig();
		}
	}

	public String getRequisitionName() {
		return getProperty(REQUISITION_NAME_DEST_PARAMETER);
	}

	public String getRequisitionComment() {
		return getProperty(REQUISITION_COMMENT_DEST_PARAMETER);
	}

	public String getURL() {
		return getProperty(URL_DEST_PARAMETER);
	}

	public String getCurrency() {
		return getProperty(CURRENCY_DEST_PARAMETER);
	}

	public String getCommodityCode() {
		return getProperty(COMMODITY_CODE_DEST_PARAMETER);
	}

	public String getPassword() {
		return getProperty(PASSWORD_DEST_PARAMETER);
	}

	public String getUser() {
		return getProperty(USER_DEST_PARAMETER);
	}

	public String getHeaderUniqueName() {
		return getProperty(HEADER_UNIQUE_NAME_DEST_PARAMETER);
	}

	public String getPasswordAdapter() {
		return getProperty(PASSWORD_ADAPTER_DEST_PARAMETER);
	}

	public String getOrigintingSystemId() {
		return getProperty(ORIGINATING_SYSTEM_ID_DEST_PARAMETER);
	}

	public String getOriginatingSystem() {
		return getProperty(ORIGINATING_SYSTEM_DEST_PARAMETER);
	}

	public String getDeliverTo() {
		return getProperty(DELIVER_TO_DEST_PARAMETER);
	}

	public String getBusinessUnit() {
		return getProperty(BUSINESS_UNIT_DEST_PARAMETER);
	}

	public String getShipTo() {
		return getProperty(SHIP_TO_DEST_PARAMETER);
	}

	public String getPreparer() {
		return getProperty(PREPARER_DEST_PARAMETER);
	}

	public String getRequester() {
		return getProperty(REQUESTER_DEST_PARAMETER);
	}

	public String getBillingAddress() {
		return getProperty(BILLING_ADDRESS_DEST_PARAMETER);
	}

	private String getProperty(String propertyName) {
		return connectivityConfiguration.getDestinationPropertyValue(propertyName);
	}
}
