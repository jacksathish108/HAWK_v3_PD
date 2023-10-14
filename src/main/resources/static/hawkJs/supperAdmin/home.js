var adminDashBoardViewerResponseData;
var adminDashBoardViewerForm = "adminDashBoardViewerForm";
var adminDashBoardViewerTemplateModal = "adminDashBoardViewerTemplateModal";
var adminDashBoardViewerTable = "adminDashBoardViewerTable";
var adminDashBoardViewerDiv = "adminDashBoardViewerDiv";

var adminDashBoardViewerReportDiv = "adminDashBoardViewerReportDiv";
var adminDashBoardViewerReportTable = "adminDashBoardViewerReportTable";
var clientActivity={};
var enquiryActivity={};
var accountActivity={};
var clientActivityLegend=[];
var enquiryActivityLegend=[];
var accountActivityLegend=[];
function getAdminDashBoardViewerDetails()
{
	doApiAction(2000);
}
function fillSupperAdminDashBoardViewerDetails(response) {
	try {
		if (response) {
			progressBar(true);
			$.each((response.responseMap), function(key, value) {
				console.log(key+" :: "+value)
				$("#" + key).text(value);
		//$("#dashBoardContainerDiv").append(value);
		});
		if(response.responseMap&&response.responseMap['clientActivity'])
		{
			//ClientActivity
			clientActivityxAxis=[];
			var clientSeriesList=[];
			$.each((['TOTAL','ACTIVE','INACTIVE']), function(index,val) {
				clientActivityLegend.push(val);
				clientSeriesList.push({
				  name: val,
                        type: "line",
                        smooth: !0,
                        itemStyle: {
                            normal: {
                                areaStyle: {
                                    type: "default"
                                }
                            }
                        },
                        'data': []
                        });
				
                    });
                    /////Enquiry Activity
                    enquiryActivityxAxis=[];
			var enquirySeriesList=[];
			$.each((['TOTAL','CONVERSION','NOT_INTERESTED']), function(index,val) {
				enquiryActivityLegend.push(val);
				enquirySeriesList.push({
				  name: val,
                        type: "line",
                        smooth: !0,
                        itemStyle: {
                            normal: {
                                areaStyle: {
                                    type: "default"
                                }
                            }
                        },
                        'data': []
                        });
				
                    }); 
           /////Account Activity
                    accountActivityxAxis=[];
			var accountSeriesList=[];
			$.each((['CREDIT','DEBIT','NEW-REGISTRATION','PENDINGDUE','RENEWAL']), function(index,val) {
				accountActivityLegend.push(val);
				accountSeriesList.push({
				  name: val,
                        type: "line",
                        smooth: !0,
                        itemStyle: {
                            normal: {
                                areaStyle: {
                                    type: "default"
                                }
                            }
                        },
                        'data': []
                        });
				
                    }); 
				
			$.each((response.responseMap['clientActivity']), function(index,obj) {
					clientActivityxAxis.push((obj.YEAR)+"-"+months[(obj.MONTH)-1]);
					clientSeriesList[0]['data'].push(obj.TOTAL);
					clientSeriesList[1]['data'].push(obj.ACTIVE);
					clientSeriesList[2]['data'].push(obj.INACTIVE);
		});
		$.each((response.responseMap['enquiryActivity']), function(index,obj) {
					enquiryActivityxAxis.push((obj.YEAR)+"-"+months[(obj.MONTH)-1]);
					enquirySeriesList[0]['data'].push(obj.TOTAL);
					enquirySeriesList[1]['data'].push(obj.CONVERSION);
					enquirySeriesList[2]['data'].push(obj.NOT_INTERESTED);
		});
		$.each((response.responseMap['accountActivity']), function(index,obj) {
					accountActivityxAxis.push((obj.YEAR)+"-"+months[(obj.MONTH)-1]);
					accountSeriesList[0]['data'].push(obj.CREDIT);
					accountSeriesList[1]['data'].push(obj.DEBIT);
					accountSeriesList[2]['data'].push(obj.NEWREGISTRATION);
					accountSeriesList[3]['data'].push(obj.PENDINGDUE);
					accountSeriesList[4]['data'].push(obj.RENEWAL);
		});		
		clientActivity["xAxis"]=clientActivityxAxis;
		clientActivity["series"]=clientSeriesList;
		enquiryActivity["xAxis"]=enquiryActivityxAxis;
		enquiryActivity["series"]=enquirySeriesList;
		accountActivity["xAxis"]=accountActivityxAxis;
		accountActivity["series"]=accountSeriesList;
		
		console.log("clientActivityxAxis  ::"+JSON.stringify(clientActivityxAxis))
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

