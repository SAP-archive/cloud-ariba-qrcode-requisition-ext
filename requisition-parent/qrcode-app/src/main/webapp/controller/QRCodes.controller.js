sap.ui.define([ "sap/ui/model/json/JSONModel", "sap/ui/core/mvc/Controller",
		"sap/m/MessageBox", "sap/ui/core/BusyIndicator" ], 
		function(JSONModel, Controller, MessageBox, BusyIndicator) {
	"use strict";
	
	return Controller.extend("sap.hcp.ariba.ext.controller.QRCodes", {
		
		onInit : function() {
			
			var oModel = new JSONModel();
			oModel.setProperty("/items", []);
	        var items = ["23V882", "22ED51", "20RX80"]; 
	        
	        items.forEach(function(partNumber) {
				$.ajax({
					type : "GET",
					url : "/qrcode-app/api/v1/catalog/item?partNumber=" + partNumber,
					beforeSend : BusyIndicator.show()
				}).done(function(data) {
					if (data === undefined || data === "") {
						MessageBox.warning("Catalog item [" + partNumber
							+ "] not found.");
					} else {
						data.quantity = 1;
						oModel.getProperty("/items").push(data);
						oModel.refresh(true);
						if (data.contractPrice.amount === null) {
							MessageBox.warning("Catalog Item [ " + data.shortName
								+ "] is OUT OF STOCK.");
						}
					}	
				}).fail(function(oError) {
					MessageBox.warning("Catalog item [" + partNumber
						+ "] not found.");	
				}).complete(function() {
					BusyIndicator.hide();
				});
			});
	        this.getView().setModel(oModel);
		},
		
		onPress : function(oEvent) {
			
			var partNumber = oEvent.getSource().data("partNumber");
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.navTo("itemDetails", {partNumber: partNumber});
		}
	});
});
