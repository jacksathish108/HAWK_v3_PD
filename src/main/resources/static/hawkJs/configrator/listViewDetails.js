var listViewResponseData;
var webPageResponseData;
var listViewDetailsForm = "listViewDetailsForm";
var listViewReportTable = "listViewReportTable"
var listViewReportDiv = "listViewReportDiv"
var qtagsResponseData;
function getAllListViewDetails(status) {
	if (status)
		doApiAction(5000, null);
}


function getAllSelectQtags(status) {
	if (status)
		doApiAction(5001, null);
}

function setListViewDetails() {
	var formElement = document.forms.namedItem(listViewDetailsForm);
	var oData = new FormData(formElement);
	doApiAction(5050, oData);
}

function fillQtags(response) {
	try {
		if (response && response.responceObject) {
			formResetById(listViewDetailsForm);
			$("#sourceQtag").empty();
			$("#targetQtag").empty();
			progressBar(true);
			qtagsResponseData = response.responceObject;
			$.each(qtagsResponseData, function(key, val) {
				$("#sourceQtag").append("<option class='option' value='" + key + "'>" + (key + "-" + val) + "</option>");
				$("#targetQtag").append("<option class='option' value='" + key + "'>" + (key + "-" + val) + "</option>");
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






function fillListViewDetails(response) {
	try {
		if (response && response.responceList) {
			formResetById(listViewDetailsForm);
			progressBar(true);
			listViewResponseData = response.responceList;
			var columns = [];
			columns.push("Actions");
			columns.push("LastChange");
			/*		columns.push("Create_By");*/
			columns.push("ListViewCode");
			columns.push("sourceQtag");
			columns.push("targetQtag");
			columns.push("DependencyCondition");
			columns.push("Status");
			columns.push("Description");



			$("#" + listViewReportDiv).empty();
			$("#" + listViewReportDiv).append(tableCreator(listViewReportTable));
			$("#" + listViewReportDiv + " thead").append(tableHeaderMaker(columns));
			$.each(listViewResponseData, function(index, row) {
				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.id + "'  onClick='loadListViewEditModal(this.id)' class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' rowName='" + row.name + "'    id='" + row.pageCode + "' onConform='listViewDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
				actions = actions + " </div>";
				fields.push(actions);
				fields.push(sqlTDateToDateDDMMYY(row.updateDate));
				fields.push(row.listViewCode);
				fields.push(row.sourceQtag);
				fields.push(row.targetQtag);
				fields.push(row.dependencyCondition);
				fields.push(row.status);;
				fields.push("<p  style='max-width: 150px;'>" + row.description + "</p>")

				$("#" + listViewReportTable + " tbody").append(tableRowMaker(fields));
			});
			tableRendaring(listViewReportTable);
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


function setFocus(id) {
	$("#" + id).focus();
}
function listViewDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		doApiAction(5051, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}

function loadListViewEditModal(id) {
	try {
		progressBar(true);
		$("[name=" + listViewDetailsForm + "]").trigger("reset");
		preFill();
		$("input[name=id]").val("");
		if (id) {
			singleObject = fillDetailsByName(listViewResponseData, 'id', id, listViewDetailsForm);
		}
		$('#listViewDetailsModal').modal();
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
