package com.sap.hcp.ariba.sample.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 * Used to group all services in one application
 *
 */
public class App extends Application {

	@Override
	public Set<Class<?>> getClasses() {

		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(JacksonJsonProvider.class);
		classes.add(RequisitionService.class);
		classes.add(CatalogService.class);
		classes.add(QRCodeService.class);
		classes.add(PartNumberService.class);

		return classes;
	}
}
