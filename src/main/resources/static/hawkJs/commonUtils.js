var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
var changeHistoryReportDiv_0 = "changeHistoryReportDiv";
var changeHistoryReportTable_0 = "changeHistoryReportTable";
var calendarEventDatesList = [];
/**
 * 
 */
var base_path = "";
var apiPrifix = "/Hawk_api_01";
var ViewVersion = "/pages/v1";
var video_link_prefix = 'https://www.youtube.com/embed/';



function GetFormattedDate(date) {
	if (date) {
		try {
			const d_date = new Date(date);
			const year = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d_date)
			const month = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(d_date)
			const day = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d_date)

			return (year + "-" + month + "-" + day);
		}
		catch (err) {
			console.log(date + " :: ERROR :  " + err);
			return date;
		}
	}
}


function GetFormattedDateDDMMYY(date) {
	if (date) {
		const d_date = date;
		const year = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d_date)
		const month = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(d_date)
		const day = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d_date)
		return (day + "/" + month + "/" + year);
	}
}


function GetFormattedDate_DDMMYY_to_YYMMDD(date) {
	if (date) {
		var parts = date.split('/');
		var str_date = parts[2] + "-" + (parts[1]) + "-" + parts[0];
		console.log("str_date   " + str_date)
		return new Date(str_date);
	}
}

function isObject(val) {
	if (val === null) { return false; }
	return ((typeof val === 'function') || (typeof val === 'object'));
}
function checkTextField(field) {
	return (field.value === "") ? false : true;
}


function progressBar(status) {
	var loaders = document.getElementById("loader-1");
	if (status) {
		loaders.style.display = "inherit";
	}
	else {
		loaders.style.display = "none";
	}

}
function redirectTo(pageName) {
	progressBar(true);
	console.log(pageName)
	window.location.href = ViewVersion + pageName;
	progressBar(false);
}
function oauthLogin() {
	progressBar(true);
	location.href = location.origin + "/oauth2/authorization/google";
}
/*function logout()
{
	  var request = new XMLHttpRequest();
		request.open("POST", "/logout");
	request.responseType = 'json';
	request.send();
	 request.onload = function() {
		 window.location.href = "/login";
	  }
	}*/

function formValidation(formName) {
	var validationStatus = true;
	var errorMsg;
	try {
		if (formName) {
			var formElement = document.forms.namedItem(formName);

			if (formElement) {
				$.each(formElement, function(i, field) {
					if (field.required) {
						if (field.value == "") {
							validationStatus = false;
							errorMsg = (field.name + " must be filled out");
							$(field).addClass('is-invalid');
							return false;
						}
						else {
							$(field).removeClass('is-invalid');
						}
					}

				});
			}
			else {
				validationStatus = false;
				errorMsg = "Invalid Form Name"
			}
		}
		else {
			validationStatus = false;
			errorMsg = "Invalid Form Name"
		}
	}
	catch (err) {
		validationStatus = false;
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}


	if (!validationStatus)
		errorTost(errorMsg);

	return validationStatus;

}
function tableRendaring(dataTable) {
	clsName = dataTable + 'cls';
	$('#' + dataTable + ' thead tr')
		.clone(true)
		.addClass(clsName)
		.appendTo('#' + dataTable + ' thead');
	var table = $('#' + dataTable).DataTable({
		"buttons": ["csv", "excel", "pdf", "print", "colvis"],
		orderCellsTop: true,
		fixedHeader: true,
		paging: true,
		"autoWidth": false,

		initComplete: function() {
			var api = this.api();

			// For each column
			api
				.columns()
				.eq(0)
				.each(function(colIdx) {
					// Set the header cell to contain the input element
					var cell = $('.' + clsName + ' th').eq(
						$(api.column(colIdx).header()).index()
					);
					var title = $(cell).text();
					if (!title.includes('Actions') && false) {
						$(cell).html('<input class="form-control" type="text" placeholder="' + title + '" />');

						// On every keypress in this input
						$(
							'input',
							$('.' + clsName + ' th').eq($(api.column(colIdx).header()).index())
						)
							.off('keyup change')
							.on('keyup change', function(e) {
								e.stopPropagation();

								// Get the search value
								$(this).attr('title', $(this).val());
								var regexr = '({search})'; //$(this).parents('th').find('select').val();

								var cursorPosition = this.selectionStart;
								// Search the column for that value
								api
									.column(colIdx)
									.search(
										this.value != ''
											? regexr.replace('{search}', '(((' + this.value + ')))')
											: '',
										this.value != '',
										this.value == ''
									)
									.draw();

								$(this)
									.focus()[0]
									.setSelectionRange(cursorPosition, cursorPosition);
							});
					}
					else {
						$(cell).html('');
					}
				});
		},
	}).buttons().container().appendTo('#' + dataTable + '_wrapper .col-md-6:eq(0)');

}
function tableRendaringWithOutFillter(dataTable) {

	var table = $('#' + dataTable).DataTable();

}
function tableRowMaker(jsonData) {
	var row = "<tr>"
	$.each(jsonData, function(index, field) {
		row = row + "<td>" + field + "</td>";
	});
	row = row + "</tr>";
	return row;
}

