package com.sap.hcp.ariba.sample.services.config;

/**
 * Used to get basic authentication for specified user.
 *
 */
public class AuthenticationConfig {

	/**
	 * returns a basic authentication string used for authentication of the
	 * specified user
	 * 
	 * @param user
	 *            - the user to be authorized
	 * @param password
	 *            - the user's password
	 * @return a user authorization string
	 */
	public static String getBasicAuthentication(String user, String password) {

		StringBuilder authorization = new StringBuilder();
		authorization.append("Basic ").append(user).append(":").append(password);

		return authorization.toString();
	}
}
