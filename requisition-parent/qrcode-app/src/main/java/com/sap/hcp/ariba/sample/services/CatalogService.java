package com.sap.hcp.ariba.sample.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sap.hcp.ariba.sample.facade.CatalogSearchFacade;

import ariba.buyer.catalog.CatalogItem;

/**
 * Used to search items from catalog
 *
 */
@Path("/catalog")
public class CatalogService {

	/**
	 * Search item by supplier part number
	 *
	 * @param partNumber
	 *            - supplier part number
	 * @return response - CatalogItem or HTTP Error 500 Internal server error
	 */
	@GET
	@Path("/item")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchItemByPartNumber(@QueryParam("partNumber") String partNumber) {

		Response response;

		try {
			CatalogItem catalogItem = new CatalogSearchFacade().searchItem(partNumber);
			response = Response.status(Response.Status.OK).entity(catalogItem).build();
		} catch (Exception e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return response;
	}
}