var webPageResponseData;

var webPageDetailsForm = "webPageDetailsForm";
var webPageReportTable = "webPageReportTable"
var webPageReportDiv = "webPageReportDiv"

function getAllWebPageDetails(status) {
	if (status)
		doApiAction(1500, null);
}
function setWebPageDetails() {
	var formElement = document.forms.namedItem(webPageDetailsForm);
	var oData = new FormData(formElement);
	doApiAction(1550, oData);
}



function getAllViewDetails(status) {
	if (status)
		doApiAction(1501, null);
}




function fillWebPageDetails(response) {
	try {
		if (response && response.responceList) {
			formResetById(webPageDetailsForm);
			progressBar(true);
			webPageResponseData = response.responceList;
			var columns = [];
			columns.push("Actions");
			columns.push("LastChange");
			/*		columns.push("Create_By");*/
			columns.push("Status");
			columns.push("ApplicableFor");
			columns.push("MenuGroup");
			columns.push("TabOrder");
			columns.push("PageCode");
			columns.push("Name");
			columns.push("Title");
			columns.push("description");

			$("#" + webPageReportDiv).empty();
			$("#" + webPageReportDiv).append(tableCreator(webPageReportTable));
			$("#" + webPageReportDiv + " thead").append(tableHeaderMaker(columns));
			var webPageOptions;
			$.each(webPageResponseData, function(index, row) {


				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.pageCode + "'  onClick='loadWebPageEditModal(this.id)' class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' rowName='" + row.name + "'    id='" + row.pageCode + "' onConform='webPageDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
				actions = actions + " </div>";
				fields.push(actions);
				fields.push(sqlTDateToDateDDMMYY(row.updateDate));
				fields.push(row.status);
				fields.push(row.applicableFor);
				fields.push(row.menuGroup);
				fields.push(row.tabOrder);
				fields.push(row.pageCode);
				fields.push(row.title);
				fields.push(row.name);
				fields.push("<p  style='max-width: 150px;'>" + row.description + "</p>");
				/*fields.push(row.location);*/
				$("#" + webPageReportTable + " tbody").append(tableRowMaker(fields));

				webPageOptions = webPageOptions + "<option class='option' pageCode='" + row.pageCode + "' value='" + row.pageCode + "'>" + row.pageCode + "</option>";


			});
			tableRendaring(webPageReportTable);
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
function fillAllViewDetails(response) {
	try {
		if (response && response.responceObject) {
			progressBar(true);
			$.each(response.responceObject, function(key, val) {
				$("#applicableViewList").append("<option class='option' value=" + key + ">" + (key + "-" + val) + "</option>");
			});
			$("#applicableViewList").bootstrapDualListbox('refresh');
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
function webPageDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		doApiAction(1551, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}


function loadWebPageEditModal(pageCode) {
	console.log(pageCode)
	progressBar(true);
	try {
		$("[name=webPageDetailsForm]").trigger("reset");
		preFill();
		$("#applicableViewList  option").each(function() {
			this.selected = false;
		});
		$("input[name=id]").val("");
		if (pageCode) {

			var singleObject = fillDetailsByName(webPageResponseData, 'pageCode', pageCode, webPageDetailsForm);
			if (singleObject) {
				$.each(singleObject.applicableViews, function(key, val) {
					console.log("key val : " + key + ":::" + val)
					$("#applicableViewList").find("option[value='" + key + "']").prop("selected", "selected");
				});
			}
		}
		$("#applicableViewList").bootstrapDualListbox('refresh');
		$('#webPageDetailsModal').modal();
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
