sap.ui.define([ "sap/ui/model/json/JSONModel", "sap/ui/core/mvc/Controller",
		"sap/m/MessageBox", "sap/ui/core/BusyIndicator" ], 
		function(JSONModel, Controller, MessageBox, BusyIndicator) {
	"use strict";
	
	return Controller.extend("sap.hcp.ariba.ext.controller.ItemDetails", {

		onInit : function() {
			var oRouter = sap.ui.core.UIComponent.getRouterFor(this);
			oRouter.getRoute("itemDetails").attachPatternMatched(this.searchItem, this);	
		},

		increaseQty : function() {
			var oModel = new JSONModel();
			var data = this.getView().getModel().getData();
			data.quantity++;
			oModel.setData(data);
			this.getView().setModel(oModel);
		},

		decreaseQty : function() {
			var data = this.getView().getModel().getData();
			if (data.quantity > 1) {
				var oModel = new JSONModel();
				data.quantity--;
				oModel.setData(data);
				this.getView().setModel(oModel);
			}
		},

		submit : function() {
			var data = this.getView().getModel().getData();
			var partNumber = data.supplierPartId;
			var quantity = data.quantity;
			
			if(quantity <= 0) {
				MessageBox.error("Quantity must be a positive number.")
			} else {
				$.ajax({
					type : "GET",
					url : "/qrcode-app/api/v1/requisition/submit?partNumber="
							+ partNumber + "&quantity=" + quantity,
					beforeSend : BusyIndicator.show()
				}).done(function(data) {
					MessageBox.success("Requisition [ "
						+ data.requisitionRequisitionIdExportItem.item[0].uniqueName.value
						+ " ] successfully submited.");
				}).fail(function(error) {
					MessageBox.error("Submitting requisition failed.")
				}).complete(function() {
					BusyIndicator.hide();
				});
			}
		},
		
		searchItem: function (oEvent) {
			
			var partNumber = oEvent.getParameter("arguments").partNumber;
			
			var oModel = new JSONModel();
			var submitButton = this.getView().byId("submitButton");

			$.ajax({
				type : "GET",
				url : "/qrcode-app/api/v1/catalog/item?partNumber=" + partNumber,
				beforeSend : BusyIndicator.show()
			}).done(function(data) {
				if (data === undefined || data === "") {
					MessageBox.warning("Catalog item [" + partNumber
						+ "] not found.");
					submitButton.setEnabled(false);
				} else {
					data.quantity = 1;
					oModel.setData(data);
					if (data.contractPrice.amount === null) {
						MessageBox.warning("Catalog Item [ " + data.shortName
							+ "] is OUT OF STOCK.");
						submitButton.setEnabled(false);
					}
				}	
			}).fail(function(oError) {
				
				MessageBox.warning("Catalog item [" + partNumber
					+ "] not found.");	
			}).complete(function() {
				BusyIndicator.hide();
			});

			this.getView().setModel(oModel);
		}	
	});
});
