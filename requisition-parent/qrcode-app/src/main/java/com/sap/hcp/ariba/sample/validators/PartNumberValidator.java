package com.sap.hcp.ariba.sample.validators;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.UnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.hcp.ariba.sample.services.config.DestinationProperties;

/**
 * Facade for part numbers validation
 *
 */
public class PartNumberValidator {

	private static final String EMPTY_STRING = "";
	private static final String UNDEFINED = "undefined";

	private static final String DEBUG_PART_NUMBER_ITEMS_AFTER_VALIDATING_MESSAGE = "Part number items after validating: {}, {}, {}.";
	private static final String DEBUG_PART_NUMBER_ITEMS_BEFORE_VALIDATING_MESSAGE = "Part number items before validating: {}, {}, {}.";

	private static final Logger logger = LoggerFactory.getLogger(PartNumberValidator.class);

	/**
	 * One part number is invalid if it is null, 'undefined' or empty string.
	 * Each invalid part number will be replaced with default part number set in
	 * the destination.
	 * 
	 * @param partNumber1
	 *            part number 1
	 * @param partNumber2
	 *            part number 2
	 * @param partNumber3
	 *            part number 3
	 * @return List with validated parameters.
	 */
	public List<String> getValidatedPartNumbers(String partNumber1, String partNumber2, String partNumber3)
			throws UnavailableException {
		logger.debug(DEBUG_PART_NUMBER_ITEMS_BEFORE_VALIDATING_MESSAGE, partNumber1, partNumber2, partNumber3);

		DestinationProperties destinationProperties = new DestinationProperties();
		List<String> partNumbers = new ArrayList<>();

		if (isNotValid(partNumber1)) {
			partNumber1 = destinationProperties.getPartNumberItem1();
		}
		if (isNotValid(partNumber2)) {
			partNumber2 = destinationProperties.getPartNumberItem2();
		}
		if (isNotValid(partNumber3)) {
			partNumber3 = destinationProperties.getPartNumberItem3();
		}

		logger.debug(DEBUG_PART_NUMBER_ITEMS_AFTER_VALIDATING_MESSAGE, partNumber1, partNumber2, partNumber3);

		partNumbers.add(partNumber1);
		partNumbers.add(partNumber2);
		partNumbers.add(partNumber3);

		return partNumbers;
	}

	private boolean isNotValid(String partNumber) {
		return partNumber == null || partNumber.equalsIgnoreCase(UNDEFINED) || partNumber.equals(EMPTY_STRING);
	}

}
