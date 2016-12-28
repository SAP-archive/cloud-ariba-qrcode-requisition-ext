package com.sap.hcp.ariba.sample.services;

import java.util.Arrays;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.SOAPMessage;

import com.sap.hcp.ariba.sample.facade.CatalogSearchFacade;
import com.sap.hcp.ariba.sample.facade.RequisitionImportFacade;

import ariba.buyer.catalog.CatalogItem;
import ariba.buyer.requisition.Requisition;
import ariba.buyer.requisition.RequisitionItem;

/**
 * Used to submit requisitions
 *
 */
@Path("/requisition")
public class RequisitionService {

	private static final String EMPTY_STRING = "";
	private static final String COMMA = ",";

	/**
	 * Submits requisition
	 *
	 * @param partNumber
	 *            - supplier part number
	 * @param quantity
	 *            - items count
	 * @return response - HTTP OK 200 for success, HTTP Error 404 Not Found if item is
	 *         not found item or HTTP Error 500 Internal server error for
	 *         requisition submit problem
	 */
	@GET
	@Path("submit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response importRequisition(@QueryParam("partNumber") String partNumber,
			@QueryParam("quantity") Double quantity) {

		Response response = null;
		SOAPMessage result = null;
		Requisition requisition = new Requisition();

		try {
			CatalogItem catalogItem = new CatalogSearchFacade().searchItem(partNumber);
			RequisitionItem item = preapareRequisitionItem(catalogItem);

			item.quantity(quantity);
			requisition.items(Arrays.asList(item));
			result = new RequisitionImportFacade().importRequisition(requisition);
		} catch (Exception e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		if (result == null) {
			response = Response.status(Response.Status.NOT_FOUND).build();
		} else {
			response = Response.status(Response.Status.OK).build();
		}

		return response;
	}

	private RequisitionItem preapareRequisitionItem(CatalogItem catalogItem) {
		return new RequisitionItem().needByDate(new Date()).commodityCode(catalogItem.getClassificationCodeValue())
				.currency(catalogItem.getContractPrice().getCurrency()).description(catalogItem.getDescription())
				.manPartNumber(catalogItem.getManufacturerPartId())
				.itemPrice(getValidatedAmount(catalogItem.getContractPrice().getAmount()))
				.supplierName(catalogItem.getSupplierName()).supplierPartNumber(catalogItem.getSupplierPartId())
				.unitOfMeasure(catalogItem.getUnitOfMeasureValue())
				.numberInCollection(Long.parseLong(catalogItem.getItemNumber()))
				.originatingSystemLineNumber(Long.parseLong(catalogItem.getOriginatingLineNumber()));
	}

	private static Double getValidatedAmount(String amount) {
		return Double.valueOf(amount.replaceAll(COMMA, EMPTY_STRING));
	}

}
