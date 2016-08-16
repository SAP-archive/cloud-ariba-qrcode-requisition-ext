package com.sap.hcp.ariba.sample.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.UnavailableException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sap.hcp.ariba.sample.facade.CatalogSearchFacade;
import com.sap.hcp.ariba.sample.facade.RequisitionImportFacade;
import com.sap.hcp.ariba.sample.model.Item;
import com.sap.hcp.ariba.sample.model.Requisition;

import ariba.buyer.vrealm_3.catalog.WSCatalogItem;
import ariba.buyer.vrealm_3.requisition.RequisitionImportPullReply;

@Path("/requisition")
public class RequisitionService {

	@GET
	@Path("submit")
	@Produces("application/json")
	public Response importRequisition(@QueryParam("partNumber") String partNumber,
			@QueryParam("quantity") Double quantity) {

		Response response;
		RequisitionImportPullReply result = null;
		Requisition requisition = new Requisition();
		requisition.setComment("This requisition is imported by HCP on date");
		requisition.setName("HCP XML Req Import");
		requisition.setNeedBy(new Date());

		List<Item> items = new ArrayList<>();

		try {
			WSCatalogItem wsCatalogItem = new CatalogSearchFacade().searchItem(partNumber);
			Item item = new Item().toItem(wsCatalogItem);

			item.setQuantity(quantity);
			items.add(item);
			requisition.setItems(items);
			result = new RequisitionImportFacade().importRequisition(requisition);
		} catch (UnavailableException e) {
			response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e).build();
		}

		if (result == null) {
			response = Response.status(Response.Status.NOT_FOUND).build();
		} else {
			response = Response.status(Response.Status.OK).entity(result).build();
		}

		return response;
	}
}
