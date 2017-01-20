package com.sap.hcp.ariba.sample.services.config;

public class DestinationProperties {

	private static final String COMMON_COMMODITY_CODE_DOMAIN = "CommonCommodityCodeDomain";
	private static final String COMMON_COMMODITY_CODE_NAME = "CommonCommodityCodeName";
	private static final String NAMESPACE_XMLN_SVARIANT = "namespaceXMLNSvariant";
	private static final String REQUISITION_NAME = "requisitionName";
	private static final String REQUISITION_COMMENT = "requisitionComment";
	private static final String URL = "URL";
	private static final String PASSWORD = "Password";
	private static final String USER = "User";
	private static final String CURRENCY = "currency";
	private static final String COMMODITY_CODE = "commodityCode";
	private static final String HEADER_UNIQUE_NAME = "headerUniqueName";
	private static final String PASSWORD_ADAPTER = "passwordAdapter";
	private static final String ORIGINATING_SYSTEM_ID = "originatingSystemId";
	private static final String ORIGINATING_SYSTEM = "originatingSystem";
	private static final String DELIVER_TO = "deliverTo";
	private static final String BUSINESS_UNIT = "businessUnit";
	private static final String SHIP_TO = "shipTo";
	private static final String REQUESTER = "requester";
	private static final String PREPARER = "preparer";
	private static final String BILLING_ADDRESS = "billingAddress";
	private static final String PART_NUMBER_ITEM1 = "partNumberItem1";
	private static final String PART_NUMBER_ITEM2 = "partNumberItem2";
	private static final String PART_NUMBER_ITEM3 = "partNumberItem3";

	private static DestinationUtils connectivityConfiguration;

	public DestinationProperties() {
		connectivityConfiguration = new DestinationUtils();
	}

	public String getRequisitionName() {
		return getProperty(REQUISITION_NAME);
	}

	public String getRequisitionComment() {
		return getProperty(REQUISITION_COMMENT);
	}

	public String getURL() {
		return getProperty(URL);
	}

	public String getCurrency() {
		return getProperty(CURRENCY);
	}

	public String getCommodityCode() {
		return getProperty(COMMODITY_CODE);
	}

	public String getPassword() {
		return getProperty(PASSWORD);
	}

	public String getUser() {
		return getProperty(USER);
	}

	public String getHeaderUniqueName() {
		return getProperty(HEADER_UNIQUE_NAME);
	}

	public String getPasswordAdapter() {
		return getProperty(PASSWORD_ADAPTER);
	}

	public String getOrigintingSystemId() {
		return getProperty(ORIGINATING_SYSTEM_ID);
	}

	public String getOriginatingSystem() {
		return getProperty(ORIGINATING_SYSTEM);
	}

	public String getDeliverTo() {
		return getProperty(DELIVER_TO);
	}

	public String getBusinessUnit() {
		return getProperty(BUSINESS_UNIT);
	}

	public String getShipTo() {
		return getProperty(SHIP_TO);
	}

	public String getPreparer() {
		return getProperty(PREPARER);
	}

	public String getRequester() {
		return getProperty(REQUESTER);
	}

	public String getBillingAddress() {
		return getProperty(BILLING_ADDRESS);
	}

	public String getCommonCommodityCodeDomain() {
		return getProperty(COMMON_COMMODITY_CODE_DOMAIN);
	}

	public String getCommonCommodityCodeName() {
		return getProperty(COMMON_COMMODITY_CODE_NAME);
	}

	public String getNamespaceXMLNSvariant() {
		return getProperty(NAMESPACE_XMLN_SVARIANT);
	}

	public String getPartNumberItem1() {
		return getProperty(PART_NUMBER_ITEM1);
	}

	public String getPartNumberItem2() {
		return getProperty(PART_NUMBER_ITEM2);
	}

	public String getPartNumberItem3() {
		return getProperty(PART_NUMBER_ITEM3);
	}

	private String getProperty(String propertyName) {
		return connectivityConfiguration.getDestinationPropertyValue(propertyName);
	}
}
