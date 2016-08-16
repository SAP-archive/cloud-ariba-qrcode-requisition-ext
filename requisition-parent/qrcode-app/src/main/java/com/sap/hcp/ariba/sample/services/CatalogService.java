package com.sap.hcp.ariba.sample.services;

import javax.servlet.UnavailableException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sap.hcp.ariba.sample.facade.CatalogSearchFacade;

import ariba.buyer.vrealm_3.catalog.WSCatalogItem;

@Path("/catalog")
public class CatalogService {

	@GET
	@Path("/item")
	@Produces("application/json")
	public Response searchItemByPartNumber(@QueryParam("partNumber") String partNumber) {

		Response response;

		try {
			WSCatalogItem wsCatalogItem = new CatalogSearchFacade().searchItem(partNumber);
			response = Response.status(Response.Status.OK).entity(wsCatalogItem).build();
		} catch (UnavailableException e) {
			response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e).build();
		}

		return response;
	}
}