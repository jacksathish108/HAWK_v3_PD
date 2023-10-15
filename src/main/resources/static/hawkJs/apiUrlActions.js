var actionUrl={
1:{'requestUrl':'/getClientDetails','requestType':'GET','onSuccess':'getclientsEntrysFill(response)'},
2:{'requestUrl':'/getPendingDueDetails','requestType':'GET','onSuccess':'getclientsEntrysFillForPendingDue(response)'},
3:{'requestUrl':'/getPendingDueDetails','requestType':'GET','onSuccess':'getclientsEntrysFillForPendingDue(response)'},
4:{'requestUrl':'/getRenewalDetails','requestType':'GET','onSuccess':'getclientsEntrysFill(response)'},
5:{'requestUrl':'/getFeedBackDetails','requestType':'GET','onSuccess':'fillFeedbackDetailsForClient(response)'},
6:{'requestUrl':'/getAttandanceDetails','requestType':'GET','onSuccess':'getclientsEntrysFill(response)'},
50:{'requestUrl':'/setClientDetails','requestType':'POST','onSuccess':'loadClientsDetails(true,1)'},
51:{'requestUrl':'/deleteclients','requestType':'POST','onSuccess':'loadClientsDetails(true,1)'},
52:{'requestUrl':'/setPendingDue','requestType':'POST','onSuccess':'loadClientsDetails(true,2)'},
53:{'requestUrl':'/setFeedBackDetails','requestType':'POST','onSuccess':'fillFeedbackDetailsForClient(response)'},
54:{'requestUrl':'/asginWorkoutChart','requestType':'POST','onSuccess':'loadClientsDetails(true,1)'},
100:{'requestUrl':'/getEnquiry/0','requestType':'GET','onSuccess':'getEnquiryEntrysFill(response)'},
101:{'requestUrl':'/getEnquiryHistory','requestType':'GET','onSuccess':'getEnquiryHistoryFill(response)'},
150:{'requestUrl':'/setEnquiry','requestType':'POST','onSuccess':'getEnquiryEntrys(true,100)'},
151:{'requestUrl':'/deleteEnquiry','requestType':'POST','onSuccess':'getEnquiryEntrys(true,100)'},
200:{'requestUrl':'/getAllWebPage','requestType':'GET','onSuccess':'getWebPageEntrysFill(response)'},
300:{'requestUrl':'/getPaymentHistoryDetails','requestType':'GET','onSuccess':'fillPaymentHistoryDetails(response)'},
301:{'requestUrl':'/getPaymentHistoryDetails','requestType':'GET','onSuccess':'fillPaymentHistoryDetails(response)'},
350:{'requestUrl':'/setPaymentHistoryDetails','requestType':'POST','onSuccess':'doApiAction(300)'},

400:{'requestUrl':'/getPackages','requestType':'GET','onSuccess':'fillPackageDetailsForClientRegistration(response)'},
401:{'requestUrl':'/getPackages','requestType':'GET','onSuccess':'getPackageEntrysFill(response)'},
450:{'requestUrl':'/setPackageDetails','requestType':'POST','onSuccess':'getPackageDetails(true,400)'},
451:{'requestUrl':'/deletePackage','requestType':'POST','onSuccess':'getPackageDetails(true,400)'},
452:{'requestUrl':'/setClonePackageDetails','requestType':'POST','onSuccess':'getPackageDetails(true,400)'},

600:{'requestUrl':'/getWorkoutChartDetails','requestType':'GET','onSuccess':'fillWorkoutChartDetails(response)'},
601:{'requestUrl':'/getWorkoutChartDetails','requestType':'GET','onSuccess':'fillWorkoutChartDetailsforClientDetails(response)'},
602:{'requestUrl':'/getWorkoutChartDetailsByPlanner','requestType':'GET','onSuccess':'fillWorkoutChartByPlanner(response)'},


650:{'requestUrl':'/setWorkoutChartDetails','requestType':'POST','onSuccess':'getWorkoutChartDetails(true)'},
651:{'requestUrl':'/deleteWorkoutChartDetails','requestType':'POST','onSuccess':'getWorkoutChartDetails(true)'},


800:{'requestUrl':'/getActivitysDetails','requestType':'GET','onSuccess':'fillActivityDetails(response)'},
850:{'requestUrl':'/setActivityDetails','requestType':'POST','onSuccess':'getActivityDetails(true)'},


500:{'requestUrl':'/getAllUsers','requestType':'GET','onSuccess':'getUsersEntrysFill(response)'},
550:{'requestUrl':'/setUserDetails','requestType':'POST','onSuccess':'getUsersDetails(true,500)'},
551:{'requestUrl':'/deleteUser','requestType':'POST','onSuccess':'getUsersDetails(true,500)'},
900:{'requestUrl':'/login','requestType':'POST','onSuccess':"locationReplace('.'+response.view)"},
901:{'requestUrl':'/logout','requestType':'POST','onSuccess':"redirectTo('')"},


1100:{'requestUrl':'/getAssessmentTemplateDetails','requestType':'GET','onSuccess':'fillAssessmentTemplate(response)'},
1101:{'requestUrl':'/getAssessmentTemplateDetails','requestType':'GET','onSuccess':'assementTemplateBuilder(response)'},
1150:{'requestUrl':'/setAssessmentTemplateDetails','requestType':'POST','onSuccess':'getAssessmentTemplateDetails(true)'},
1151:{'requestUrl':'/deleteAssessmentTemplate','requestType':'POST','onSuccess':'getAssessmentTemplateDetails(true)'},


1200:{'requestUrl':'/getAssessmentDetails','requestType':'GET','onSuccess':'fillAssessmentDetails(response)'},
1201:{'requestUrl':'/getAssessmentDetails','requestType':'GET','onSuccess':'fillAssessmentViewerDetails(response)'},
1250:{'requestUrl':'/setAssessmentDetails','requestType':'POST','onSuccess':'getAssessmentDetails(true)'},
1251:{'requestUrl':'/deleteAssessment','requestType':'POST','onSuccess':'getAssessmentDetails(true)'},


1400:{'requestUrl':'/getCalendarEvents','requestType':'GET','onSuccess':'fillCalendarEventsDetails(response)'},
1450:{'requestUrl':'/setCalendarEvent','requestType':'POST','onSuccess':'getCalendarEventsDetails(true)'},
1451:{'requestUrl':'/deleteCalendarEvent','requestType':'POST','onSuccess':'getCalendarEventsDetails(true)'},


1600:{'requestUrl':'/getFeedbackTemplates','requestType':'GET','onSuccess':'fillFeedbackTemplateDetails(response)'},
1650:{'requestUrl':'/setFeedbackTemplateDetails','requestType':'POST','onSuccess':'getFeedbackTemplateDetails(true)'},
1651:{'requestUrl':'/deleteFeedbackTemplate','requestType':'POST','onSuccess':'getFeedbackTemplateDetails(true)'},

//////////////////////////////



1500:{'requestUrl':'/getAllWebPage','requestType':'GET','onSuccess':'fillWebPageDetails(response)'},
1550:{'requestUrl':'/setWebPage','requestType':'POST','onSuccess':'getAllWebPageDetails(true)'},
1551:{'requestUrl':'/deleteWebPage','requestType':'POST','onSuccess':'getAllWebPageDetails(true)'},

2000:{'requestUrl':'/getAllQuestion','requestType':'GET','onSuccess':'fillQuestionDetails(response)'},
2050:{'requestUrl':'/setQuestion','requestType':'POST','onSuccess':'getAllQuestionDetails(true)'},
2051:{'requestUrl':'/deleteQuestion','requestType':'POST','onSuccess':'getAllQuestionDetails(true)'},



//////////////////////////
10000:{'requestUrl':'/getAllWebPage','requestType':'GET','onSuccess':'fillmenuItems(response)'},





//1300:{'requestUrl':'/getAdminDashbord','requestType':'GET','onSuccess':'fillAdminDashBoardViewerDetails(response)'},
//2000:{'requestUrl':'/getSupperAdminDashbord','requestType':'GET','onSuccess':'fillSupperAdminDashBoardViewerDetails(response)'},





//ONLY HISTORY
1000:{'requestUrl':'/getChangeHistory','requestType':'POST','onSuccess':'fillReport(response)'}
};

