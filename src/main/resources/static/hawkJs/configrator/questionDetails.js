var questionResponseData;

var questionDetailsForm = "questionDetailsForm";
var questionReportTable = "questionReportTable"
var questionReportDiv = "questionReportDiv"

function getAllQuestionDetails(status) {
	if (status)
		doApiAction(2000, null);
}
function setQuestionDetails() {
	var formElement = document.forms.namedItem(questionDetailsForm);
	var oData = new FormData(formElement);
	doApiAction(2050, oData);
}

function fillQuestionDetails(response) {
	try {
		if (response && response.responceList) {
			formResetById(questionDetailsForm);
			progressBar(true);
			questionResponseData = response.responceList;
			var columns = [];
			columns.push("Actions");
			columns.push("Last Change");
			columns.push("Q_Tag");
			columns.push("Index");
			columns.push("ElementType");
			columns.push("Unique");
			columns.push("DataType");
			columns.push("DefaultValue");
			columns.push("Name");
			columns.push("Description");
			columns.push("Status");
			columns.push("Requied");
			columns.push("Style");
			columns.push("CssClass");
			columns.push("Options");
			columns.push("OnClick");
			columns.push("OnChange");
			columns.push("Attributes");
			columns.push("JScript");
			


			$("#" + questionReportDiv).empty();
			$("#" + questionReportDiv).append(tableCreator(questionReportTable));
			$("#" + questionReportDiv + " thead").append(tableHeaderMaker(columns));
			$.each(questionResponseData, function(index, row) {


				var fields = [];
				var actions = "<div class='btn-group btn-group-sm'>";
				actions = actions + "<a  id='" + row.qtag + "'  onClick='loadQuestionEditModal(this.id)' class='btn btn-info'><i class='fas fa-eye'></i></a>";
				actions = actions + "<a  rowId='" + row.id + "' rowName='" + row.name + "'    id='" + row.qtag + "' onConform='questionDelete'  onClick='deleteConformation(this)' class='btn btn-danger'><i class='fas fa-trash'></i></a>";
				actions = actions + " </div>";
				fields.push(actions);
				fields.push(sqlTDateToDateDDMMYY(row.updateDate));
				fields.push(row.qtag);
				fields.push(row.index);
				fields.push(row.elementType);
				fields.push(row.unique);
				fields.push(row.dataType);
				fields.push(row.defaultValue);
				fields.push(row.name);
				fields.push("<p  style='max-width: 150px;'>" + row.description + "</p>");
				fields.push(row.status);
				fields.push(row.required);
				fields.push("<p  style='max-width: 150px;'>" + row.style + "</p>");
				fields.push("<p  style='max-width: 150px;'>" + row.cssClass + "</p>");
				fields.push("<p  style='max-width: 150px;'>" + row.options + "</p>");
				fields.push(row.onClick);
				fields.push(row.onChange);
				fields.push(row.attributes);
				fields.push(row.jscript);
				$("#" + questionReportTable + " tbody").append(tableRowMaker(fields));

			});
			tableRendaring(questionReportTable);
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
function questionDelete(btnDelete) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", btnDelete.getAttribute("rowId"));
		doApiAction(2051, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}


function loadQuestionEditModal(qtag) {
	progressBar(true);
	try {
		$("[name=" + questionDetailsForm + "]").trigger("reset");
		//preFill();
		$("input[name=id]").val("");
		if (qtag) {

			fillDetailsByName(questionResponseData, 'qtag', qtag, questionDetailsForm);

		}
		$('#questionDetailsModal').modal();
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

$('#elementType').on('change', function() {
	if (this.value == "select") {
		$("#options").prop('disabled', false);
	} else {
		//$("#options").val("");
		$("#options").prop('disabled', true);
	}

});

