var adminDashBoardViewerResponseData;
var adminDashBoardViewerForm = "adminDashBoardViewerForm";
var adminDashBoardViewerTemplateModal = "adminDashBoardViewerTemplateModal";
var adminDashBoardViewerTable = "adminDashBoardViewerTable";
var adminDashBoardViewerDiv = "adminDashBoardViewerDiv";

var adminDashBoardViewerReportDiv = "adminDashBoardViewerReportDiv";
var adminDashBoardViewerReportTable = "adminDashBoardViewerReportTable";


function getAdminDashBoardViewerDetails()
{
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
function adminDashBoardViewerDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		doApiAction(1251, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}

function adminDashBoardViewerTemplate(id) {
	progressBar(true);
	try {
		formResetById(adminDashBoardViewerForm);
		$("input[name=id]").val("");
		if (id) {
			$.each(getObjects(adminDashBoardViewerResponseData, 'id', id), function(key, value) {
				if (key.includes("Date")) {
					$("#" + key).val(sqlTDateToDateYYMMDD(value));
				}
				else {
					setValueById(key, value);
				}

			});
		}
		$('#' + adminDashBoardViewerTemplateModal).modal();
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
	convertTableToArrayObject()
}

function convertTableToArrayObject(tableId) {
	var aadminDashBoardViewerTestArray = [];
	if (tableId) {
		var rows = $('#' + tableId).DataTable().rows().data();
		//var adminDashBoardViewerTestDetails = {};
		$.each(rows, function(index, data) {
			var adminDashBoardViewerTest = {};
			adminDashBoardViewerTest["workGroup"] = data[0];
			adminDashBoardViewerTest["test"] = data[1];
			adminDashBoardViewerTest["type"] = data[2];
			adminDashBoardViewerTest["measurement"] = $(rows.cell(index, 3).node()).find('input').val();
			adminDashBoardViewerTest["remarks"] = $(rows.cell(index, 4).node()).find('textarea').val();
			aadminDashBoardViewerTestArray.push(adminDashBoardViewerTest);
			//JSON.stringify
		});
		//adminDashBoardViewerTestDetails["adminDashBoardViewerDate"] = $('#adminDashBoardViewerDate').val();
		//adminDashBoardViewerTestDetails["adminDashBoardViewerTest"] = aadminDashBoardViewerTestArray;
		//console.log(JSON.stringify(adminDashBoardViewerTestDetails))
	}
	return aadminDashBoardViewerTestArray;
}

