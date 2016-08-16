package com.sap.hcp.ariba.sample.facade;

import java.net.URL;
import java.util.List;

import javax.servlet.UnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hcp.ariba.sample.services.config.DestinationProperties;

import ariba.buyer.vrealm_3.catalog.WSCatalogItem;
import ariba.buyer.vrealm_3.catalog.WSCatalogItemSearchPortType_WSCatalogItemSearchPortType_Client;
import ariba.buyer.vrealm_3.catalog.WSCatalogItemSearchReply;

/**
 * Facade for Catalog Search API
 *
 */
public class CatalogSearchFacade extends AuthenticatedUserFacade{

	private static final String WS_CATALOG_ITEM_SEARCH_WSDL = "/WSCatalogItemSearch?wsdl";

	private static final String SEARCH_FIELD_MATCH_ALL = "MatchAll";
	private static final String SEARCH_FIELD_LIKE = "like";

	private static final String DEBUG_CATALOG_ITEM_WAS_NOT_FOUND = "A result for catalog item search for field [{}], operator [{}], item number [{}], user [{}] was not found.";

	private static final Logger logger = LoggerFactory.getLogger(CatalogSearchFacade.class);
	
	public CatalogSearchFacade(String wsdlName) throws UnavailableException {
		super(wsdlName);
	}
	
	public CatalogSearchFacade() throws UnavailableException {
		this(WS_CATALOG_ITEM_SEARCH_WSDL);
	}

	/**
	 * Search Catalog Item by specified part number
	 * 
	 * @param partNumber
	 *            - the part number if the item to be searched for.
	 * @return Catalog Item containing details about the searched item or null
	 *         if the item is not found.
	 * @throws UnavailableException
	 *             - thrown if connectivity configuration failed.
	 */
	public WSCatalogItem searchItem(String partNumber) throws UnavailableException {

		WSCatalogItem wsCatalogItem = null;
		DestinationProperties destinationProperties = new DestinationProperties();

		if(wsdlURL != null && authorization != null) { 
			wsCatalogItem = searchCatalogItem(SEARCH_FIELD_MATCH_ALL, SEARCH_FIELD_LIKE, partNumber,
					destinationProperties.getRequester(), authorization, wsdlURL);
		}

		return wsCatalogItem;
	}

	private WSCatalogItem searchCatalogItem(String valueField, String valueOperator, String valueItemNumber,
			String valueUser, String authorization, URL wsdlURL) throws UnavailableException {

		WSCatalogItem catalogItem = null;

		WSCatalogItemSearchReply _wsCatalogItemSearchOperation__return = WSCatalogItemSearchPortType_WSCatalogItemSearchPortType_Client
				.searchInCatalog(wsdlURL, valueField, valueOperator, valueItemNumber, valueUser, authorization);

		List<WSCatalogItem> catalogItems = _wsCatalogItemSearchOperation__return.getWSCatalogSearchResponseItem()
				.getItem().getCatalogItems().getItem();

		if (!catalogItems.isEmpty()) {
			WSCatalogItem firstCatalogItem = catalogItems.iterator().next();
			if (firstCatalogItem.getSupplierPartId().equals(valueItemNumber)) {
				catalogItem = firstCatalogItem;
			}
		}

		if (catalogItem == null) {
			logger.debug(DEBUG_CATALOG_ITEM_WAS_NOT_FOUND, valueField, valueOperator, valueItemNumber, valueUser);
		}

		return catalogItem;
	}
}