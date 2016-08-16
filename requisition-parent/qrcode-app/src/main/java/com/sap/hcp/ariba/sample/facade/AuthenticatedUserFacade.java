package com.sap.hcp.ariba.sample.facade;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.UnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hcp.ariba.sample.services.config.AuthenticationConfig;
import com.sap.hcp.ariba.sample.services.config.DestinationProperties;

/**
 * Facade for configuring the user authentication as well as the requested wsdl URL
 */
public class AuthenticatedUserFacade {
	
	protected URL wsdlURL;
	protected String authorization;
	private final String ERROR_FAILED_TO_CONNECT_TO_ARIBA_REQUISITION_API = "Failed to connect to Ariba requisition API. Make sure your Ariba instance URL is set up correctly in the destination. URL: [{}]";
	private final Logger logger = LoggerFactory.getLogger(AuthenticatedUserFacade.class);

	/**
	 * Default constructed in which the wsdlURL is configured as well as the user authentication
	 * @param wsdlName - path to the wsdl
	 * @throws UnavailableException
	 */
	public AuthenticatedUserFacade(String wsdlName) throws UnavailableException {
		DestinationProperties properties = new DestinationProperties();
		try {
			wsdlURL = new URL(properties.getURL() + wsdlName);
			authorization = AuthenticationConfig.getBasicAuthentication(properties.getUser(),
					properties.getPassword());
		} catch (MalformedURLException e) {
			logger.error(ERROR_FAILED_TO_CONNECT_TO_ARIBA_REQUISITION_API, wsdlURL);
		}
	}

}
