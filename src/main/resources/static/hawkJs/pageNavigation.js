var pages = {
	1: "dashbord()",
	2: "enquiries()",
	3: "newClient()",
	4: "payment()",
	5: "workoutChart()",
	6: "package()",
	7: "enquiryReport()",
	8: "clientReport()",
	9: "assessmentReport()",
	10: "renewalReport()",
	11: "chartUnassignedReport()",
	12: "pendingDueReport()",
	13: "feedbackReport()",
	14: "googleReviewReport()",
	15: "attendanceReport()",
	16: "enquiryReport",
	16: "webPageDetails()"
};

function rendar(id) {
	try {
		switch (id) {
			case 1:
				adminDashboard();
				break;
			case 2:
				enquiries();
				break;
			case 3:
				clientDetails();
				break;
			case 4:
				packageDetails();
				break;
			case 5:
				usersDetails();
				break;
			case 16:
				webPageDetails();
				break;
			case 17:
				pendingDueDetails();
				break;
			case 18:
				workoutChartDetails();
				break;
			case 19:
				paymentDetails();
				break;
			case 20:
				activityDetails();
				break;
			case 21:
				assessmentTemplateDetails();
				break;
			case 23:
				assessmentDetails();
				break;
			case 24:
				calendarEventDetsils();
				break;
			case 25:
				feedbackTemplateDetails();
				break;

			default:
				adminDashboard();
				break;
		}
	} catch (err) {
		errorTost(err);
	}

}