var viewResponseData;

var viewDetailsForm = "viewDetailsForm";
var viewReportTable = "viewReportTable"
var viewReportDiv = "viewReportDiv"
var applicableQtagList;
function getAllViewDetails(status) {
	if (status)
		doApiAction(3000, null);
}


function getAllQtagDetails(status) {
	if (status)
		doApiAction(2001, null);
}

function setViewDetails() {
	var formElement = document.forms.namedItem(viewDetailsForm);
	var oData = new FormData(formElement);
	doApiAction(3050, oData);
}

function fillViewDetails(response) {
	try {
		if (response && response.responceList) {
			formResetById(viewDetailsForm);
			progressBar(true);
			viewResponseData = response.responceList;
			var columns = [];
			columns.push("Actions");
			columns.push("LastChange");
			/*		columns.push("Create_By");*/
			columns.push("Status");
			columns.push("Name");
			columns.push("description");

			$("#" + viewReportDiv).empty();
			$("#" + viewReportDiv).append(tableCreator(viewReportTable));
			$("#" + viewReportDiv + " thead").append(tableHeaderMaker(columns));
			$.each(viewResponseData, function(index, row) {
				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.id + "'  onClick='loadViewEditModal(this.id)' class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' rowName='" + row.name + "'    id='" + row.pageCode + "' onConform='viewDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
				actions = actions + " </div>";
				fields.push(actions);
				fields.push(sqlTDateToDateDDMMYY(row.updateDate));
				fields.push(row.status);;
				fields.push(row.name);
				fields.push("<p  style='max-width: 150px;'>" + row.description + "</p>");
				$("#applicableQtagList").bootstrapDualListbox('refresh');

				$("#" + viewReportTable + " tbody").append(tableRowMaker(fields));
			});
			tableRendaring(viewReportTable);
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
function fillQtagDetails(response) {
	try {
		if (response && response.responceObject) {
			formResetById(viewDetailsForm);
			progressBar(true);
			viewResponseData = response.responceObject;
			$.each(viewResponseData, function(key, val) {
				$("#applicableQtagList").append("<option class='option' value='" + key + "'>" + (key + "-" + val) + "</option>");
			});
			$("#applicableQtagList").bootstrapDualListbox('refresh');
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
function viewDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		doApiAction(3051, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}

function loadViewEditModal(id) {
	try {
		progressBar(true);
		$("[name=" + viewDetailsForm + "]").trigger("reset");
		preFill();
		$("#applicableQtagList  option").each(function() {
			this.selected = false;
		});
		$("input[name=id]").val("");
		if (id) {
			var singleObject = fillDetailsByName(viewResponseData, 'id', id, viewDetailsForm);
			console.log(JSON.stringify(singleObject))
			if (singleObject) {
				$.each(singleObject.applicableQtagMap, function(key, val) {
					console.log("::" + key + "::" + val)
					$("#applicableQtagList").find("option[value=" + key + "]").prop("selected", "selected");
				});
			}

		}
		$("#applicableQtagList").bootstrapDualListbox('refresh');
		$('#viewDetailsModal').modal();
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