function doApiAction(pageCode,oData,prefixData)
{
	console.log("pageCode : "+pageCode+"  prefixData:"+prefixData)
	if(actionUrl[pageCode])
	{
		if(prefixData)
	apiRequest(actionUrl[pageCode].requestType,actionUrl[pageCode].requestUrl+prefixData,oData,actionUrl[pageCode].onSuccess)
else
apiRequest(actionUrl[pageCode].requestType,actionUrl[pageCode].requestUrl,oData,actionUrl[pageCode].onSuccess)

	}
	
}

   function login()
{
	var formElement = document.forms.namedItem("login_form");
	var oData = new FormData(formElement);
	doApiAction(900,oData);
}
  
   function logout()
{
doApiAction(901,null);
}




/*function logout()
{
	  var request = new XMLHttpRequest();
		request.open("POST", "/logout");
	request.responseType = 'json';
	request.send();
	 request.onload = function() {
		 window.location.href = "/login";

}}*/
//Client - 1 to 99 (GET-(1 to 49),POST-(50 to 99)) 
//Enquiery - 100 to 199(GET-(100 to 149),POST-(150 to 199))
//WebPage- 200to 299
//Payment- 300to 399
//User - 500 to 599(GET-(500 to 549),POST-(550 to 599))
//PAcakge - 400 to 499(GET-(1 to 49),POST-(50 to 99))
//WorkoutChartDetails - 600 to 699 (GET-(1 to 49),POST-(50 to 99))
//Feedback - 700 to 799 (GET-(1 to 49),POST-(50 to 99))
//Feedback - 800 to 899 (GET-(1 to 49),POST-(50 to 99))
//AssessmentTemplate - 1100 to 1199 (GET-(1 to 49),POST-(50 to 99))
//Assessment - 1200 to 1299 (GET-(1 to 49),POST-(50 to 99))
//Dashbord - 1300 to 1399 (GET-(1 to 49),POST-(50 to 99))
//Calendar - 1400 to 1499 (GET-(1 to 49),POST-(50 to 99))