package com.sap.hcp.ariba.sample.facade;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.UnavailableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * Facade used to create QR codes based on Catalog Item Part Number
 */
public class QRCodeFacade {

	private static final String FILE_FORMAT_PNG = "png";
	private static final String ENCODING_UTF_8 = "UTF-8";
	private static final String ERROR_CONVERTING__FAILED = "Converting QR Code to image failed. Check if the image to be saved in the QR Code is creaated properly. Image: [ {} ]";
	private static final String ERROR_ENCODING_FAILED = "QR code encoding failed. Check if the data to be saved in the QR Code is correct. Data: [ {} ]";
	private static final String ERROR_INVALID_URL = "The URL to be saved in the QR Code is not valid. URL: [ {} ]";
	private static final Logger logger = LoggerFactory.getLogger(QRCodeFacade.class);

	/**
	 * Creates QR Code which will be used to open the application UI with
	 * details about specified item
	 * 
	 * @param partNumber
	 *            - Catalog Item Part Number
	 * @param applicationURL
	 *            - the url host of the started application
	 * @return ByteArrayInputStream representing QR Code image containing URL
	 *         used to redirect to Catalog Item details page in the application
	 *         UI.
	 * @throws UnavailableException
	 *             - thrown if the QR Code is not created properly.
	 */
	public static ByteArrayInputStream createQRCode(String partNumber, String applicationURL)
			throws UnavailableException {

		URI QRData = null;
		int size = 250;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedImage image = null;

		try {

			QRData = new URI("http://" + applicationURL + "/#/item/" + partNumber);
			Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);

			hintMap.put(EncodeHintType.CHARACTER_SET, ENCODING_UTF_8);
			hintMap.put(EncodeHintType.MARGIN, 1);
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(QRData.toString(), BarcodeFormat.QR_CODE, size, size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			image = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);

			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}

			ImageIO.write(image, FILE_FORMAT_PNG, baos);

		} catch (URISyntaxException e) {

			String errorMessage = MessageFormatter.format(ERROR_INVALID_URL, QRData).toString();
			logger.error(errorMessage);
			throw new UnavailableException(errorMessage);

		} catch (WriterException e) {

			String errorMessage = MessageFormatter.format(ERROR_ENCODING_FAILED, QRData).toString();
			logger.error(errorMessage);
			throw new UnavailableException(errorMessage);

		} catch (IOException e) {

			String errorMessage = MessageFormatter.format(ERROR_CONVERTING__FAILED, image).toString();
			logger.error(errorMessage);
			throw new UnavailableException(errorMessage);

		}

		return new ByteArrayInputStream(baos.toByteArray());
	}

}
