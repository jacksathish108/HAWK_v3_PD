var dataLinkResponseData;
var webPageResponseData;
var dataLinkDetailsForm = "dataLinkDetailsForm";
var dataLinkReportTable = "dataLinkReportTable"
var dataLinkReportDiv = "dataLinkReportDiv"
var applicableQtagList;
function getAllDataLinkDetails(status) {
	if (status)
		doApiAction(4000, null);
}


function getAllWebPageCodeDetails(status) {
	if (status)
		doApiAction(4001, null);
}

function getAllViewIdDetails(status) {
	if (status)
		doApiAction(4002, null);
}

function setDataLinkDetails() {
	var formElement = document.forms.namedItem(dataLinkDetailsForm);
	var oData = new FormData(formElement);
	doApiAction(4050, oData);
}

function fillDataLinkDetails(response) {
	try {
		if (response && response.responceList) {
			formResetById(dataLinkDetailsForm);
			progressBar(true);
			dataLinkResponseData = response.responceList;
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


			$("#" + dataLinkReportDiv).empty();
			$("#" + dataLinkReportDiv).append(tableCreator(dataLinkReportTable));
			$("#" + dataLinkReportDiv + " thead").append(tableHeaderMaker(columns));
			$.each(dataLinkResponseData, function(index, row) {
				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.id + "'  onClick='loadDataLinkEditModal(this.id)' class='btn btn-info'><i class='fas fa-edit'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' rowName='" + row.name + "'    id='" + row.pageCode + "' onConform='dataLinkDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
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

				$("#" + dataLinkReportTable + " tbody").append(tableRowMaker(fields));
			});
			tableRendaring(dataLinkReportTable);
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

			// Clear existing options
			$("#sourceWebPageCode").empty();
			$("#targetWebPageCode").empty();
			$("#sourceViewId").empty();
			$("#targetViewId").empty();

			// Add default "Select one" option
			$("#sourceWebPageCode").append("<option value='' selected disabled>Select one</option>");
			$("#targetWebPageCode").append("<option value='' selected disabled>Select one</option>");
			$("#sourceViewId").append("<option value='' selected disabled>Select one</option>");
			$("#targetViewId").append("<option value='' selected disabled>Select one</option>");

			// Loop through each item in responseObject
			$.each(webPageResponseData, function(index, item) {
				if (item.pageCode && item.pid !== undefined) {
					let option = "<option class='option' value='" + item.pid + "'>" + item.pageCode + "</option>";
					$("#sourceWebPageCode").append(option);
					$("#targetWebPageCode").append(option);
				}
			});
		}
	} catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	} finally {
		progressBar(false);
	}
}

function sourceWebpagechange() {
	try {
		progressBar(true);
		$("#sourceViewId").empty();
		$("#sourceViewId").append("<option value='' selected disabled>Select one</option>");

		const selectedVal = parseInt($("#sourceWebPageCode").val());

		// Find the object by pid
		const selectedItem = webPageResponseData.find(item => item.pid === selectedVal);

		if (selectedItem && selectedItem.vid_list) {
			$("#sourceViewId").append(
				"<option class='option' value='" + selectedItem.vid_list + "'>" + (selectedItem.view_names || selectedItem.vid_list) + "</option>"
			);
		}
	} catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	} finally {
		progressBar(false);
	}
}

function targetWebpagechange() {
	try {
		progressBar(true);
		$("#targetViewId").empty();
		$("#targetViewId").append("<option value='' selected disabled>Select one</option>");

		const selectedVal = parseInt($("#targetWebPageCode").val());

		// Find the object by pid
		const selectedItem = webPageResponseData.find(item => item.pid === selectedVal);

		if (selectedItem && selectedItem.vid_list) {
			$("#targetViewId").append(
				"<option class='option' value='" + selectedItem.vid_list + "'>" + (selectedItem.view_names || selectedItem.vid_list) + "</option>"
			);
		}
	} catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	} finally {
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
function dataLinkDelete(btnDelete) {
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

function loadDataLinkEditModal(id) {
	try {
		var singleObject ;
		progressBar(true);
		$("[name=" + dataLinkDetailsForm + "]").trigger("reset");
		preFill();
		$("#applicableQtagList  option").each(function() {
			this.selected = false;
		});
		$("input[name=id]").val("");
		if (id) {
			 singleObject = fillDetailsByName(dataLinkResponseData, 'id', id, dataLinkDetailsForm);
			sourceWebpagechange();
			targetWebpagechange();

		}
		$('#dataLinkDetailsModal').modal();	
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
