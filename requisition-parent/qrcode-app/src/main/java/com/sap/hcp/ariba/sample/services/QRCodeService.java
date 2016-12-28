package com.sap.hcp.ariba.sample.services;

import java.io.ByteArrayInputStream;
import java.text.MessageFormat;

import javax.servlet.UnavailableException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sap.hcp.ariba.sample.facade.QRCodeFacade;

/**
 * Used to generate QR codes
 *
 */
@Path("/qrcode")
public class QRCodeService {

	private static final String QRCODE_APP = "/qrcode-app";
	private static final String TWO_STRINGS_FORMAT = "{0}{1}";

	@Context
	private UriInfo uri;

	/**
	 * Generate QR code
	 *
	 * @param partNumber
	 *            - supplier part number
	 * @return response - ByteArrayInputStream or HTTP Error 500 Internal server error
	 */
	@GET
	@Path("/create")
	@Produces("image/png")
	public Response generateQRCode(@QueryParam("partNumber") String partNumber) {

		String applicationURL = MessageFormat.format(TWO_STRINGS_FORMAT, uri.getBaseUri().getHost(), QRCODE_APP);
		Response response = null;

		try {
			ByteArrayInputStream qrCode = QRCodeFacade.createQRCode(partNumber, applicationURL);
			response = Response.status(Response.Status.OK).entity(qrCode).build();
		} catch (UnavailableException e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return response;
	}

}
