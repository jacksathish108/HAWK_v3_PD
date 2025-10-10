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
				menuItem = menuItem + "<li class='nav-item'><a id='" + row.id + "' onclick='loadWebPage(this.id)' class='nav-link'> <i class='far fa-circle nav-icon'></i>";
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
    if (!(response && response.responceObject)) return;

    progressBar(true);
    let singleObject = response.responceObject;
    webPageResponseData = singleObject;

    try {
        $("input[name=id]").val("");
        let getDatasByIds = [{}];
        let pageViewDiv = "";
        reportTableHeaders = {};

        $.each(singleObject.applicableViews, function (_, view) {
            const viewId = view.id;
            pageViewDiv += createViewCard(viewId);
            const formId = `${viewId}DetailsForm`;
            const modalId = `${viewId}DetailsModal`;
            const columsHeaderList = {};

            // Sort applicable questions
            const sortedQtags = Object.entries(view.applicableQtagMap)
                .sort(([, a], [, b]) => a.index - b.index);

            let formFieldsHTML = "";
            sortedQtags.forEach(([qtag, qConfig]) => {
                    formFieldsHTML += createFormField(qConfig);
                    columsHeaderList[qtag] = qConfig;

            });

            // Store headers for future use
            reportTableHeaders[viewId] = columsHeaderList;

            const modalHTML = createModal(formId, modalId, singleObject.id, viewId, formFieldsHTML);
            pageViewDiv += modalHTML;

            getDatasByIds.push({ pageId: singleObject.id, viewid: viewId });
        });

        // Fetch data for views
        getDatasByIds.forEach(({ pageId, viewid }) => {
            window.pageId = pageId;
            window.viewId = viewid;
            getAnswersByViewId();
        });

        UserDashboardRendar(singleObject.pageCode, pageViewDiv);
        setFocus('name');
    } catch (err) {
        errorTost(err);
        console.error("ERROR:", err);
    } finally {
        progressBar(false);
    }
}
function createViewCard(viewId) {
    return `
    <div class='col-md-12' id='viewDetails_${viewId}'>
        <div class='card-body'>
            <div class='card card-secondary'>
                <div class='card-header'>
                    <h3 class='card-title'>Report</h3>
                    <div class='card-tools'>
                        <button class='btn btn-success' id='loadModal_${viewId}' onclick="calendarRendaring();loadViewEditModal('${viewId}DetailsModal','${viewId}DetailsForm')">New</button>
                        <button type='button' class='btn btn-tool' data-card-widget='collapse' title='Collapse'>
                            <i class='fas fa-minus'></i>
                        </button>
                    </div>
                </div>
                <div class='card-body table-responsive' id='${viewId}ReportDiv'></div>
            </div>
        </div>
    </div>`;
}

function createModal(formId, modalId, pageId, viewId, formFieldsHTML) {
    return `
    <div class='modal fade' id='${modalId}' tabindex='-1' role='dialog' aria-labelledby='exampleModalLabel' aria-hidden='true'>
        <div class='modal-dialog' role='document'>
            <div class='modal-content'>
                <div class='modal-header'>
                    <div class='row'>
                        <h5 class='modal-title' id='exampleModalLabel'>General Information</h5>
                    </div>
                    <button type='button' class='close' data-dismiss='modal' aria-label='Close'>
                        <span aria-hidden='true'>&times;</span>
                    </button>
                </div>
                <div class='modal-body overflow-auto'>
                    <div class='box clearfix'>
                        <form name='${formId}' id='${formId}'>
                            <input id='id' name='id' type='hidden'>
                            <div class='row'>
                                <div class='col-md-12'>
                                    <div class='card card-primary'>
                                        <div class='card-body'>
                                            <div class='row'>
                                                ${formFieldsHTML}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class='modal-footer'>
                    <button type='button' class='btn btn-secondary btn-success' pageId='${pageId}' viewId='${viewId}' onclick="if(formValidation('${formId}')) { setAnswersDetails(this); }">Submit</button>
                </div>
            </div>
        </div>
    </div>`;
}

