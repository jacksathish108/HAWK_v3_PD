var targetDiv="dashBoardContainer";
function DashboardRendar(title,sourceDiv) {
	$("#title").text(title);
	$("#pageName").text(title);
	$("#activePage").text(title);
	$("#"+targetDiv).empty();
	$('#'+targetDiv).append($("#"+sourceDiv).html());
	$("#"+sourceDiv).empty();
}

function UserDashboardRendar(title,sourceDiv) {
	$("#title").text(title);
	$("#pageName").text(title);
	$("#activePage").text(title);
	$("#"+targetDiv).empty();
	//$('#'+targetDiv).append($("#"+sourceDiv).html());
	$('#'+targetDiv).append(sourceDiv);
//	$("#"+sourceDiv).empty();
}
