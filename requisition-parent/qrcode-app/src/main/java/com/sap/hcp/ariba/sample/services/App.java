package com.sap.hcp.ariba.sample.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

/**
 * Used to group all services in one application
 *
 */
public class App extends Application {

	private Set<Object> singletons = new HashSet<>();

	/**
	 * Constructor used to initialize all services.
	 */
	public App() {

		setSingletons();
	}

	private void setSingletons() {

		getSingletons().add(new JacksonJaxbJsonProvider());
		getSingletons().add(new RequisitionService());
		getSingletons().add(new CatalogService());
		getSingletons().add(new QRCodeService());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.ws.rs.core.Application#getSingletons()
	 */
	@Override
	public Set<Object> getSingletons() {

		return singletons;
	}
}