function createFormField(field) {
    const {
        qtag, name, dataType = "text", style = "", attributes = "", required,
        readOnly, elementType, defaultValue = "", options = "", onClick = "",cssClass = "", onChange = "", jscript = ""
    } = field;

    const readonlyAttr = readOnly == 1 ? "readonly='readonly'" : "";
    const requiredAttr = required == 1 ? "required='required'" : "";
    const changeHandler = onChange ? `onchange='${onChange}(this)'` : "";
    const fullClass = cssClass ? `class='${cssClass}'` : "";
    const label = `<label for='${name}'>${name}${required == 1 ? "<label>*</label>" : ""}</label>`;
	
	const onclickHandler = onClick ? `onclick="${onClick}(this)"` : "";

    let inputHTML = "";

    // BUTTON SUPPORT
	console.log("button field::", elementType);
    if (elementType === "button") {
		console.log("button field::", field);
        inputHTML = `
        <div class='col-md-6'>
            <div class='form-group'>
                <button 
                    type="button" 
                    name='${qtag}' 
                    id='${qtag}' 
                    ${fullClass} 
                    ${readonlyAttr} 
                    ${attributes}
                    style='${style}'
                     ${onclickHandler}
                >
                    ${name}
                </button>
                <script>${jscript}</script>
            </div>
        </div>`;
        return inputHTML; // Done, don't wrap again
    }

    // START INPUT FIELD WRAPPER
    let fieldWrapper = `
    <div class='col-md-6'>
        <div class='form-group'>
            ${label}`;

    // SELECT
    if (elementType === "select") {
        let optionHTML = "<option disabled selected value> -- select an option -- </option>";
        if (options.includes("<option")) {
            optionHTML += options;
        } else {
            options.split(",").forEach(optionVal => {
                optionHTML += `<option value='${optionVal.trim()}'>${optionVal.trim()}</option>`;
            });
        }

        inputHTML = `<select 
            name='${qtag}' 
            id='${qtag}' 
            ${fullClass} 
            ${readonlyAttr} 
            ${requiredAttr} 
            ${changeHandler} 
            style='${style}' 
            ${attributes}
        >
            ${optionHTML}
        </select>`;
    }

    // CALENDAR
    else if (elementType === "calendar") {
        inputHTML = `
        <div class='input-group date' id='c_${qtag}' data-target-input='nearest'>
            <input 
                type='${dataType}' 
                name='${qtag}' 
                id='${qtag}' 
                ${fullClass} 
                ${readonlyAttr} 
                ${requiredAttr} 
                ${changeHandler} 
                value='${defaultValue}' 
                style='${style}' 
                ${attributes} 
                data-target='#c_${qtag}'
            />
            <div class='input-group-append' data-target='#c_${qtag}' data-toggle='datetimepicker'>
                <div class='input-group-text'><i class='fa fa-calendar'></i></div>
            </div>
        </div>
        <script>$(document).ready(function() {
            calendarRendaring(); 
            $('#${qtag}').datetimepicker({ format: 'YYYY-MM-DD' }); 
        });</script>`;
    }

    // IMAGE
    else if (elementType === "image") {
        inputHTML = `
        <input 
            type='file' 
            accept='image/*' 
            name='${qtag}' 
            id='${qtag}' 
            ${fullClass} 
            ${readonlyAttr} 
            ${requiredAttr} 
            ${changeHandler} 
            style='${style}' 
            ${attributes}
        />
        <img 
            id='preview_${qtag}' 
            style='max-width: 150px; margin-top: 10px; display: none; border: 1px solid #ccc; padding: 5px; border-radius: 5px;'
        />
        <script>
            $(document).ready(function() {
                $('#${qtag}').on('change', function() {
                    const file = this.files[0];
                    if (!file) return;
                    if (!file.type.startsWith('image/')) {
                        alert('Please select a valid image file.');
                        return;
                    }
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        $('#preview_${qtag}').attr('src', e.target.result).show();
                    };
                    reader.readAsDataURL(file);
                });
            });
        </script>`;
    }

    // DEFAULT INPUT
    else {
        inputHTML = `<${elementType} 
            type='${dataType}' 
            name='${qtag}' 
            id='${qtag}' 
            ${fullClass} 
            ${readonlyAttr} 
            ${requiredAttr} 
            ${changeHandler} 
            value='${defaultValue}' 
            style='${style}' 
            ${attributes}
        ></${elementType}>`;
    }

    // FINISH FIELD WRAPPER
    fieldWrapper += `
        ${inputHTML}
        <script>${jscript}</script>
        </div>
    </div>`;

    return fieldWrapper;
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
		if (!(response && response.responceList && response.responceList[0])) return;

		progressBar(true);
		answerResponseData = response.responceList;
		const viewId = answerResponseData[0].viewId;
		const $reportDiv = $("#" + viewId + "ReportDiv").empty();

		const columns = ["Actions", "Last Change", "ChangeBy", "Status"];
		const viewQtagList = [];
		const buttonQtagList = [];

		// Separate button fields and normal fields
		$.each(reportTableHeaders[viewId], function(qtag, val) {
			if (val.elementType === 'button') {
				buttonQtagList.push(val);
			} else {
				viewQtagList.push(qtag);
				columns.push(val.name);
			}
		});

		// Create and append table
		const tableId = viewId + "ReportTable";
		$reportDiv.append(tableCreator(tableId));
		$("#" + tableId + " thead").append(tableHeaderMaker(columns));

		// Populate rows
		answerResponseData.forEach(row => {
			const fields = [];

			// Build action buttons
			let actions = `<div class='btn-group btn-group-sm'>`;

			// View button
			actions += `
				<a id='${row.id}' 
				   onClick="loadViewEditModal('${row.viewId}DetailsModal','${row.viewId}DetailsForm', this.id)" 
				   class='btn btn-info'>
					<i class='fas fa-edit'></i>
				</a>`;

			// Delete button
			actions += `
				<a rowId='${row.id}' 
				   pageId='${row.pageId}' 
				   viewId='${row.viewId}' 
				   rowName='${row.viewId}' 
				   onConform='answerDelete' 
				   onClick='deleteConformation(this)' 
				   class='btn btn-danger'>
					<i class='fas fa-trash'></i>
				</a>`;

			// Custom button fields
			buttonQtagList.forEach(btn => {
				actions += `
				<a rowId='${row.id}' 
				   pageId='${row.pageId}' 
				   viewId='${row.viewId}' 
				   rowName='${row.viewId}' 
				   id='${row.id}' 
				   ${btn.attributes || ''} 
				   onClick='${btn.onClick}(this)' 
				   class='${btn.cssClass || ''}'>
					<i class='fas fa-grin-alt'></i>
				</a>`;
			});

			actions += `</div>`;

			// If status == 3, show only description, else action buttons
			fields.push(row.status == 3 ? row.discription : actions);
			fields.push(sqlTDateToDateDDMMYYHHMMSS(row.updateDate));
			fields.push(row.updateBy);
			fields.push(row.status);

			// Add answers
			viewQtagList.forEach(qtag => {
				const obj = getObjects(row.answers, "qtag", qtag);
				fields.push(obj?.ansValue || "-");
			});

			// Append row
			$("#" + tableId + " tbody").append(tableRowMaker(fields));
		});

		// Render table
		tableRendaring(tableId);
	} catch (err) {
		errorTost(err);
		console.error("ERROR:", err);
	} finally {
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





 