function tableRowMakerWithStyle(jsonData) {
	var row = "<tr>"
	$.each(jsonData, function(index, data) {
		row = row + "<td ";

		if (data.rowspan)
			row = row + "rowspan='" + data.rowspan + "'";
		if (data.align)
			row = row + "align='" + data.align + "'";
		if (data.style)
			row = row + "style='" + data.style + "'";
		if (data.class)
			row = row + "class='" + data.class + "'";
		if (data.data_hide)
			row = row + "data-hide='" + data.data_hide + "'";



		if (data.value)
			row = row + ">" + data.value + "</td>";

		else
			row = row + ">&nbsp;</td>";


	});
	row = row + "</tr>";
	return row;
}
function tableHeaderMaker(jsonData) {
	var row = "<tr>"
	$.each(jsonData, function(index, field) {
		row = row + "<th>" + field + "</th>";
	});
	row = row + "</tr>";
	return row;
}
function sqlTDateToDateYYMMDD(sqldate) {
	var date = new Date(sqldate);
	return new Date(date.getTime() - (date.getTimezoneOffset() * 60000))
		.toISOString()
		.split("T")[0];
}
function sqlTDateToDateDDMMYY(sqldate) {
	var today = new Date(sqldate);
	var formattedtoday = today.getDate() + '-' + (today.getMonth() + 1) + '-' + today.getFullYear();
	return (formattedtoday);
}



function sqlTDateToDateDDMMYYHHMMSS(sqldate) {
	var today = new Date(sqldate);
	
	
	 let hours = today.getHours();
    const minutes = String(today.getMinutes()).padStart(2, '0');
    const seconds = String(today.getSeconds()).padStart(2, '0');
    const meridiem = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12 || 12;
    hours = String(hours).padStart(2, '0');
	
	
	var formattedtoday = today.getFullYear()+'-'+(today.getMonth() + 1) + '-' + today.getDate() + ' ' + (hours) + ':' + (minutes) + ':' + (seconds)+" "+meridiem ;
	
	
	
	return (formattedtoday);
}

function sqlTDateToDateMMDDYY(sqldate) {
	var today = new Date(sqldate);
	var formattedtoday = (today.getMonth() + 1) + '-' + today.getDate() + '-' + today.getFullYear();
	return (formattedtoday);
}


function tableCreator(tableId) {
	return "<table id='" + tableId + "'  class='table table-striped table-bordered table-hover'><thead></thead><tbody></tbody></table>";
}

function getObjects(obj, key, val) {
	var newObj = false;
	$.each(obj, function() {
		var testObject = this;
		$.each(testObject, function(k, v) {
			//alert(k);
			if (val == v && k == key) {
				newObj = testObject;
			}
		});
	});

	return newObj;
}
function toString(JsonData) {
	var stringVal = JSON.stringify(JsonData);
	console.log("Result :" + stringVal);
	return stringVal;
}
function setFocus(id) {
	$("#" + id).focus();
}

function setValueByName(formName, fieldName, value) {
 try {
    var $form = $('form[name="' + formName + '"]');
    var $field = $form.find('[name="' + fieldName + '"]');

    if (!$field.length) return;

    var type = $field.attr('type');
    var tag = $field.prop('tagName').toLowerCase();

    if (tag === 'select') {
      $field.val(value).trigger('change');
    } else if (type === 'checkbox') {
      $field.prop('checked', !!value).trigger('change');
    } else if (type === 'radio') {
      $form.find('input[name="' + fieldName + '"][value="' + value + '"]').prop('checked', true).trigger('change');
    } else {
      $field.val(value).trigger('input');
    }
  } catch (err) {
    console.log(err);
  }
}




