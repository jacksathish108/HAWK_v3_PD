var adminDashBoardViewerResponseData;
var adminDashBoardViewerForm = "adminDashBoardViewerForm";
var adminDashBoardViewerTemplateModal = "adminDashBoardViewerTemplateModal";
var adminDashBoardViewerTable = "adminDashBoardViewerTable";
var adminDashBoardViewerDiv = "adminDashBoardViewerDiv";

var adminDashBoardViewerReportDiv = "adminDashBoardViewerReportDiv";
var adminDashBoardViewerReportTable = "adminDashBoardViewerReportTable";
var menuItemcontainer = "menuItemsDiv";

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
				menuItem = menuItem + "<li class='nav-item'><a id='" + row.pageCode + "' onclick='redirectTo(this)' class='nav-link'> <i class='far fa-circle nav-icon'></i>";
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