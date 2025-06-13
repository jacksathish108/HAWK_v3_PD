var tableMappingResponseData;
var webPageResponseData;
var tableMappingDetailsForm = "tableMappingDetailsForm";
var tableMappingReportTable = "tableMappingReportTable"
var tableMappingReportDiv = "tableMappingReportDiv"
var applicableQtagList;
function getAllTableMappingDetails(status) {
	if (status)
		doApiAction(7000, null);
}


function getAllWebPageCodeDetails(status) {
	if (status)
		doApiAction(4001, null);
}

function getAllViewIdDetails(status) {
	if (status)
		doApiAction(4002, null);
}

function setTableMappingDetails() {
	var formElement = document.forms.namedItem(tableMappingDetailsForm);
	var oData = new FormData(formElement);
	doApiAction(7050, oData);
}

function fillTableMappingDetails(response) {
	try {
		if (response && response.responceList) {
			formResetById(tableMappingDetailsForm);
			progressBar(true);
			tableMappingResponseData = response.responceList;
			var columns = [];
			columns.push("Actions");
			columns.push("LastChange");
			/*		columns.push("Create_By");*/
			columns.push("Status");
			columns.push("Name");
			columns.push("description");
			columns.push("TableMapCode");
			columns.push("SourceWebPageId");
			columns.push("SourceViewId");
			columns.push("TargetTable");
			columns.push("SqlQuery");
			columns.push("columnMap");


			$("#" + tableMappingReportDiv).empty();
			$("#" + tableMappingReportDiv).append(tableCreator(tableMappingReportTable));
			$("#" + tableMappingReportDiv + " thead").append(tableHeaderMaker(columns));
			$.each(tableMappingResponseData, function(index, row) {
				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.id + "'  onClick='loadTableMappingEditModal(this.id)' class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' rowName='" + row.name + "'    id='" + row.pageCode + "' onConform='tableMappingDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
				actions = actions + " </div>";
				fields.push(actions);
				fields.push(sqlTDateToDateDDMMYY(row.updateDate));
				fields.push(row.status);;
				fields.push(row.name);
				fields.push("<p  style='max-width: 150px;'>" + row.description + "</p>")
				fields.push(row.dataMapCode);
				fields.push(row.sourceWebPageId);
				fields.push(row.sourceViewId);
				fields.push(row.targetTable);
				fields.push(row.sqlQuery);
				fields.push(row.columnMap);

				$("#" + tableMappingReportTable + " tbody").append(tableRowMaker(fields));
			});
			tableRendaring(tableMappingReportTable);
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

			$("#sourceWebPageId").empty();
			$("#targetWebPageId").empty();
			$("#sourceViewId").empty();
			$("#targetViewId").empty();

			$("#sourceWebPageId").append("<option value='' selected disabled>Select one</option>");
			$("#targetWebPageId").append("<option value='' selected disabled>Select one</option>");

			$("#sourceViewId").append("<option value='' selected disabled>Select one</option>");
			$("#targetViewId").append("<option value='' selected disabled>Select one</option>");

			$.each(webPageResponseData, function(key, val) {
				$("#sourceWebPageId").append("<option class='option' value='" + val.pid + "'>" + (val.pid+"_"+val.pageCode) + "</option>");
				$("#targetWebPageId").append("<option class='option' value='" + val.pid + "'>" + (val.pid+"_"+val.pageCode) + "</option>");
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
		var selectedVal = $("#sourceWebPageId").find(":selected").val();
var selectedData = findObjectBykey(webPageResponseData,'pid',selectedVal);
if (selectedData && selectedData.vid_list) {
    $.each(selectedData.vid_list.split(","), function(index, val) {
        var option = new Option(val, val);
        $("#sourceViewId")[0].options.add(option);
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

function targetWebpagechange() {
	try {
		progressBar(true);
		$("#targetViewId").empty();
		$("#targetViewId").append("<option value='' selected disabled>Select one</option>");
		
		
		var selectedVal = $("#targetWebPageId").find(":selected").val();
		var selectedData = findObjectBykey(webPageResponseData,'pid',selectedVal);
if (selectedData && selectedData.vid_list) {
    $.each(selectedData.vid_list.split(","), function(index, val) {
		        var option = new Option(val, val);
        $("#targetViewId")[0].options.add(option);
        //$("#targetViewId").append("<option class='option' value='" + val + "'>" + (val) + "</option>");
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


/*
$('#targetWebPageId').on('change', 'select', function() {
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
function tableMappingDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		doApiAction(7051, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}

function loadTableMappingEditModal(id) {
	try {
		var singleObject ;
		progressBar(true);
		$("[name=" + tableMappingDetailsForm + "]").trigger("reset");
		preFill();
		$("#applicableQtagList  option").each(function() {
			this.selected = false;
		});
		$("input[name=id]").val("");
		if (id) {
			 singleObject = fillDetailsByName(tableMappingResponseData, 'id', id, tableMappingDetailsForm);
			sourceWebpagechange();
			targetWebpagechange();

		}
		$('#tableMappingDetailsModal').modal();	
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
