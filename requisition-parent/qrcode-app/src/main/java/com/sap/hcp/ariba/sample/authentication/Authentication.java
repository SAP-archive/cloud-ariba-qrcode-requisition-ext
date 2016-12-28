package com.sap.hcp.ariba.sample.authentication;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hcp.ariba.sample.services.config.DestinationProperties;

/**
 * Class for configuring the user authentication as well as the requested URL
 */
public class Authentication {

	protected URL url;
	protected String user;
	protected String password;

	private final String ERROR_FAILED_TO_CREATE_URL = "Failed to create URL: [{}]";
	private final Logger logger = LoggerFactory.getLogger(Authentication.class);

	/**
	 * Default constructed in which the URL is configured as well as the user
	 * authentication
	 * 
	 * @param servicePath
	 *            - service path
	 */
	public Authentication(String servicePath) {
		DestinationProperties properties = new DestinationProperties();
		try {
			url = new URL(properties.getURL() + servicePath);
			user = properties.getUser();
			password = properties.getPassword();
		} catch (MalformedURLException e) {
			logger.error(ERROR_FAILED_TO_CREATE_URL, url);
		}
	}

}
