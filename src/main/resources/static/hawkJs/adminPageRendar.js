function enquiries() {
	$("#title").text("Enquiries");
	$("#pageName").text("Enquiries");
	$("#activePage").text("Enquiries");

	var result;
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#enquiryDetails").html());
	$("#enquiryDetails").empty();
	function getEnquiries() {
		progressBar(true);
		try {
			var request = new XMLHttpRequest();
			request.open("GET", "admin/getDashbord");
			request.send();
			request.onload = function() {
				if (this.response)
					try {
						result = JSON.parse(this.response);
						console.log(JSON.stringify(result));

						$("#userName").text(result.user_details.name);
						if (result.user_details.profilepicurl)
							$("#userImage").attr("src", result.user_details.profilepicurl);
						enquiriesDesign();
						progressBar(false);
					} catch (err) {
						errorTost("enquiries .....:  " + err);
						progressBar(false);
					}
			}
		} catch (err) {
			errorTost("enquiries " + err);
			progressBar(false);
		}
	}
}


function clientDetails() {
	$("#title").text("Client Details");
	$("#pageName").text("Client Details");
	$("#activePage").text("Client Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#clientDetails").html());
	$("#clientDetails").empty();
}
function packageDetails() {
	$("#title").text("Package Details");
	$("#pageName").text("Package Details");
	$("#activePage").text("Package Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#packageDetails").html());
	$("#packageDetails").empty();
}

function webPageDetails() {
	$("#title").text("WebPage Details");
	$("#pageName").text("WebPage Details");
	$("#activePage").text("WebPage Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#webPageDetails").html());
	$("#webPageDetails").empty();
}

function usersDetails() {
	$("#title").text("Users Details");
	$("#pageName").text("Users Details");
	$("#activePage").text("Users Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#usersDetails").html());
	$("#usersDetails").empty();
}
function pendingDueDetails() {
	$("#title").text("PendingDue Details");
	$("#pageName").text("PendingDue Details");
	$("#activePage").text("PendingDue Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#pendingDueDetails").html());
	$("#pendingDueDetails").empty();
}

function workoutChartDetails() {
	$("#title").text("Workout Chart Details");
	$("#pageName").text("Workout Chart Details");
	$("#activePage").text("Workout Chart Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#workoutChartDetails").html());
	$("#workoutChartDetails").empty();
}
function paymentDetails() {
	$("#title").text("Payment Details");
	$("#pageName").text("Payment Details");
	$("#activePage").text("Payment Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#paymentDetails").html());
	$("#paymentDetails").empty();
}
function activityDetails() {
	$("#title").text("activitys");
	$("#pageName").text("activitys");
	$("#activePage").text("activitys");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#activityDetails").html());
	$("#activityDetails").empty();
}
function assessmentTemplateDetails() {
	$("#title").text("Assessment Template");
	$("#pageName").text("Assessment Template");
	$("#activePage").text("Assessment Template");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#assessmentTemplateDetails").html());
	$("#assessmentTemplateDetails").empty();
}



function assessmentDetails() {
	$("#title").text("Assessment Details");
	$("#pageName").text("Assessment Details");
	$("#activePage").text("Assessment Details");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#assessmentDetails").html());
	$("#assessmentDetails").empty();
}

function adminDashboard() {
	$("#title").text("Dashboard");
	$("#pageName").text("Dashboard");
	$("#activePage").text("Dashboard");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#dashboardDetailsConatainer").html());
	$("#dashboardDetailsConatainer").empty();
}
function calendarEventDetsils() {
	$("#title").text("Calendar Event");
	$("#pageName").text("Calendar Event");
	$("#activePage").text("Calendar Event");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#calendarEventDetails").html());
	$("#calendarEventDetails").empty();
}
function feedbackTemplateDetails() {
	$("#title").text("Feedback Template");
	$("#pageName").text("Feedback Template");
	$("#activePage").text("Feedback Template");
	$("#dashBoardContainer").empty();
	$('#dashBoardContainer').append($("#feedbackTemplateDetails").html());
	$("#feedbackTemplateDetails").empty();
}



