var adminDashBoardViewerResponseData;
var adminDashBoardViewerForm = "adminDashBoardViewerForm";
var adminDashBoardViewerTemplateModal = "adminDashBoardViewerTemplateModal";
var adminDashBoardViewerTable = "adminDashBoardViewerTable";
var adminDashBoardViewerDiv = "adminDashBoardViewerDiv";
var adminDashBoardViewerReportDiv = "adminDashBoardViewerReportDiv";
var adminDashBoardViewerReportTable = "adminDashBoardViewerReportTable";
var menuItemcontainer = "menuItemsDiv";
var reportTableHeaders = {};
var dataLinkResponseData;
var linkBtn;
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
	doApiAction(10002);
}

function fillListView(response) {
	if (response && response.responceObject) {
		progressBar(true);
		webPageResponseData = response.responceObject;
		try {



		}
		catch (err) {
			errorTost(err);
			console.log("ERROR :  " + err);
		}
		finally {
			progressBar(false);
		}
	}
}
function fillWebPage(response) {
	if (response && response.responceObject) {
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
					pageViewDiv = pageViewDiv + "<button class='btn btn-success' id='loadModal_" + val.id + "' onclick=calendarRendaring();loadViewEditModal('" + val.id + "DetailsModal','" + val.id + "DetailsForm')>New</button>";
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
					
					
const applicableQ=(val.applicableQtagMap)
let sortedArray = Object.entries(applicableQ).sort(([,a], [,b]) => a.index - b.index);
let sortedObject = {};
for (let [key, value] of sortedArray) {
  sortedObject[key] = value;
}
					$.each(sortedObject, function(key, val) {
						if (val.elementType != "button") {
							modalDiv = modalDiv + "<div class='col-md-6'>";
							modalDiv = modalDiv + "<div class='form-group'>";
							modalDiv = modalDiv + "<label for='" + val.name + "'>" + val.name + "</label> ";
							if (val.required == 1)
								modalDiv = modalDiv + "<label>*</label>";

							if (val.elementType == "calendar") {

								modalDiv = modalDiv + "<div class='input-group date ' id='c_" + val.qtag + "' data-target-input='nearest'>";
								modalDiv = modalDiv + "<input  type='" + val.dataType + "' " + val.attributes + "  name='" + val.qtag + "' id='" + val.qtag + "' style='" + val.style + "'";
								if( val.readOnly==1)
								{
									modalDiv = modalDiv +" readonly='readonly' ";
								}
								

							}
							else {
								modalDiv = modalDiv + "<" + val.elementType + " type='" + val.dataType + "' " + val.attributes + "  name='" + val.qtag + "' id='" + val.qtag + "' style='" + val.style + "'";
								if( val.readOnly==1)
								{
									modalDiv = modalDiv +" readonly='readonly'  ";
								}
							}



							if (val.elementType == "calendar") {

								modalDiv = modalDiv + " data-target='#c_" + val.qtag + "'";
							}





							if (val.onChange)
								modalDiv = modalDiv + " onchange='" + val.onChange + "(this)'"

							if (val.cssClass) {
								modalDiv = modalDiv + " class='" + val.cssClass
								if (val.cssClass.includes("disabled"))
									modalDiv = modalDiv + " disabled ";
							}

							if (val.required == 1)
								modalDiv = modalDiv + "' required='required' value='" + val.defaultValue + "'>";
							else
								modalDiv = modalDiv + "value='" + val.defaultValue + "'>";
							if( val.readOnly==1)
								{
									modalDiv = modalDiv +" readonly='readonly'  ";
								}
							if (val.elementType == "select") {
							modalDiv += "<option disabled selected value> -- select an option -- </option>";

		if(val.options.includes("<option"))
		{
        modalDiv += val.options;
		}
		else
		{
	$.each(val.options.split(","), function(index, optionValue) {
		console.log(index + " :: " + optionValue);

        modalDiv += "<option value='" + optionValue + "'>" + optionValue + "</option>";
  			  
			});
}


							}
							if (val.elementType == "calendar") {
								modalDiv = modalDiv + "</input>";


									modalDiv = modalDiv + "<script>$(document).ready(function() {calendarRendaring(); $('#"+ val.qtag+"').datetimepicker({format : 'YYYY-MM-DD'});});</script>";



							} else {
								modalDiv = modalDiv + "</" + val.elementType + ">";
							}


							if (val.elementType == "calendar") {

								modalDiv = modalDiv + "<div class='input-group-append' data-target='#c_" + val.qtag + "' data-toggle='datetimepicker'>";
								modalDiv = modalDiv + "<div class='input-group-text'>";
								if( val.readOnly==1)
								{
									modalDiv = modalDiv +" readonly='readonly'  ";
								}
								modalDiv = modalDiv + "<i class='fa fa-calendar' ></i>";
								modalDiv = modalDiv + "</div>";
								modalDiv = modalDiv + "</div>";
								modalDiv = modalDiv + "</div>";

								//	modalDiv = modalDiv + "<script>$('#' + val.qtag).datetimepicker({format: 'YYYY-MM-DD'});</script>";

							}

							modalDiv = modalDiv + "</div>";
							modalDiv = modalDiv + "</div>";
							modalDiv = modalDiv + "<script> " + val.jscript + " </script>";

						}
						//if (val.elementType != "button") {

						columsHeaderList[val.qtag] = (val);
						//}

					});
					//console.log(modalDiv);
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
			var buttonQtagList = [];
			columns.push("Actions");
			columns.push("Last Change");
			columns.push("ChangeBy");
			columns.push("Status");
			$("#" + answerResponseData[0].viewId + "ReportDiv").empty();
			$("#" + answerResponseData[0].viewId + "ReportDiv").append(tableCreator(answerResponseData[0].viewId + "ReportTable"));
			$.each(reportTableHeaders[answerResponseData[0].viewId], function(qtag, val) {

				if (val.elementType == 'button') {
					//	console.log("val ::" + JSON.stringify(val))
					buttonQtagList.push(val);
				}
				else {
					viewQtagList.push(qtag);
					columns.push(val.name);
				}

			});
			$("#" + answerResponseData[0].viewId + "ReportDiv" + " thead").append(tableHeaderMaker(columns));

			$.each(answerResponseData, function(index, row) {
				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.id + "'  onClick=\"loadViewEditModal(\'" + row.viewId + "DetailsModal\',\'" + row.viewId + "DetailsForm\',this.id)\" class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' pageId=" + row.pageId + "  viewId=" + row.viewId + "  rowName='" + row.viewId + "'    id='" + row.id + "' onConform='answerDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";


				$.each(buttonQtagList, function(index, val) {
					if (val) {
						actions = actions + "<a  rowId='" + row.id + "'  pageId=" + row.pageId + " pageId=" + row.pageId + "  " + val.attributes + "   viewId=" + row.viewId + "  rowName='" + row.viewId + "'    id='" + row.id + "'  onClick=" + val.onClick + "(this); class='" + val.cssClass + "'><i class='fas fa-grin-alt'></i></a>";
					}
				});
				actions = actions + " </div>";

if(row.status==3)
{
fields.push(row.discription);
}
else
{
fields.push(actions);
}
				
				fields.push(sqlTDateToDateDDMMYYHHMMSS(row.updateDate));
				fields.push(row.updateBy);
				fields.push(row.status);
				
				
				
				

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
		console.log("callling load modalId::" + modalId + " :formId: " + formId + "::id:" + id)
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
					//setValueByName(formId, val.qtag, sqlTDateToDateYYMMDD(val.ansValue));
				//}
				//else {
				setValueByName(formId, val.qtag, val.ansValue);
				//}
			});
			//});

		}
		$("#" + modalId).modal();
		console.log(":modalId :" + $("#" + modalId))
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

function dataLink(btn) {
	try {
		progressBar(true);
		linkBtn = btn;
		doApiAction(11002, null, ("/" + btn.getAttribute("linkCode")));
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);

	}
	finally {
		progressBar(false);
	}
}
function fillDataLinkDetails(response) {
	try {
		if (response && response.responceObject) {
			//	formResetById(viewDetailsForm);
			progressBar(true);
			dataLinkResponseData = response.responceObject;
			var rowObject = getObjects(answerResponseData, 'id', linkBtn.getAttribute("rowId"));
			if (dataLinkResponseData) {
				loadWebPage(dataLinkResponseData.targetWebPageCode);

				$(document).ready(function() {
					setTimeout(function() {
						loadViewEditModal((dataLinkResponseData.targetViewId + 'DetailsModal'), (dataLinkResponseData.targetViewId + 'DetailsForm'));
						$.each(JSON.parse(dataLinkResponseData.qtagMap), function(target, source) {
							console.log(target + " ::" + source)
							$("#" + target).val(getObjects(rowObject.answers, 'qtag', source).ansValue).trigger('change');;
						});
					}, 500); // for 0.5 second delay 
				});
			}
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


function listViewUpdate(listViewselect) {
	try {
		progressBar(true);
		if (listViewselect) {
			var gs = $(listViewselect).find('option:selected').attr('listview');
			$.each(JSON.parse(gs), function(key, ansVal) {

				console.log(key + ":::" + ansVal)
				$("#" + key).val(ansVal);

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





 
