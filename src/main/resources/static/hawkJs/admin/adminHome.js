var adminDashBoardViewerResponseData;
var adminDashBoardViewerForm = "adminDashBoardViewerForm";
var adminDashBoardViewerTemplateModal = "adminDashBoardViewerTemplateModal";
var adminDashBoardViewerTable = "adminDashBoardViewerTable";
var adminDashBoardViewerDiv = "adminDashBoardViewerDiv";
var adminDashBoardViewerReportDiv = "adminDashBoardViewerReportDiv";
var adminDashBoardViewerReportTable = "adminDashBoardViewerReportTable";
var menuItemcontainer = "menuItemsDiv";
var reportTableHeaders = {};
var pageid, viewid;
var answerResponseData;
function getMenuItems() {
	doApiAction(10000);
}
function getAdminDashBoardViewerDetails() {

	doApiAction(1300);
}


function fillAdminDashBoardViewerDetails(response) {
	try {
		if (response) {
			progressBar(true);
			$.each((response.responseMap), function(key, value) {
				$("#dashBoardContainerDiv").append(value);
			});
		}
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}
function fillmenuItems(response) {
	try {
		if (response && response.responceList) {
			progressBar(true);
			webPageResponseData = response.responceList;
			$("#" + menuItemcontainer).empty();
			$.each(webPageResponseData, function(index, row) {
				var menuItem = "<div class='sidebar'>";
				menuItem = menuItem + "<nav class='mt-2'>";
				menuItem = menuItem + "<ul class='nav nav-pills nav-sidebar flex-column' data-widget='treeview' role='menu' data-accordion='false'>";
				menuItem = menuItem + "<li class='nav-item'><a id='" + row.pageCode + "' onclick='loadWebPage(this.id)' class='nav-link'> <i class='far fa-circle nav-icon'></i>";
				menuItem = menuItem + "<p>" + row.name + "</p>";
				menuItem = menuItem + "</a></li></ul></nav></div>";
				$("#" + menuItemcontainer).append(menuItem);
			});
		}
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}

function loadWebPage(pageCode) {

	doApiAction(10001, null, '/' + pageCode);
}

function fillWebPage(response) {
	if (response && response.responceList) {
		progressBar(true);
		webPageResponseData = response.responceObject;
		try {

			$("input[name=id]").val("");
			var getDatasByIds = [{}];
			reportTableHeaders = {};
			var pageViewDiv = ""
			var singleObject = webPageResponseData;
			if (singleObject) {
				$.each(singleObject.applicableViews, function(key, val) {
					pageViewDiv = "<div class='col-md-12' id=viewDetails_" + val.id + ">";
					pageViewDiv = pageViewDiv + "<div class='card-body'>";
					pageViewDiv = pageViewDiv + "<div class='card card-secondary'>";
					pageViewDiv = pageViewDiv + "<div class='card-header'>";
					pageViewDiv = pageViewDiv + "<h3 class='card-title'>Report</h3>";
					pageViewDiv = pageViewDiv + "<div class='card-tools'>";
					pageViewDiv = pageViewDiv + "<button class='btn btn-success' onclick=loadViewEditModal('" + val.id + "DetailsModal','" + val.id + "DetailsForm')>New</button>";
					pageViewDiv = pageViewDiv + "<button type='button' class='btn btn-tool'";
					pageViewDiv = pageViewDiv + "data-card-widget='collapse' title='Collapse'>";
					pageViewDiv = pageViewDiv + "<i class='fas fa-minus'></i>";
					pageViewDiv = pageViewDiv + "</button>";
					pageViewDiv = pageViewDiv + "</div>";
					pageViewDiv = pageViewDiv + "</div>";
					pageViewDiv = pageViewDiv + "<div class='card-body table-responsive' id='" + val.id + "ReportDiv'>";
					pageViewDiv = pageViewDiv + "</div>";
					pageViewDiv = pageViewDiv + "</div>";
					pageViewDiv = pageViewDiv + "</div>";
					pageViewDiv = pageViewDiv + "</div>";
					var modalDiv = "<div class='modal fade' id='" + val.id + "DetailsModal' tabindex='-1'";
					modalDiv = modalDiv + "role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>";
					modalDiv = modalDiv + "<div class='modal-dialog' role='document'>";
					modalDiv = modalDiv + "<div class='modal-content'>";
					modalDiv = modalDiv + "<div class='modal-header'>";
					modalDiv = modalDiv + "<div class='row'>";
					modalDiv = modalDiv + "<h5 class='modal-title' id='exampleModalLabel'>General Information</h5>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "<button type='button' class='close' data-dismiss='modal'";
					modalDiv = modalDiv + "aria-label='Close'>";
					modalDiv = modalDiv + "<span aria-hidden='true'>&times;</span>";
					modalDiv = modalDiv + "</button>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "<div class='modal-body overflow-auto'>";
					modalDiv = modalDiv + "<div class='box clearfix'>";
					modalDiv = modalDiv + "<form name='" + val.id + "DetailsForm' id='" + val.id + "DetailsForm'>";
					modalDiv = modalDiv + "<input id='id' name='id' type='hidden'>";
					modalDiv = modalDiv + "<div class='row'>";
					modalDiv = modalDiv + "<div class='col-md-12'>";
					modalDiv = modalDiv + "<div class='card card-primary'>";
					modalDiv = modalDiv + "<div class='card-body'>";
					modalDiv = modalDiv + "<div class='row'>";

					var columsHeaderList = {};
					$.each(val.applicableQtagMap, function(key, val) {
						console.log("index " + val.qtag + "::" + val.index)
						if (val.elementType != "button") {
							modalDiv = modalDiv + "<div class='col-md-6'>";
							modalDiv = modalDiv + "<div class='form-group'>";
							modalDiv = modalDiv + "<label for='" + val.name + "'>" + val.name + "</label> ";
							if (val.required == 1)
								modalDiv = modalDiv + "<label>*</label>";
							modalDiv = modalDiv + "<" + val.elementType + " type='" + val.dataType + "' name='" + val.qtag + "' id='" + val.qtag + "' style='" + val.style + "' class='" + val.cssClass + "'";
							if (val.required == 1)
								modalDiv = modalDiv + "' required='required' value='" + val.defaultValue + "'>";
							else
								modalDiv = modalDiv + "value='" + val.defaultValue + "'>";

							if (val.elementType == "select") {
								modalDiv = modalDiv + val.options;
							}
							modalDiv = modalDiv + "</" + val.elementType + ">";


							modalDiv = modalDiv + "</div>";
							modalDiv = modalDiv + "</div>";
						}
						if (val.elementType != "button") {
							columsHeaderList[val.qtag] = (val.name);
						}

					});
					reportTableHeaders[val.id] = columsHeaderList;
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</form>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "<div class='modal-footer'>";
					modalDiv = modalDiv + "<button type='button' class='btn btn-secondary btn-success' pageId=" + singleObject.id + " viewId=" + val.id;
					modalDiv = modalDiv + " onclick=\" if(formValidation(\'" + val.id + "DetailsForm\')){setAnswersDetails(this);}\">Submit</button>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					modalDiv = modalDiv + "</div>";
					pageViewDiv = pageViewDiv + modalDiv;
					getDatasByIds.push({ "pageId": singleObject.id, "viewid": val.id });
				});
				$.each(getDatasByIds, function(index, val) {
					pageId = val.pageId;
					viewId = val.viewid;
					getAnswersByViewId();
				});
			}
			UserDashboardRendar(singleObject.pageCode, pageViewDiv)

			setFocus('name');
		}
		catch (err) {
			errorTost(err);
			console.log("ERROR :  " + err);
		}
		finally {
			progressBar(false);

		}
		progressBar(false);
	}
}
function setAnswersDetails(submitBtn) {
	var formElement = document.forms.namedItem($(submitBtn).attr("viewid") + "DetailsForm");
	var oData = new FormData(formElement);
	let formDataObject = Object.fromEntries(oData.entries());
	oData = new FormData();
	oData.append("pageId", $(submitBtn).attr("pageid"));
	oData.append("viewId", $(submitBtn).attr("viewid"));
	oData.append("answers", JSON.stringify(formDataObject));
	formDataObject = Object.fromEntries(oData.entries());
	//let formDataJsonString = JSON.stringify(formDataObject);
	pageId = submitBtn.getAttribute("pageId");
	viewId = submitBtn.getAttribute("viewId");
	doApiAction(11050, oData);

}


function getAnswersByViewId() {
	if (pageId && viewId) {
		var oData = new FormData();
		oData.append("pageId", pageId);
		oData.append("viewId", viewId);
		doApiAction(11051, oData);
	}

}
function fillAnswerDetails(response) {
	try {
		if (response && response.responceList && response.responceList[0]) {
			//	formResetById(viewDetailsForm);
			progressBar(true);
			answerResponseData = response.responceList;
			var columns = [];
			var viewQtagList = [];
			columns.push("Actions");
			$("#" + answerResponseData[0].viewId + "ReportDiv").empty();
			$("#" + answerResponseData[0].viewId + "ReportDiv").append(tableCreator(answerResponseData[0].viewId + "ReportTable"));
			$.each(reportTableHeaders[answerResponseData[0].viewId], function(qtag, name) {
				columns.push(name);
				viewQtagList.push(qtag);
			});
			$("#" + answerResponseData[0].viewId + "ReportDiv" + " thead").append(tableHeaderMaker(columns));

			$.each(answerResponseData, function(index, row) {
				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.id + "'  onClick=\"loadViewEditModal(\'" + row.viewId + "DetailsModal\',\'" + row.viewId + "DetailsForm\',this.id)\" class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' pageId=" + row.pageId + "  viewId=" + row.viewId + "  rowName='" + row.viewId + "'    id='" + row.id + "' onConform='answerDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
				actions = actions + " </div>";
				fields.push(actions);
				$.each(viewQtagList, function(index, val) {
					var Object = getObjects(row.answers, "qtag", val);
					if (Object && Object.ansValue) {
						fields.push(Object.ansValue);
					}
					else {
						fields.push("-");
					}
				});

				$("#" + answerResponseData[0].viewId + "ReportTable" + " tbody").append(tableRowMaker(fields));

			});
			tableRendaring(answerResponseData[0].viewId + "ReportTable");
		}
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}


function loadViewEditModal(modalId, formId, id) {
	try {
		progressBar(true);
		$("[name=" + formId + "]").trigger("reset");
		//preFill();
		$("input[name=id]").val("");
		if (id) {
			var rowObject = getObjects(answerResponseData, 'id', id);

			$("input[name=id]").val(rowObject.id);
			$.each(rowObject.answers, function(index, val) {

				//$.each(val, function(key, val) {
				console.log(":::" + val)
				//if (key.includes("Date")/*||key.includes("date")*/) {
				//console.log(key + ":::" + val)
				//	setValueByName(formId, val.qtag, sqlTDateToDateYYMMDD(val.ansValue));
				//}
				//else {
				setValueByName(formId, val.qtag, val.ansValue);
				//}
			});
			//});

		}
		$("#" + modalId).modal();
		setFocus('name');
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}
function answerDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		pageId = btnDelete.getAttribute("pageId");
		viewId = btnDelete.getAttribute("viewId");
		doApiAction(11052, oData);

	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}
function formValidation(formId) {
	console.log(formId)
	return true;
}