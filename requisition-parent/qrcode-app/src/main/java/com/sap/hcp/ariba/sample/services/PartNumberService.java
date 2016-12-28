package com.sap.hcp.ariba.sample.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sap.hcp.ariba.sample.validators.PartNumberValidator;

/**
 * Used to validate supplier part numbers
 *
 */
@Path("/partNumbers")
public class PartNumberService {

	/**
	 * Check if some of part numbers from parameters is null or undefined and
	 * replace it with default from destination.
	 *
	 * @param partNumber1
	 *            - supplier part number 1
	 * @param partNumber2
	 *            - supplier part number 2
	 * @param partNumber3
	 *            - supplier part number 3
	 * @return response - a list with supplier part numbers or HTTP Error 500 Internal server error
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getValidatedPartNumbers(@QueryParam("partNumber1") String partNumber1,
			@QueryParam("partNumber2") String partNumber2, @QueryParam("partNumber3") String partNumber3) {
		Response response = null;

		try {
			List<String> partNumbers = new PartNumberValidator().getValidatedPartNumbers(partNumber1, partNumber2, partNumber3);

			response = Response.status(Response.Status.OK).entity(partNumbers).build();
		} catch (Exception e) {
			response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

		return response;

	}
}