function setValueById(key, val) {
	//$("#" + key).val(val);
	obj = document.getElementById(key);
	if (obj && val)
		obj.value = val;
}
function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if ((charCode > 31 && charCode < 48) || charCode > 57) {
		return false;
	}
	return true;
}
function capitalizeFirstLetter(string) {
	return string.charAt(0).toUpperCase() + string.slice(1);
}
function formResetById(formName) {
	$('#' + formName).trigger("reset");
}


function apiRequest(requestType, url, oData, onSuccessMethod) {
	var response;
	var request = new XMLHttpRequest();
	request.responseType = 'json';
	request.open(requestType, apiPrifix + url);
	request.send(oData);
	request.onload = function() {
		try {
			progressBar(true);

			response = this.response;
			if (response) {
				if (response.statusCode == 200) {
					if (oData)
						successTost(response.message);
					$("#userName").text(response.userName);
					if (response.userDetails.profilePicUrl)
						$("#userImage").attr("src", response.userDetails.profilePicUrl);
				}
				else {
					errorTost(response.message);
				}
			}
		} catch (err) {
			errorTost(err);
			console.log("ERROR :  " + err);
		}
		finally {
			progressBar(false);
			eval(onSuccessMethod);
		}
	}
}

function columnMapping(columnHeaders, row, fields) {
	$.each((JSON.parse(columnHeaders)), function(colIndex, colName) {
		if (typeof row[colName] != 'undefined') {
			if (colName.includes("Date")) {
				fields.push(sqlTDateToDateYYMMDD(row[colName]));
			}
			else {
				fields.push(row[colName]);
			}
		}
		else {
			fields.push("-");
		}
	});
	return fields;

}
function fillDetailsById(responseData, key, value) {
	if (key) {
		$.each(getObjects(responseData, key, value), function(key, value) {
			if (key.includes("Date")) {
				$("#" + key).val(sqlTDateToDateYYMMDD(value));
			}
			else {
				setValueById(key, value);
			}
		});
		setFocus('name');
	}
}
function fillDetailsByName(responseData, key, value, formName) {
	if (key) {
		var Object = getObjects(responseData, key, value);
		$.each(getObjects(responseData, key, value), function(key, val) {
			if (key.includes("Date")/*||key.includes("date")*/) {
				//console.log(key + ":::" + val)
				setValueByName(formName, key, sqlTDateToDateYYMMDD(val));
			}
			else {
				setValueByName(formName, key, val);
			}
		});
		setFocus('name');
		return Object;
	}
}
function callChangeHistoryReport() {

	$('#changeHistoryReportModal').modal();
}
function getChangeReport(history) {
	progressBar(true);
	try {
		var oData = new FormData();
		oData.append("id", history.getAttribute("rowId"))
		oData.append("module", history.getAttribute("moduleName"));
		doApiAction(1000, oData);
	}
	catch (err) {
		errorTost(err);
		console.log("ERROR :  " + err);
	}
	finally {
		progressBar(false);
	}
}
function fillReport(response) {

	var columns = [];
	columns.push('Create_Date');
	columns.push('Create_By');
	columns.push('Module_Name');
	columns.push('Modified_Values');
	$("#" + changeHistoryReportDiv_0).empty();
	$("#" + changeHistoryReportDiv_0).append(tableCreator(changeHistoryReportTable_0));
	$("#" + changeHistoryReportDiv_0 + " thead").append(tableHeaderMaker(columns));

	$.each(response.responceList, function(index, values) {
		var fields = [];
		fields.push(sqlTDateToDateYYMMDD(values.createDate));
		fields.push(values.createBy);
		fields.push(values.moduleName);
		fields.push(values.modifiedValues);
		$("#" + changeHistoryReportDiv_0 + " tbody").append(tableRowMaker(fields));
	});
	tableRendaring(changeHistoryReportTable_0);
	callChangeHistoryReport();

}

function locationReplace(target) {
	window.location.replace(target);
}
/////only for test
function preFill() {
	var number = 1 + Math.floor(Math.random() * 6);
	const inputElements = document.getElementsByTagName('input');
	for (let i = 0; i < inputElements.length; i++) {
		inputElements[i].value = (inputElements[i].name) + number;
	} const textareaElements = document.getElementsByTagName('textarea');
	for (let i = 0; i < textareaElements.length; i++) {
		textareaElements[i].value = (textareaElements[i].name) + number;
	}
}