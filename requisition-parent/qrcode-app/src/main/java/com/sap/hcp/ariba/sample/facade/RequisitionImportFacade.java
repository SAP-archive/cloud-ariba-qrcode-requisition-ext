package com.sap.hcp.ariba.sample.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.UnavailableException;
import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hcp.ariba.sample.model.Item;
import com.sap.hcp.ariba.sample.model.Requisition;
import com.sap.hcp.ariba.sample.services.config.DestinationProperties;

import ariba.buyer.vrealm_3.requisition.PrepareItemParameter;
import ariba.buyer.vrealm_3.requisition.PrepareRequisitionParameter;
import ariba.buyer.vrealm_3.requisition.RequisitionImportPullPortType_RequisitionImportPullPortType_Client;
import ariba.buyer.vrealm_3.requisition.RequisitionImportPullReply;

/**
 * Facade for Requisition Import API.
 *
 */
public class RequisitionImportFacade extends AuthenticatedUserFacade {

	private static final String REQUISITION_IMPORT_PULL_WSDL = "/RequisitionImportPull?wsdl";

	private static final String ERROR_EXCEPTION_OCCURRED_WHILE_TRYING_TO_SUBMIT_THE_REQUISITON = "Exception occurred while trying to submit the requisiton";

	private static final Logger logger = LoggerFactory.getLogger(RequisitionImportFacade.class);

	public RequisitionImportFacade(String wsdlName) throws UnavailableException {
		super(wsdlName);
	}

	public RequisitionImportFacade() throws UnavailableException {
		this(REQUISITION_IMPORT_PULL_WSDL);
	}

	/**
	 * Import requisition for approval.
	 * 
	 * @param requisition
	 *            - the requisition to be imported.
	 * @return - true if the requisition is submitted successfully and false
	 *         otherwise.
	 * @throws UnavailableException
	 *             - thrown if connectivity configuration failed.
	 */
	public RequisitionImportPullReply importRequisition(Requisition requisition) throws UnavailableException {
		RequisitionImportPullReply submitRequisition = null;
		DestinationProperties destinationProperties = new DestinationProperties();
		try {
			requisition.setComment(destinationProperties.getRequisitionComment());
			requisition.setName(destinationProperties.getRequisitionName());
			requisition.setNeedBy(new Date());
			PrepareRequisitionParameter reqParameter = prepareRequisitionParameter(requisition, destinationProperties);
			if (wsdlURL != null && authorization != null) {
				submitRequisition = RequisitionImportPullPortType_RequisitionImportPullPortType_Client
						.submitRequisition(wsdlURL, reqParameter, authorization);
			}
		} catch (DatatypeConfigurationException e) {

			logger.error(ERROR_EXCEPTION_OCCURRED_WHILE_TRYING_TO_SUBMIT_THE_REQUISITON, e);
			return submitRequisition;
		}

		return submitRequisition;
	}

	private PrepareRequisitionParameter prepareRequisitionParameter(Requisition requisition,
			DestinationProperties destinationProperties) {

		Date needByDate = requisition.getNeedBy();
		String headerComment = requisition.getComment();
		String headerName = requisition.getName();

		String requester = (requisition.getRequester() != null) ? requisition.getRequester()
				: destinationProperties.getRequester();
		String preparer = (requisition.getRequester() != null) ? requisition.getRequester()
				: destinationProperties.getPreparer();

		List<Item> items = requisition.getItems();

		PrepareRequisitionParameter reqParameter = new PrepareRequisitionParameter(needByDate,
				destinationProperties.getShipTo(), destinationProperties.getBusinessUnit(),
				destinationProperties.getDeliverTo(), headerComment, headerName,
				destinationProperties.getOriginatingSystem(), destinationProperties.getOrigintingSystemId(),
				destinationProperties.getPasswordAdapter(), preparer, requester,
				destinationProperties.getHeaderUniqueName());

		reqParameter.items = prepareRequisitionItems(items, reqParameter, destinationProperties);

		return reqParameter;
	}

	private List<PrepareItemParameter> prepareRequisitionItems(List<Item> items,
			PrepareRequisitionParameter reqParameter, DestinationProperties destinationProperties) {

		List<PrepareItemParameter> prepareItems = new ArrayList<>();

		for (Item item : items) {

			String description = item.getDescription();
			String manPartNumber = item.getManPartNumber();
			String commodityCode = item.getCommodityCode();
			String currency = item.getCurrency();
			Double quantity = item.getQuantity();
			String supplier = item.getSupplier();
			String supplierPartNumber = item.getSupplierPartNumber();
			String unitOfMeasure = item.getUnitOfMeasure();
			String itemComment = "This line item is imported by HCP on " + new Date();
			Double itemPrice = item.getPrice();
			long numberInCollection = item.getNumberInCollection();
			long originatingSystemLineNumber = item.getOriginatingSystemLineNumber();

			PrepareItemParameter itemParameter = new PrepareItemParameter(reqParameter.needByDate, reqParameter.shipTo,
					reqParameter.deliverTo, commodityCode, destinationProperties.getBillingAddress(), manPartNumber,
					description, currency, supplierPartNumber, unitOfMeasure, itemComment, supplier, null, null, null,
					quantity, itemPrice, numberInCollection, originatingSystemLineNumber);

			prepareItems.add(itemParameter);
		}

		return prepareItems;
	}
}
