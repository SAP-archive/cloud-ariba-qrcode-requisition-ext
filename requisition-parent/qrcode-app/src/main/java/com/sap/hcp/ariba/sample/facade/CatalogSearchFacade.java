package com.sap.hcp.ariba.sample.facade;

import java.net.URL;
import java.util.List;

import javax.servlet.UnavailableException;
import javax.xml.soap.SOAPException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hcp.ariba.sample.authentication.Authentication;
import com.sap.hcp.ariba.sample.services.config.DestinationProperties;

import ariba.buyer.catalog.CatalogItem;
import ariba.buyer.catalog.CatalogSOAPClient;

/**
 * Facade for Catalog Search API
 *
 */
public class CatalogSearchFacade extends Authentication {

	private static final String WS_CATALOG_ITEM_SEARCH_PATH = "/WSCatalogItemSearch";

	private static final String SEARCH_FIELD_MATCH_ALL = "MatchAll";
	private static final String SEARCH_FIELD_LIKE = "like";

	private static final String DEBUG_CATALOG_ITEM_WAS_NOT_FOUND = "A result for catalog item search for field [{}], operator [{}], item number [{}], user [{}] was not found.";

	private static final Logger logger = LoggerFactory.getLogger(CatalogSearchFacade.class);

	/**
	 * CatalogSearchFacade constructor
	 *
	 * @param servicePath
	 *            - service path
	 */
	public CatalogSearchFacade(String servicePath) {
		super(servicePath);
	}

	/**
	 * CatalogSearchFacade default constructor
	 *
	 */
	public CatalogSearchFacade() {
		this(WS_CATALOG_ITEM_SEARCH_PATH);
	}

	/**
	 * Search catalog item by specified part number
	 * 
	 * @param partNumber
	 *            - the part number of the item to be searched for.
	 * @return Catalog Item containing details about the searched item or null
	 *         if the item is not found.
	 * @throws SOAPException
	 *             if there is a problem with catalog searching.
	 * 
	 */
	public CatalogItem searchItem(String partNumber) throws SOAPException, UnavailableException {
		DestinationProperties destinationProperties = new DestinationProperties();

		return searchCatalogItem(SEARCH_FIELD_MATCH_ALL, SEARCH_FIELD_LIKE, partNumber,
				destinationProperties.getRequester(), user, password, url,
				destinationProperties.getNamespaceXMLNSvariant());
	}

	private CatalogItem searchCatalogItem(String field, String operator, String itemNumber, String requester,
			String user, String password, URL url, String namespaceXMLNSvariant) throws SOAPException {

		CatalogItem catalogItem = null;

		List<CatalogItem> catalogItems = CatalogSOAPClient.search(field, operator, itemNumber, requester, user,
				password, url, namespaceXMLNSvariant);

		if (catalogItems != null && !catalogItems.isEmpty()) {
			CatalogItem firstCatalogItem = catalogItems.iterator().next();
			if (firstCatalogItem.getSupplierPartId().equals(itemNumber)) {
				catalogItem = firstCatalogItem;
			}
		}

		if (catalogItem == null) {
			logger.debug(DEBUG_CATALOG_ITEM_WAS_NOT_FOUND, field, operator, itemNumber, requester);
		}

		return catalogItem;
	}
}
