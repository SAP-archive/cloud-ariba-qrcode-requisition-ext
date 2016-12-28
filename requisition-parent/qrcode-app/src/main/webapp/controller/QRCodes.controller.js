sap.ui.define([ "sap/ui/model/json/JSONModel", "sap/ui/core/mvc/Controller",
		"sap/m/MessageBox", "sap/ui/core/BusyIndicator" ], 
		function(JSONModel, Controller, MessageBox, BusyIndicator) {
	"use strict";
	
	return Controller.extend("sap.hcp.ariba.ext.controller.QRCodes", {
		
		onInit : function() {
			        
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);

			this.getView().setModel(new JSONModel(), "view");
			oRouter.getRoute("home").attachMatched(this._onRouteMatched, this);

		},

		_onRouteMatched : function (oEvent) {
			var oArgs, oView, oQuery;

			var oModel = new JSONModel();
			oModel.setProperty("/items", []);

			oArgs = oEvent.getParameter("arguments");
			oView = this.getView();

			oQuery = oArgs["?query"];
			if (oQuery && oQuery.partNumber1 && oQuery.partNumber2 && oQuery.partNumber3){
				 var items = [oQuery.partNumber1, oQuery.partNumber2, oQuery.partNumber3]; 
			        
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
			        oView.setModel(oModel);
			} else {
				var partNumber1;
		        var partNumber2;
		        var partNumber3;
		        
		        if(oQuery){
		        	partNumber1 = oQuery.partNumber1;
		        	partNumber2 = oQuery.partNumber2;
		        	partNumber3 = oQuery.partNumber3;
		        }

		        var rout = sap.ui.core.UIComponent.getRouterFor(this);
				$.ajax({
	 	        	type : "GET",
	 	        	url : "/qrcode-app/api/v1/partNumbers?partNumber1=" + partNumber1 + "&partNumber2=" + partNumber2 + "&partNumber3=" + partNumber3,
	 	        	async : false,
	 	        	beforeSend : BusyIndicator.show(),
	 	        }).done(function(data) {
					if (data === undefined || data === "") {
						MessageBox.warning("Catalog items not found.");
					} else {
						var currenturl = location.href;
						var url = currenturl + "?partNumber1=" + data[0] + "&partNumber2=" + data[1] + "&partNumber3=" + data[2];
						rout.navTo("home", {
							query: {
								partNumber1 : data[0],
								partNumber2 : data[1],
								partNumber3 : data[2],
							}
						},true /*no history*/);
					}	
				}).fail(function(oError) {
					MessageBox.warning("Catalog items not found.");	
				}).complete(function() {
					BusyIndicator.hide();
				});
			}
		},
		
		onPress : function(oEvent) {
			
			var partNumber = oEvent.getSource().data("partNumber");
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			window.history.pushState("object or string", "Title", "/qrcode-app/" + window.location.href.substring(window.location.href.lastIndexOf('/') + 1).split("?")[0]);
			oRouter.navTo("itemDetails", {partNumber: partNumber});
		},

	});
});
