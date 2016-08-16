# Ariba QRCode Application

## Introduction

This is an SAP HANA Cloud Platform Web application that shows how to search for catalog items and submit requisitions using the Ariba P2P API. 

There are two possible usage scenarios for this application:

- [[Scenario 1]](#scenario-1): Scan a QR Code to show details about a specified item and submit a requisition through the application UI.
- [[Scenario 2]](#scenario-2): Scan a QR Code to submit requisition without starting the application UI.

## Prerequisites

1.    Eclipse installed with SAP HANA Cloud Platform Tools plugins.
2.    JDK 1.7 set as an Installed JRE in *Windows > Preferences > Java > Installed JREs*.
3.    Java Web Tomcat 7 set as a runtime environment in *Windows > Preferences > Server > Runtime Environments*.
4.    [Destinations configured for connecting to the Ariba API](#configure-the-destinations).

## Build and Deploy the Application

1. Clone the Git repository.

2. Import the project as a Maven project into your Eclipse workspace. 
>*Note*: Make sure you use JDK 1.7 for this project. You can configure this in the project build path.

3. Run the *requisition-parent* project with Maven goal `clean install`. 

4. If you are deploying the application locally, see [Creating and Deleting Destinations Locally](https://help.hana.ondemand.com/help/frameset.htm?7fa92ffa007346f58491999361928303.html).<br>
If you are deploying it on the Cloud, see [Creating and Deleting Destinations on the Cloud](https://help.hana.ondemand.com/help/frameset.htm?94dddf7d9e56401ba1719b7e836d8ee9.html).

5. Deploy the application. 
>*Note*: Make sure you select the Java Web Tomcat 7 Server as a runtime environment.

6. To access the application, follow the steps described in the [Access the application](#access-the-application) section.

## Configure the Destinations

You need to configure an HTTP Connectivity Destination for this application before running the project.
>*Note*: To learn more about how to configure the destination, see [SAP HANA Cloud Platform Destinations Documentation] (https://help.hana.ondemand.com/help/frameset.htm?e4f1d97cbb571014a247d10f9f9a685d.html).

The HTTP API Destination looks like this:

	Name=ariba-p2p-api
	Description=Ariba P2P SOAP WS API Destination
	Type=HTTP
	URL=https\://<ariba host>/Buyer/soap/...
	User=<endpoint-user>
	Password=<endpoint-password>
	Authentication=BasicAuthentication
	ProxyType=Internet
	CloudConnectorVersion=2
	deliverTo=<deliver to name>
	headerUniqueName=<header>
	shipTo=<shipping address>
	billingAddress=<billing address>
	originatingSystem=<originating system>
	passwordAdapter=PasswordAdapter1
	preparer=<preparer user>
	requester=<requester user>
	originatingSystemId=<system id>
	businessUnit=<business unit>

## Access the application

In both scenarios you need to generate a static QR Code of type URL. You can use your preferred QR Code generator.
If you deploy the application on the cloud, this is the URL pattern that you use to access it: `http://<application><account>.<domain>/qrcode-app/`

If you run the application locally, then use this URL pattern: `http://<host>:<port>/qrcode-app/`.

### Scenario 1

1. Generate the QR Code using this URL pattern: `http://<application><account>.<domain>/qrcode-app/#/item/<item_part_number_in_Ariba>`, or `http://<host>:<port>/qrcode-app/#/item/<item_part_number_in_Ariba`.
2. Use a QR code scanner from your mobile device to show the details about this item in the application UI and submit the requisition.

### Scenario 2

1. Generate the QR Code using this URL pattern: `http://<application><account>.<domain>/qrcode-app/api/v1/requisition/submit?partNumber=<item_part_number_in_Ariba>&quantity=<quantity_of_the_item_to_be_ordered>` or `http://<host>:<port>/qrcode-app/api/v1/requisition/submit?partNumber=<item_part_number_in_Ariba>&quantity=<quantity_of_the_item_to_be_ordered>`.
2. Use a QR code scanner from your mobile device to submit the requisition with the specified item and quantity.

## Resources

* SAP HANA Cloud Platform Documentation - https://help.hana.ondemand.com/

## Copyright and license

Copyright 2016 SAP AG

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
