var dataMapingResponseData;
var webPageResponseData;
var dataMapingDetailsForm = "dataMapingDetailsForm";
var dataMapingReportTable = "dataMapingReportTable"
var dataMapingReportDiv = "dataMapingReportDiv"
var applicableQtagList;
function getAllDataMapingDetails(status) {
	if (status)
		doApiAction(6000, null);
}


function getAllWebPageCodeDetails(status) {
	if (status)
		doApiAction(4001, null);
}

function getAllViewIdDetails(status) {
	if (status)
		doApiAction(4002, null);
}

function setDataMapingDetails() {
	var formElement = document.forms.namedItem(dataMapingDetailsForm);
	var oData = new FormData(formElement);
	doApiAction(4050, oData);
}

function fillDataMapingDetails(response) {
	try {
		if (response && response.responceList) {
			formResetById(dataMapingDetailsForm);
			progressBar(true);
			dataMapingResponseData = response.responceList;
			var columns = [];
			columns.push("Actions");
			columns.push("LastChange");
			/*		columns.push("Create_By");*/
			columns.push("Status");
			columns.push("Name");
			columns.push("description");

			columns.push("LinkCode");
			columns.push("SourceWebPageCode");
			columns.push("TargetWebPageCode");
			columns.push("SourceViewId");
			columns.push("TargetViewId");
			columns.push("QtagMaping");


			$("#" + dataMapingReportDiv).empty();
			$("#" + dataMapingReportDiv).append(tableCreator(dataMapingReportTable));
			$("#" + dataMapingReportDiv + " thead").append(tableHeaderMaker(columns));
			$.each(dataMapingResponseData, function(index, row) {
				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.id + "'  onClick='loadDataMapingEditModal(this.id)' class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' rowName='" + row.name + "'    id='" + row.pageCode + "' onConform='dataMapingDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
				actions = actions + " </div>";
				fields.push(actions);
				fields.push(sqlTDateToDateDDMMYY(row.updateDate));
				fields.push(row.status);;
				fields.push(row.name);
				fields.push("<p  style='max-width: 150px;'>" + row.description + "</p>")
				fields.push(row.linkCode);
				fields.push(row.sourceWebPageCode);
				fields.push(row.targetWebPageCode);
				fields.push(row.sourceViewId);
				fields.push(row.targetViewId);
				fields.push(row.qtagMap);

				$("#" + dataMapingReportTable + " tbody").append(tableRowMaker(fields));
			});
			tableRendaring(dataMapingReportTable);
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
function fillWebPageCode(response) {
	try {
		if (response && response.responceObject) {
			progressBar(true);
			webPageResponseData = response.responceObject;

			$("#sourceWebPageCode").empty();
			$("#targetWebPageCode").empty();
			$("#sourceViewId").empty();
			$("#targetViewId").empty();

			$("#sourceWebPageCode").append("<option value='' selected disabled>Select one</option>");
			$("#targetWebPageCode").append("<option value='' selected disabled>Select one</option>");

			$("#sourceViewId").append("<option value='' selected disabled>Select one</option>");
			$("#targetViewId").append("<option value='' selected disabled>Select one</option>");

			$.each(webPageResponseData, function(key, val) {
				$("#sourceWebPageCode").append("<option class='option' value='" + key + "'>" + (key) + "</option>");
				$("#targetWebPageCode").append("<option class='option' value='" + key + "'>" + (key) + "</option>");
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
function sourceWebpagechange() {
	try {
		progressBar(true);
		$("#sourceViewId").empty();
		$("#sourceViewId").append("<option value='' selected disabled>Select one</option>");
		var selectltedVal = $("#sourceWebPageCode").find(":selected").val();
		$.each(webPageResponseData[selectltedVal], function(index, val) {
			$("#sourceViewId").append("<option class='option' value='" + val + "'>" + (val) + "</option>");
		});
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}

function targetWebpagechange() {
	try {
		progressBar(true);
		$("#targetViewId").empty();
		$("#targetViewId").append("<option value='' selected disabled>Select one</option>");
		var selectltedVal = $("#targetWebPageCode").find(":selected").val();
		$.each(webPageResponseData[selectltedVal], function(index, val) {
			$("#targetViewId").append("<option class='option' value='" + val + "'>" + (val) + "</option>");
		});
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}


/*
$('#targetWebPageCode').on('change', 'select', function() {
	$("#targetViewId").empty();
	$("#targetViewId").append("<option value='' selected disabled>Select one</option>");
	var selectltedVal = $(this).find(":selected").val();
	$.each(webPageResponseData[selectltedVal], function(index, val) {
		$("#targetViewId").append("<option class='option' value='" + val + "'>" + (val) + "</option>");
	});
});*/

function setFocus(id) {
	$("#" + id).focus();
}
function dataMapingDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		doApiAction(4051, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}

function loadDataMapingEditModal(id) {
	try {
		var singleObject ;
		progressBar(true);
		$("[name=" + dataMapingDetailsForm + "]").trigger("reset");
		preFill();
		$("#applicableQtagList  option").each(function() {
			this.selected = false;
		});
		$("input[name=id]").val("");
		if (id) {
			 singleObject = fillDetailsByName(dataMapingResponseData, 'id', id, dataMapingDetailsForm);
			sourceWebpagechange();
			targetWebpagechange();

		}
		$('#dataMapingDetailsModal').modal();	
					if (singleObject) {
						
						$("#sourceViewId").val(singleObject.sourceViewId).change();
						$("#targetViewId").val(singleObject.targetViewId).change();

			}
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
