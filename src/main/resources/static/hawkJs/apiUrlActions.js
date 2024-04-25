var actionUrl={
500:{'requestUrl':'/getAllUsers','requestType':'GET','onSuccess':'getUsersEntrysFill(response)'},
550:{'requestUrl':'/setUserDetails','requestType':'POST','onSuccess':'getUsersDetails(true,500)'},
551:{'requestUrl':'/deleteUser','requestType':'POST','onSuccess':'getUsersDetails(true,500)'},
900:{'requestUrl':'/login','requestType':'POST','onSuccess':"locationReplace('.'+response.view)"},
901:{'requestUrl':'/logout','requestType':'POST','onSuccess':"redirectTo('')"},



1400:{'requestUrl':'/getCalendarEvents','requestType':'GET','onSuccess':'fillCalendarEventsDetails(response)'},
1450:{'requestUrl':'/setCalendarEvent','requestType':'POST','onSuccess':'getCalendarEventsDetails(true)'},
1451:{'requestUrl':'/deleteCalendarEvent','requestType':'POST','onSuccess':'getCalendarEventsDetails(true)'},


//////////////////////////////



1500:{'requestUrl':'/getAllWebPage','requestType':'GET','onSuccess':'fillWebPageDetails(response)'},
1501:{'requestUrl':'/getAllViewName','requestType':'GET','onSuccess':'fillAllViewDetails(response)'},


1550:{'requestUrl':'/setWebPage','requestType':'POST','onSuccess':'getAllWebPageDetails(true)'},
1551:{'requestUrl':'/deleteWebPage','requestType':'POST','onSuccess':'getAllWebPageDetails(true)'},

2000:{'requestUrl':'/getAllQuestion','requestType':'GET','onSuccess':'fillQuestionDetails(response)'},
2001:{'requestUrl':'/getAllQtag','requestType':'GET','onSuccess':'fillQtagDetails(response)'},
2050:{'requestUrl':'/setQuestion','requestType':'POST','onSuccess':'getAllQuestionDetails(true)'},
2051:{'requestUrl':'/deleteQuestion','requestType':'POST','onSuccess':'getAllQuestionDetails(true)'},
2052:{'requestUrl':'/uploadQuestion','requestType':'POST','onSuccess':'getAllQuestionDetails(true)'},

3000:{'requestUrl':'/getAllView','requestType':'GET','onSuccess':'fillViewDetails(response)'},
3050:{'requestUrl':'/setView','requestType':'POST','onSuccess':'getAllViewDetails(true)'},
3051:{'requestUrl':'/deleteView','requestType':'POST','onSuccess':'getAllViewDetails(true)'},


4000:{'requestUrl':'/getAllDataLink','requestType':'GET','onSuccess':'fillDataLinkDetails(response)'},
4001:{'requestUrl':'/getAllWebPageCode','requestType':'GET','onSuccess':'fillWebPageCode(response)'},
4002:{'requestUrl':'/getAllViewCode','requestType':'GET','onSuccess':'fillViewCode(response)'},

4050:{'requestUrl':'/setDataLink','requestType':'POST','onSuccess':'getAllDataLinkDetails(true)'},
4051:{'requestUrl':'/deleteDataLink','requestType':'POST','onSuccess':'getAllDataLinkDetails(true)'},


5000:{'requestUrl':'/getAllListView','requestType':'GET','onSuccess':'fillListViewDetails(response)'},
5001:{'requestUrl':'/getAllSelectQtags','requestType':'GET','onSuccess':'fillQtags(response)'},



5050:{'requestUrl':'/setListView','requestType':'POST','onSuccess':'getAllListViewDetails(true)'},
5051:{'requestUrl':'/deleteListView','requestType':'POST','onSuccess':'getAllListViewDetails(true)'},




//////////////////////////
10000:{'requestUrl':'/getAllMenuItems','requestType':'GET','onSuccess':'fillmenuItems(response)'},
10001:{'requestUrl':'/getAllWebPage','requestType':'GET','onSuccess':'fillWebPage(response)'},
10002:{'requestUrl':'/getAllListView','requestType':'GET','onSuccess':'fillListView(response)'},

////////////////PRODUCT API
//11000:{'requestUrl':'/getAnswersByViewId','requestType':'GET','onSuccess':'fillAnswerDetails(response)'},

11001:{'requestUrl':'/getAllAnswer','requestType':'GET','onSuccess':'fillAnswerDetails(response)'},
11002:{'requestUrl':'/getDataLink','requestType':'GET','onSuccess':'fillDataLinkDetails(response)'},



11050:{'requestUrl':'/setAnswer','requestType':'POST','onSuccess':'getAnswersByViewId(true)'},
11051:{'requestUrl':'/getAnswersByViewId','requestType':'POST','onSuccess':'fillAnswerDetails(response)'},
11052:{'requestUrl':'/deleteAnswer','requestType':'POST','onSuccess':'getAnswersByViewId(true)'},



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