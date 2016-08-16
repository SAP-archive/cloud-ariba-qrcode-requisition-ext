package com.sap.hcp.ariba.sample.services;

import java.io.ByteArrayInputStream;

import javax.servlet.UnavailableException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sap.hcp.ariba.sample.facade.QRCodeFacade;

@Path("/qrcode")
public class QRCodeService {

	@Context
	UriInfo uri;

	@GET
	@Path("/create")
	@Produces("image/png")
	public Response generateQRCode(@QueryParam("partNumber") String partNumber) {

		String applicationURL = uri.getBaseUri().getHost() + "/qrcode-app";
		Response response = null;

		try {
			ByteArrayInputStream qrCode = QRCodeFacade.createQRCode(partNumber, applicationURL);
			response = Response.status(Response.Status.OK).entity(qrCode).build();
		} catch (UnavailableException e) {
			response = Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e).build();
		}

		return response;
	}

}
