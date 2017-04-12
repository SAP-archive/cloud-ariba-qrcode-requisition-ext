package com.sap.hcp.ariba.sample.facade;

import java.util.Date;
import java.util.List;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hcp.ariba.sample.authentication.Authentication;
import com.sap.hcp.ariba.sample.services.config.DestinationProperties;

import ariba.buyer.requisition.Requisition;
import ariba.buyer.requisition.RequisitionItem;
import ariba.buyer.requisition.RequisitionSOAPClient;

/**
 * Facade for Requisition Import API.
 *
 */
public class RequisitionImportFacade extends Authentication {

	private static final String REQUISITION_IMPORT_PULL_PATH = "/RequisitionImportPull";

	private static final String ERROR_EXCEPTION_OCCURRED_WHILE_TRYING_TO_SUBMIT_THE_REQUISITON = "Exception occurred while trying to submit the requisiton";

	private static final Logger logger = LoggerFactory.getLogger(RequisitionImportFacade.class);

	/**
	 * CatalogSearchFacade constructor
	 *
	 * @param servicePath
	 *            - service path
	 */
	public RequisitionImportFacade(String servicePath) {
		super(servicePath);
	}

	/**
	 * CatalogSearchFacade default constructor
	 *
	 */
	public RequisitionImportFacade() {
		this(REQUISITION_IMPORT_PULL_PATH);
	}

	/**
	 * Import requisition for approval.
	 *
	 * @param requisition
	 *            - the requisition to be imported.
	 * @return SOAPMessage if the requisition is submitted successfully.
	 * @throws SOAPException
	 *             if the requisition is not submitted successfully.
	 */
	public SOAPMessage importRequisition(Requisition requisition) throws SOAPException {
		SOAPMessage submitRequisition = null;
		DestinationProperties destinationProperties = new DestinationProperties();
		try {
			Requisition preparedRequisition = prepareRequisition(requisition, destinationProperties);

			submitRequisition = RequisitionSOAPClient.submit(url, preparedRequisition, user, password);
		} catch (SOAPException e) {
			logger.error(ERROR_EXCEPTION_OCCURRED_WHILE_TRYING_TO_SUBMIT_THE_REQUISITON, e);
			throw e;
		}

		return submitRequisition;
	}

	private Requisition prepareRequisition(Requisition requisition, DestinationProperties destinationProperties) {
		String requester = (requisition.getRequester() != null) ? requisition.getRequester()
				: destinationProperties.getRequester();
		String preparer = (requisition.getPreparer() != null) ? requisition.getPreparer()
				: destinationProperties.getPreparer();

		List<RequisitionItem> items = requisition.getItems();
		List<RequisitionItem> RequisitionItemList = prepareRequisitionItems(items, destinationProperties);

		requisition.needByDate(new Date()).headerComment(destinationProperties.getRequisitionComment())
				.headerName(destinationProperties.getRequisitionName()).shipTo(destinationProperties.getShipTo())
				.businessUnit(destinationProperties.getBusinessUnit()).deliverTo(destinationProperties.getDeliverTo())
				.originatingSystem(destinationProperties.getOriginatingSystem())
				.originatingSystemReferenceId(String.valueOf(System.currentTimeMillis()))
				.passwordAdapter(destinationProperties.getPasswordAdapter()).preparer(preparer).requester(requester)
				.headerUniqueName(destinationProperties.getHeaderUniqueName())
				.namespaceXMLNSvariant(destinationProperties.getNamespaceXMLNSvariant()).items(RequisitionItemList);

		return requisition;
	}

	private List<RequisitionItem> prepareRequisitionItems(List<RequisitionItem> prepareItems,
			DestinationProperties destinationProperties) {

		for (RequisitionItem item : prepareItems) {
			item.shipTo(destinationProperties.getShipTo()).deliverTo(destinationProperties.getDeliverTo())
					.billingAddress(destinationProperties.getBillingAddress())
					.commonCommodityCodeDomain(destinationProperties.getCommonCommodityCodeDomain())
					.commonCommodityCodeName(destinationProperties.getCommonCommodityCodeName());

		}

		return prepareItems;
	}
}
