const oneDay = 24 * 60 * 60 * 1000;
var months=["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
var resultData;
var duePay=0;
var client_data_dump=[];
var account_management_list;
function script_loder()
{
	var linkObject=[];
	/*linkObject.push({element_type:"script",src:base_path+"/calender_js/select2.min.js"});
	linkObject.push({element_type:"script",src:base_path+"/calender_js/moment.min.js"});
	linkObject.push({element_type:"script",src:base_path+"/calender_js/daterangepicker.js"});
	linkObject.push({element_type:"script",src:base_path+"/calender_js/global.js"});
*/
	for(var count=0;count<linkObject.length;count++)
	{
	var element=document.createElement(linkObject[count].element_type);
	if(linkObject[count].element_type=='link')
		{
		element.rel=linkObject[count].rel;
		  element.href=linkObject[count].href;
		}
	else if(linkObject[count].element_type=='script')
		{
		element.src=linkObject[count].src;
		}
	  document.getElementsByTagName("head")[0].appendChild(element);
	}
}

	script_loder();



function res(search_key)
{
	  var request = new XMLHttpRequest();
	  	request.open("POST", base_path+"/Hawk_api_01/search/");
	  	 var  oData = new FormData();
	  	oData.append('client_Name', search_key);
	  request.send(oData);	  	
	  request.onload = function() {
	    var result = JSON.parse(this.response);
	    sessionCheck(result);
	  /*   
		$.each(result.data, function(index) {		
		 	//var continer="<div class='col-12 col-sm-6 col-md-4 d-flex align-items-stretch'><div class='card bg-light'><div class='card-header text-muted border-bottom-0'>"+result.data[index].client_id+"</div><div class='card-body pt-0'><div class='row'><div class='col-7'> <h2 class='lead'><b>"+result.data[index].client_Name+"</b></h2><p class='text-muted text-sm'><b>Package: </b> "+result.data[index].package+"</p><p class='text-muted text-sm text-danger'><b>Due_Amount: </b>"+result.data[index].due_Amount+"<ul class='ml-4 mb-0 fa-ul text-muted'><li class='small'><span class='fa-li'><i class='fas fa-lg fa-building'></i></span> Address: "+result.data[index].address+"</li> <li class='small'><span class='fa-li'><i class='fas fa-lg fa-phone'></i></span> Phone #: "+result.data[index].mobile_No+"</li></ul></div><div class='col-5 text-center'><img src='../../dist/img/user1-128x128.jpg' alt='' class='img-circle img-fluid'></div></div></div><div class='card-footer'><div class='text-right'><a href='#' class='btn btn-sm bg-teal'><i class='fas fa-comments'></i></a><a href='#' class='btn btn-sm btn-primary'><i class='fas fa-user'></i> View Profile</a></div></div></div></div>";
		//	result.data[index].client_Name
	 //	var continer=" <tr><td>"+(index+1)+"</td>"+" <td>"+result.data[index].client_Name+"</td>"+" <td>"+result.data[index].mobile_No+"</td>"+" <td>"+result.data[index].gender+"</td>"+"<td>"+result.data[index].visual_Status+"</td>"+"</tr>";
		//	$( "#enquiry_list" ).append(continer);
			
			
			
		
	}); */
	  }
}

function new_client()
  {
  var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem("new_client_form");
  	request.open("POST", base_path+"/Hawk_api_01/new_client/");
  	var formData = new FormData();
  var  oData = new FormData(formElement);
  request.responseType = 'json';
  oData.append ('due_Amount',$('#due_Amount').val());
  request.send(oData);
  request.onload = function() {
	  var result =(this.response).status;
	  sessionCheck(this.response);
	if(result&&result=="1")
	  {
		 alert("Client enrollment completed...."); 
		 
		  try
		  {
		  	  document.getElementById("new_client_form").reset(); 
		 enquiry_report_onload();
		  }
		  catch (err) {
				console.log("ERROR :  "+err);
					} 
	  }
	else
		{
		alert("Mobile Number already exists....");
		  try
		  {
		 enquiry_report_onload();
		  }
		  catch (err) {
				console.log("ERROR :  "+err);
					} 
		}
	  
  	  }

  }
  
  
  
  function  paid_amount()
  {
  	var traiff_Amount= $('#traiff_Amount').val();
  	var discount= $('#discount').val();
  	var amount_Paid= $('#paid_Amount').val();

    
  	 if(traiff_Amount)
  		 {
  		 if(discount)
  		 
  		 {
  			 if(amount_Paid)
  			 {
  				var due_Amount=traiff_Amount-discount;
  				due_Amount=due_Amount-amount_Paid;
  					$('#due_Amount').val(due_Amount); 
  				 
  			 }
  		 }
  		 }
  		 
  		 
  		  if($("#due_Amount").val()<0)
  		 {
  		 	$("#discount").val("0");
  		 	$("#paid_Amount").val("0");
  		 	$("#due_Amount").val("0");
  		 }
  		 
  		 
  		 
  		 
  		 if($("#due_Amount").val()==0)
		{
			$("#due_Date").val("");
		  document.getElementById("due_Date").disabled = true;
		}
  }
  

  function exportTableToExcel(tableID, filename,status){
  if(status==1)
  filename=filename+'_'+GetFormattedDateDDMMYY(new Date());
  else
     filename=location.href.split('/').slice(-1)+'_'+GetFormattedDateDDMMYY(new Date());  
      var downloadLink;
      var dataType = 'application/vnd.ms-excel';
      var tableSelect = document.getElementById(tableID);
      var tableHTML = tableSelect.outerHTML.replace(/ /g, '%20');
      
      // Specify file name
      filename = filename?filename+'.xls':'excel_data.xls';
      
      // Create download link element
      downloadLink = document.createElement("a");
      
      document.body.appendChild(downloadLink);
      
      if(navigator.msSaveOrOpenBlob){
          var blob = new Blob(['\ufeff', tableHTML], {
              type: dataType
          });
          navigator.msSaveOrOpenBlob( blob, filename);
      }else{
          // Create a link to the file
          downloadLink.href = 'data:' + dataType + ', ' + tableHTML;
      
          // Setting the file name
          downloadLink.download = filename;
          
          //triggering the function
          downloadLink.click();
      }
  }
  
  
  function client_quick_view_list_request()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/client_quick_view_list/");
  request.send();
  request.onload = function() {
    var result = JSON.parse(this.response);
    sessionCheck(this.response);
  	$.each(result.data.client_quick_view, function(index) {			
  	var client_Quick_View=result.data.client_quick_view[index];
  	  dataSource_list.push({"name": client_Quick_View.client_id});
  	  dataSource_list.push({"name": client_Quick_View.mobile_No});
  });
  }
  }
  
  
  function enquiry_delete(id)
  {
	  var request = new XMLHttpRequest();
  	request.open("GET", base_path+"/Hawk_api_01/enquiry_delete/"+id);
		request.send();
		request.onload = function() {
		sessionCheck(this.response);
			try { 	
				    var result = (JSON.parse(this.response).status);
				    if(result.includes("0"))
				    	{
				    	alert("Unable to Delete(ErrorCode-800).....,");
				    	}
				    else
				    	{
				    	alert("success to Delete.....,");
				    	onload_request();
				    	}
		} catch (err) {
		//console.log("ERROR :  "+err);
			} 
		}
  }
  
  
  function enquiry_Modal(id,status)
  {
	  
	var object_index = resultData.findIndex(obj => obj.id==id);
	//console.log("object_index "+object_index)
	$.each(resultData[object_index], function(key,value) {	
		try
		{
			document.getElementsByName(key)[status].value=value;
		}
		catch (err) {
			console.log("ERROR :  "+err);
				} 
	});
	
	
	
	if(status==0)
$("#enquiry_editModal").modal();  
	else if(status==1)
		$("#enquiry_entrollModal").modal();  
		
	 	document.getElementById("follow_Date").readOnly = true; 
  }  
  function update_enquiry_follow()
  {
	  var request = new XMLHttpRequest();
	  	var formElement = document.forms.namedItem("enquiry_follow");
	  	request.open("POST", base_path+"/Hawk_api_01/enquiry_follow/");
	  	var formData = new FormData();
	  var  oData = new FormData(formElement);
	  request.responseType = 'json';
	  request.send(oData);
	  request.onload = function() {
	  sessionCheck(this.response);
		  if(this.response.status==="1")
			  {
			  alert("Updated....");
		    	enquiry_report_onload();
			  }
		  else
			  {
			  alert("Invalid Update(ErrorCode-801)....");
			  }
	  	  }
	  }
  
  function enquiry_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/enquiry_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
var dataSet=[];
     resultData = JSON.parse(this.response).data;
    
	$.each(resultData, function(index) {

	
		 var columns=[];
	      columns.push(index+1);
	      columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].create_Date)));
	     columns.push(moment((new Date(resultData[index].create_Date))).format('YYYY-MM-DD'));
	       columns.push(resultData[index].client_Name);
	        columns.push(resultData[index].mobile_No);
	        columns.push(resultData[index].gender);
	        columns.push(resultData[index].visual_Status);
	        columns.push(resultData[index].follow_Date);
	         columns.push(GetFormattedDate_DDMMYY_to_YYMMDD(resultData[index].follow_Date));
	      //  columns.push("<textarea  class='form-control' rows='1'>"+resultData[index].remarks+"</textarea>");
	         columns.push("<p  style='max-width: 150px;'>"+resultData[index].remarks+"</p>");
	        columns.push("<button type='button' style='btn btn-info'     id='"+resultData[index].mobile_No+"' class='btn btn-danger'    onClick='enquiry_delete(this.id)'>Delete</button> <button  style='' type='button' id='"+resultData[index].id+"' class='btn btn-primary' onClick='enquiry_Modal(this.id,0)'>Edit</button> <button   style='' type='button' name ='"+resultData[index].id+"' class='btn btn-success'    onClick='enquiry_Modal(this.name,1)'>Enroll</button>" );
	 	      dataSet.push(columns);	
});
	
	   $('#bootstrap_table').DataTable( {
                     data: dataSet,
        scrollCollapse: true,
        paging:         false,
             columns: [
             { title: "Id" },
             { title: "Date",'iDataSort': 2 },
              {title: "SysDate", "bVisible": false},
                 { title: "Name" },
                 { title: "Mobile_no" },
                 { title: "Gender" },
                   { title: "Visual_status" },
                 { title: "Follow_date",'iDataSort': 8 },
                 {title: "SysDate1", "bVisible": false},
                 { title: "Remarks" },
                 { title: "Action" }
                 ]
         } );
         
  $("#enquiry_report").appendTo($("#parent_continer"));
  }
  }
/*  conversion_rate_report*/
  {
	 
	  
	  function conversion_rate_report_Modal(id)
	  {
		var object_index = resultData.findIndex(obj => obj.id==id);
	 $('[name="client_id"]').val(resultData[object_index].client_id); 
	 $('[name="client_Name"]').val(resultData[object_index].client_Name);
	 $('[name="mobile_No"]').val(resultData[object_index].mobile_No); 
	 duePay=resultData[object_index].payment_History.due_Amount;
	 	$.each(resultData[object_index].payment_History, function(key,value) {
		$("#"+key).val(value);	
		});
		 	$("#enquiry_editModal").modal();  
		 	document.getElementById("due_Date").readOnly = true; 
		 	$('[name="duePay"]').val("0");
		 	
	  }
	  function conversion_rate_report_onload()
	  {
	  var dataSet=[];
	  var request = new XMLHttpRequest();
	  	request.open("POST", base_path+"/Hawk_api_01/conversion_rate_report/");
	  request.send();
	  request.onload = function() {
	  sessionCheck(this.response);
	     resultData = JSON.parse(this.response).data;
		$.each(resultData, function(index) {		
	 var columns=[];
	      columns.push(index+1);
	      columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].create_Date)));
	     columns.push(moment((new Date(resultData[index].create_Date))).format('YYYY-MM-DD'));
	     columns.push(resultData[index].client_id);
	       columns.push(resultData[index].client_Name);
	        columns.push(resultData[index].mobile_No);
	        columns.push(resultData[index].gender);
	        columns.push(resultData[index].payment_History.category);
	           columns.push(resultData[index].payment_History.package_Duration);
	           columns.push(resultData[index].payment_History.paid_Amount);
	           columns.push(resultData[index].payment_History.due_Amount);
	              columns.push(resultData[index].payment_History.due_Date);
	         columns.push(GetFormattedDate_DDMMYY_to_YYMMDD(resultData[index].payment_History.due_Date));

	      	 	      dataSet.push(columns);	
});
	
	   $('#bootstrap_table').DataTable( {
                     data: dataSet,
             columns: [
             { title: "Id" },
             { title: "Joining Date",'iDataSort': 2 },
              {title: "SysDate", "bVisible": false},
               { title: "Client Id" },
                 { title: "Name" },
                 { title: "Mobile No" },
                 { title: "Gender" },
                   { title: "Caterogy" },
                   { title: "Package" },
                   { title: "Amount Paid" },
                   { title: "Due Amount" },
                 { title: "Due Date",'iDataSort': 11 },
                 {title: "SysDate1", "bVisible": false}
                 ]
         } );
		
	  }
	  }
  }
/*  workout_chart_followup_report*/
  function workout_chart_followup_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/workout_chart_followup_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
     resultData = JSON.parse(this.response).data;
     
     workout_view_fn(resultData.wod);
     resultData=resultData.client_quick_view;
var dataSet=[];
	$.each(resultData, function(index) {		
		{
			var mydate = new Date(resultData[index].create_Date);
		 var columns=[];
	      columns.push(index+1);
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].create_Date)));
	     columns.push(moment((new Date(resultData[index].create_Date))).format('YYYY-MM-DD'));
	        columns.push(resultData[index].client_id);
	       columns.push(resultData[index].client_Name);
	    	const diffDays = Math.round(Math.abs((mydate - new Date()) / oneDay));
 	
 	if(diffDays>=0&&diffDays<=5)
 	 	  columns.push("<p class='text-success'>"+(diffDays)+"</p>");	
 	else if(diffDays>=6&&diffDays<=10)
 		 columns.push("<p class='text-warning'>"+(diffDays)+"</p>");	
 	else if(10<diffDays)	
 	 columns.push("<p class='text-danger'>"+(diffDays)+"</p>");	
 	
 				columns.push(resultData[index].payment_History.category);
	           columns.push(resultData[index].payment_History.package_Duration);
	           columns.push(resultData[index].payment_History.target);	
	
	
	if(resultData[index].client_Quick_View.warm_ups==null)
 	columns.push("");	
 	else
 		columns.push(resultData[index].client_Quick_View.warm_ups);	
 		if(resultData[index].client_Quick_View.workout_Chart==null)
 				columns.push("");	
 			else
 	columns.push("<p class='text-danger'>"+(resultData[index].client_Quick_View.workout_Chart)+"</p>");	
		if(resultData[index].client_Quick_View.stretching==null)
 			columns.push("");	
 			else
 	columns.push("<p class='text-danger'>"+(resultData[index].client_Quick_View.stretching)+"</p>");
 	columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon ' onClick='workout_chart_followup_Modal(this.id)'>Edit</button>");
 	dataSet.push(columns);	
 		}
});
	
	
	 $('#bootstrap_table').DataTable( {
                     data: dataSet,
             columns: [
             { title: "Id"},
             { title: "Joining Date",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
             { title: "Client Id"},
             { title: "Name"},
             { title: "Days"},
             { title: "Caterogy"},
             { title: "Target"},
             { title: "Package Duration"},
             { title: "Warm ups"},
             { title: "Workout Chart"},
             { title: "Stretching"},
             { title: "Action"}
                 ]
         } );
	
	
	
	
	
  }
  }
  
  //Feedback
  function feedback_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/feedback_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
    //Feedback Follow up
     {
	 resultData = JSON.parse(this.response).data;
var dataSet=[];
	$.each(resultData, function(index) {
		try
		{	
		 var columns=[];
	     columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon ' onClick='workout_chart_followup_Modal(this.id)'>"+resultData[index].client_id+"</button>");
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].renewal_Date)));
	     columns.push(moment((new Date(resultData[index].renewal_Date))).format('YYYY-MM-DD'));
	       columns.push(resultData[index].client_Name);
	       columns.push(resultData[index].mobile_No); 	
	       columns.push(resultData[index].payment_History.package_Duration);
	       if(resultData[index].feedback_Info){
		 columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].feedback_Info.create_date)));
	}
	       else{
		  columns.push("<p  class='text-danger' >-</p>");
	}
	 	dataSet.push(columns);	
 		} catch (err) {
			console.log("ERROR :  "+err);
				} 
});
	
	
	 $('#feedback_follow_up_bootstrap_tablel').DataTable( {
                     data: dataSet,
             columns: [
           //  { title: "Id"},
           { title: "Client Id"},
             { title: "Package Start",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
             { title: "Name"},
             { title: "Mobile No"},
             { title: "Package Duration"},
             { title: "Previous Feedback"}
                 ]
         } );
         }
         //Feedback report
              {
	 resultData = JSON.parse(this.response).suporting_data;
var dataSet=[];
	$.each(resultData, function(index) {
	try
		{
		 var columns=[];
	     columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon ' onClick='feedbackupdate_Modal(this.id)'>"+resultData[index].client_id+"</button>");
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].renewal_Date)));
	     columns.push(moment((new Date(resultData[index].renewal_Date))).format('YYYY-MM-DD'));
	       columns.push(resultData[index].client_Name);
	       columns.push(resultData[index].mobile_No); 	
	       columns.push(resultData[index].payment_History.package_Duration);
	       columns.push(resultData[index].client_Quick_View.client_Status);
	       if(resultData[index].feedback_Info){
		var feedback_category=resultData[index].feedback_Info.feedback_category.split(",");
		var feedback_remarks=resultData[index].feedback_Info.remarks.split(",");		
		var feedback_status=resultData[index].feedback_Info.status.split(",");
		var container="<div>";
		var container1="<div>";
		var container2="<div>";
				$.each(feedback_remarks, function(index) {
					if(feedback_category[index]==1)
						container =container +"<p class='text-nowrap'>Actionable"+"</p><br>";
				 else
					container =container +"<p class='text-nowrap'>Non-Actionable"+"</p><br>";
					
				if(feedback_status[index]==1)
				container2 =container2 +"<p class='text-nowrap'>Completed"+"</p><br>";
				 else
					container2 =container2 +"<p class='text-nowrap'>Pending"+"</p><br>";
					
				 container1 =container1 +"<p>"+feedback_remarks[index]+"</p><br>";
	});	
			console.log(container1);
			 container =container +"</div>";
		 container1 =container1 +"</div>";
		 container2 =container2 +"</div>";
		 columns.push(container);
		  columns.push(container1);
columns.push(container2);
	}
	       else{
		columns.push("<p  class='text-danger' >-</p>");
		  columns.push("<p  class='text-danger' >-</p>");
		  columns.push("<p  class='text-danger' >-</p>");
	}
	 	dataSet.push(columns);	
 		} catch (err) {
			console.log("ERROR :  "+err);
				} 
});
	
	
	 $('#feedback_report').DataTable( {
                     data: dataSet,
             columns: [
           //  { title: "Id"},
           { title: "Client Id"},
             { title: "Package Start",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
             { title: "Name"},
             { title: "Mobile No"},
             { title: "Package Duration"},
             { title: "Status"},
             { title: "Category"},
             { title: "FeedBack"},
             { title: "Action"}
                 ]
         } );
         }
  }
  }

  function workout_chart_followup_Modal(id)
  {
	var object_index = resultData.findIndex(obj => obj.id==id);
 $('[name="client_id"]').val(resultData[object_index].client_id); 
 
 	$.each(resultData[object_index].client_Quick_View, function(key,value) {
	$("#"+key).val(value);	
	});
	$('#start_Date').val(GetFormattedDateDDMMYY(new Date(resultData[object_index].client_Quick_View.start_Date)));
	$('#end_Date').val(GetFormattedDateDDMMYY(new Date(resultData[object_index].client_Quick_View.end_Date)));

	 	$("#workout_chart_followup_Modal").modal();
	 	
	 
  }
  function workout_view_fn(wod)
  {
	  $.each(wod, function(index) {	
	  $("#workout_Chart").append("<option class='option' value='"+wod[index].workout_Planner+"'>"+wod[index].workout_Planner+"</option>");
		  $("#stretching").append("<option class='option' value='"+wod[index].workout_Planner+"'>"+wod[index].workout_Planner+"</option>");
		  $("#warm_ups").append("<option class='option' value='"+wod[index].workout_Planner+"'>"+wod[index].workout_Planner+"</option>");
	  });
  }
  
  function quick_view_update_fn(url)
  {
	var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem("workout_chart_client_quick_view");
  	request.open("POST", base_path+"/Hawk_api_01/"+url);
  	var formData = new FormData(formElement);
	formData.append ("update_code","1");  
   request.responseType = 'json';
  request.send(formData);
  request.onload = function() {
  sessionCheck(this.response);
	  if(this.response.status==="1")
	  {
	  alert("Updated....");
	  }
  else
	  {
	  alert("Invalid Update(ErrorCode-801)....");
	  }
	  location.reload(true);
  	  }
  }
  //Feedback
  
    function feedbackupdate(feedback_form)
  {
	var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem(feedback_form);
  	request.open("POST", base_path+"/Hawk_api_01/feedback_update");
  	var formData = new FormData(formElement);
	formData.append ("update_code","1");  
   request.responseType = 'json';
  request.send(formData);
  request.onload = function() {
  sessionCheck(this.response);
	  if(this.response.status==="1")
	  {
	  alert("Updated....");
	  }
  else
	  {
	  alert("Invalid Update(ErrorCode-801)....");
	  }
	  location.reload(true);
  	  }
  }
  
  
  
  
  
  
  
/*  pending_due_report*/
  function due_paid_amount()
  {
  
	var bal_amt=duePay-parseInt($("#duePay").val());
	//console.log("bal_amt  :"+bal_amt);
	 if(bal_amt>=0&&bal_amt<=duePay)
	 $("#due_Amount").val(bal_amt);
	 else
		 {
		 $("#due_Amount").val(duePay);
		 $("#duePay").val(0);
		 }
  } 
  function update_pending_due()
  {
  var request = new XMLHttpRequest();

  var formdata = new FormData();
  formdata.append('client_id', $('[name="client_id"]').val());
  formdata.append('duePay', $('[name="duePay"]').val());
  formdata.append('payment_Type', $('[name="payment_Type"]').val());
  formdata.append('due_Date', $('[name="due_Date"]').val());
  	
  	request.open("POST", base_path+"/Hawk_api_01/update_pending_due/");
  	var formData = new FormData();
  request.responseType = 'json';
  request.send(formdata);
  request.onload = function() {
  sessionCheck(this.response);
	  if(this.response.status==="1")
		  {
		  alert("Updated....");
		  }
	  else
		  {
		  alert("Invalid Update(ErrorCode-801)....");
		  }
	  location.reload(true);
  	  }
  }
  function pending_due_editModal(id)
  {
	var object_index = resultData.findIndex(obj => obj.id==id);
 $('[name="client_id"]').val(resultData[object_index].client_id); 
 $('[name="client_Name"]').val(resultData[object_index].client_Name);
 $('[name="mobile_No"]').val(resultData[object_index].mobile_No); 
 duePay=resultData[object_index].payment_History.due_Amount;
 	$.each(resultData[object_index].payment_History, function(key,value) {
	$("#"+key).val(value);	
	});
	 	$("#enquiry_editModal").modal();  
	 	document.getElementById("due_Date").readOnly = true; 
	 	$('[name="duePay"]').val("0");	
  }
  function pending_due_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/pending_due_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
     resultData = JSON.parse(this.response).data;
   var dataSet=[];

	$.each(resultData, function(index) {		
if(resultData[index].payment_History.due_Amount>0)
		{
		
			var mydate = new Date(resultData[index].renewal_Date);
		 var columns=[];
	      columns.push(index+1);
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].renewal_Date)));
	     columns.push(moment((new Date(resultData[index].renewal_Date))).format('YYYY-MM-DD'));
	        columns.push(resultData[index].client_id);
	       columns.push(resultData[index].client_Name);
		   columns.push(resultData[index].mobile_No);	
		   columns.push(resultData[index].payment_History.category);
	           columns.push(resultData[index].payment_History.package_Duration);
	var diffDays = Math.round(Math.abs((mydate - new Date()) / oneDay));
	columns.push(diffDays);
	columns.push(resultData[index].payment_History.paid_Amount);
 	columns.push("<p class='text-danger'>"+resultData[index].payment_History.due_Amount+"</p>");
 	
 	
 	mydate = new Date(resultData[index].payment_History.due_Date);
 	 diffDays = Math.round(Math.abs(( new Date()-mydate) / oneDay));
	if(diffDays>=0)
	{
	columns.push("<p class='text-danger'>"+resultData[index].payment_History.due_Date+"</p>");
	 columns.push(GetFormattedDate_DDMMYY_to_YYMMDD(resultData[index].payment_History.due_Date));
	
	}
 	 	else
 	 	{
 		 columns.push("<p class=''>"+resultData[index].payment_History.due_Date+"</p>");
 		  columns.push(GetFormattedDate_DDMMYY_to_YYMMDD(resultData[index].payment_History.due_Date));
 		 
 		 } 
 	
 	 columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon '    onClick='pending_due_editModal(this.id)'>Due Pay</button>");
dataSet.push(columns);
	}
});
	
	
	 $('#bootstrap_table').DataTable( {
                     data: dataSet,
             columns: [
             { title: "Id"}, 
                    { title: "Package Start",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
                    { title: "Client Id"},
                    { title: "Name"},
                    { title: "Mobile No"},
                      { title: "Caterogy"},
                     { title: "Package"},
                      { title: "Days"},
                         { title: "Amount Paid"},
                          { title: "Due Amount"},
                            { title: "Due Date",'iDataSort': 12 },
               			  {title: "SysDate1", "bVisible": false},
           
                            { title: "Payment"}
                 ]
         } );

  }
  }
  
/*  Renewal Report*/
  function update_Renewal_report()
  {
  var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem("client_Renewal");
  	request.open("POST", base_path+"/Hawk_api_01/client_Renewal/");
  	var formData = new FormData();
  var  oData = new FormData(formElement);
  request.responseType = 'json';
  request.send(oData);
  request.onload = function() {
  sessionCheck(this.response);
	  if(this.response.status==="1")
		  {
		  alert("Updated....");
		  }
	  else
		  {
		  alert("Invalid Update(ErrorCode-801)....");
		  }
	  location.reload(true);
  	  }
  }
  
  
  
  /*  update_client_details*/
  function update_client_details()
  {
  var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem("client_form");
  	request.open("POST", base_path+"/Hawk_api_01/update_client_details/");
  	var formData = new FormData();
  var  oData = new FormData(formElement);
  request.responseType = 'json';
  request.send(oData);
  request.onload = function() {
  sessionCheck(this.response);
	  if(this.response.status==="1")
		  {
		  alert("Updated....");
		  }
	  else
		  {
		  alert("Invalid Update(ErrorCode-801)....");
		  }
	  location.reload(true);
  	  }
  }


  
  function renewal_report_editModal(id,status)
  {
	var object_index = resultData.findIndex(obj => obj.id==id);
 $('[name="client_id"]').val(resultData[object_index].client_id); 
	$.each(resultData[object_index], function(key,value) {
	$("#"+key).val(value);	
	});
	$.each(resultData[object_index].payment_History, function(key,value) {
		$("#"+key).val(value);	
		});
	$("#cus_date").val(GetFormattedDateDDMMYY(new Date(resultData[object_index].payment_History.renewal_Date)));	
		//console.log(GetFormattedDateDDMMYY(new Date(resultData[object_index].payment_History.renewal_Date)));
		
		
		
		due_Amount=parseInt(resultData[object_index].payment_History.due_Amount);
		if(due_Amount>0&&status==1)
		{
		alert(resultData[object_index].client_id +" Have Pending  Due Amount "+due_Amount+" Need to close" );
		}
	else{
	$("#enquiry_editModal").modal();
	}
	 	
	 	
  }
  function client_report_editModal(id)
  {
	var object_index = resultData.findIndex(obj => obj.id==id);
 $('[name="client_id"]').val(resultData[object_index].client_id); 
 
 $.each(resultData[object_index], function(index,data) {
	 if(isObject(data))
		 {
			$.each(data, function(index,data) {
				if(index.includes("Date")&&data)
				{
				var mydate = new Date(data);
				if(mydate!="Invalid Date")
				data=GetFormattedDateDDMMYY(mydate);
				}
			$( "#"+index).val(data);
				});
		 }
	 else{
			if(index.includes("Date")&&data)
			{
			var mydate = new Date(data);
			data=GetFormattedDateDDMMYY(mydate);
			
			}
		 $("#"+index).val(data);
	 }
	});
	
	 	$("#client_report_editModal").modal();
	 	
  }
  
   function client_report_showModal(id)
  {
	var object_index = resultData.findIndex(obj => obj.id==id);
 $('[name="client_id"]').val(resultData[object_index].client_id); 
 
 
	// 	$("#client_report_showModal").modal();
	 	
  }

  
  function renewal_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/renewal_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
	   var dataSet=[];
     resultData = JSON.parse(this.response).data;
	$.each(resultData, function(index) {	
			 var columns=[];
	      columns.push(index+1);
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].create_Date)));
	     columns.push(moment((new Date(resultData[index].create_Date))).format('YYYY-MM-DD'));
	      columns.push(resultData[index].client_id);
	       columns.push(resultData[index].client_Name);
		   columns.push(resultData[index].mobile_No);
		   columns.push(resultData[index].payment_History.category);
	       columns.push(resultData[index].payment_History.package_Duration);
	        columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].renewal_Date)));
	        columns.push("<p class='text-danger'>"+resultData[index].payment_History.due_Amount+"</p>");
			//columns.push("<p class='text-danger'>"+resultData[index].payment_History.renewal_Date+"</p>");
			columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].payment_History.renewal_Date)));
			columns.push(moment((new Date(resultData[index].payment_History.renewal_Date))).format('YYYY-MM-DD'));
	 	    //columns.push(GetFormattedDate_DDMMYY_to_YYMMDD(resultData[index].payment_History.renewal_Date));
	       columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon '    onClick='renewal_report_editModal(this.id,1)'>Renewal</button>");
	   dataSet.push(columns);
});
	 $('#bootstrap_table').DataTable( {
                     data: dataSet,
             columns: [
                            
                           				{ title: "Id"}, 
                   						{ title: "Joining Date",'iDataSort': 2 },
            						    {title: "SysDate", "bVisible": false},
           								{ title: "Client Id"},
										{ title: "Name"},
										{ title: "Mobile No"},
										{ title: "Category"},
										{ title: "Package"},
										{ title: "Renewal Date"},
										{ title: "Pending Due"},
										{ title: "End Date",'iDataSort': 11},
										 {title: "SysDate1", "bVisible": false},
										{ title: "Action"}      
                 ]
         } );
  }
  
  }
  
  function client_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/client_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
	  $( "#enquiry_list" ).empty();
     resultData = JSON.parse(this.response).data;
        
 ////console.log("resultdata  : "+ JSON.stringify(resultData)); 
     workout_view_fn(JSON.parse(this.response).suporting_data);
	
	
	$.each(resultData, function(index) {	
	
	var temp1={};
	
	$.each(resultData[index], function(index,data) {	

	 if(isObject(data))
		 {
			$.each(data, function(index,data) {
				if(index.includes("Date"))
				{
				var mydate = new Date(data);
				if(mydate!="Invalid Date")
				{
				data=GetFormattedDateDDMMYY(mydate);
				}
				}			
			temp1[index]=data;
				});
		 }
	 else{
			if(index.includes("Date"))
			{
			//console.log("index  :: "+index +"   data  :: "+data);
			var mydate = new Date(data);
				data=GetFormattedDateDDMMYY(mydate);
			
			}
		temp1[index]=data;
	 }
	});
	
client_data_dump.push(temp1);
	});
	var dataSet =[];
	$.each(resultData, function(index) {		
 			var columns=[];
	      columns.push(index+1);
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].create_Date)));
	     columns.push(moment((new Date(resultData[index].create_Date))).format('YYYY-MM-DD'));
	     
	      columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon '  onClick='client_report_editModal(this.id)'>"+resultData[index].client_id+"</button>");
	       columns.push(resultData[index].client_Name);
		   columns.push(resultData[index].mobile_No);
		   columns.push(resultData[index].payment_History.category);
	       columns.push(resultData[index].payment_History.package_Duration);
	        columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].renewal_Date)));
	        	columns.push(moment((new Date(resultData[index].renewal_Date))).format('YYYY-MM-DD'));
	        
   columns.push((resultData[index].payment_History.traiff_Amount)-(resultData[index].payment_History.discount));
	columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-danger remove glyphicon ' style=' width:100px;' onClick='client_report_showModal(this.id)'>"+resultData[index].payment_History.due_Amount+"</button>");
 	 	columns.push("<p class='text-danger'>"+resultData[index].client_Quick_View.client_Status+"</p>");

columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].payment_History.renewal_Date)));
			columns.push(moment((new Date(resultData[index].payment_History.renewal_Date))).format('YYYY-MM-DD'));


 	const date1 = new Date(resultData[index].create_Date);
const date2 = new Date();
const diffTime = Math.abs(date2 - date1);
const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)); 
 	var feedback=resultData[index].client_Quick_View.feedback;
 	 if(!feedback&&diffDays>30)
 	columns.push("<p class='text-danger'>"+"Waiting"+"</p>");
 	else if(feedback==1)
 	columns.push("Done");
 	else if(feedback==0)
 	columns.push("NA");
  	else
 columns.push("NE");
 	var google_review=resultData[index].client_Quick_View.google_review;
 	 if(!google_review&&diffDays>30)
 	columns.push("<p class='text-danger'>"+"Waiting"+"</p>");
 	else if(google_review==1)
 	columns.push("Done");
 	else if(google_review==0)
columns.push("NA");
 		else
 columns.push("NE");
   	columns.push("<button type='button'class='btn btn-danger remove glyphicon glyphicon-trash'  id='"+resultData[index].client_id+"'  onClick='client_delete_click(this.id)' >-</button>");
	   dataSet.push(columns);	
});
	
	  
	 $('#bootstrap_table').DataTable( {
                     data: dataSet,
                   
             columns: [
             { title: "Id"}, 
             { title: "Joining_Date",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
                   { title: "client_id"},
       									{ title: "Client_Name"},
										{ title: "Mobile_No"},
										{ title: "Category"},
										{ title: "Package"},
										{ title: "Renewal_Date",'iDataSort': 9},
										 {title: "SysDate0", "bVisible": false},
										{ title: "Package_Amount"},
										{ title: "Due_Amount"},
										{ title: "Status"},
										{ title: "End_Date",'iDataSort': 14},
										 {title: "SysDate1", "bVisible": false},
										{ title: "FeedBack"},
										{ title: "Google_Review"},
										{ title: "Action"}
                 ]
         } ); 
         
         
	  
  }
  
  }
  
  
  
   function client_attendance_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/attendance_report");
  		var formData = new FormData();
     	formData.append ("att_date",$("#att_date").val());
  request.send(formData);
  request.onload = function() {
  sessionCheck(this.response);
	  $( "#enquiry_list" ).empty();
     resultData = JSON.parse(this.response).data;
        
 ////console.log("resultdata  : "+ JSON.stringify(resultData)); 
     workout_view_fn(JSON.parse(this.response).suporting_data);
	
	
	$.each(resultData, function(index) {	
	
	var temp1={};
	
	$.each(resultData[index], function(index,data) {	

	 if(isObject(data))
		 {
			$.each(data, function(index,data) {
				if(index.includes("Date"))
				{
				var mydate = new Date(data);
				data=GetFormattedDateDDMMYY(mydate);
				
				}			
			temp1[index]=data;
				});
		 }
	 else{
			if(index.includes("Date")&&data)
			{
			var mydate = new Date(data);
			data=GetFormattedDateDDMMYY(mydate);
			
			}
		temp1[index]=data;
	 }
	});
	
client_data_dump.push(temp1);
	});
	$.each(resultData, function(index) {		
 	var continer=" <tr><td scope='row'>"+(index+1)+"</td>";
 	continer=continer+" <td scope='row'> "+GetFormattedDateDDMMYY(new Date(resultData[index].create_Date))+"</td>";	
 	continer=continer+"<td> <button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon '    onClick='client_report_editModal(this.id)'>"+resultData[index].client_id+"</button>";
 	continer=continer+" <td scope='row'> "+resultData[index].client_Name+"</td>";
 	continer=continer+" <td scope='row'>"+resultData[index].mobile_No+"</td>";
 	continer=continer+" <td scope='row'>"+resultData[index].payment_History.category+"</td>";
 	continer=continer+" <td scope='row'>"+resultData[index].payment_History.package_Duration+"</td>";
 	continer=continer+" <td scope='row'>"+resultData[index].payment_History.target+"</td>";
 	continer=continer+" <td scope='row' class='text-danger'>"+resultData[index].client_Quick_View.client_Status+"</td>";
 	continer=continer+" <td scope='row'>"+GetFormattedDateDDMMYY(new Date(resultData[index].payment_History.renewal_Date))+"</td>";
 	continer=continer+"</tr>";
		$( "#enquiry_list" ).append(continer);
});
	  $('#bootstrap_table1').DataTable( {} );
  }
  
  }
  
  
  
  
  
  
  
  
  
  
  
/*  feedback_report*/
  function feedback_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/feedback_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
	  $( "#enquiry_list" ).empty();
    var result = JSON.parse(this.response);
	$.each(result.data, function(index) {		
 	var continer="<tr><td>"+(index+1)+"</td>";
 	continer=continer+"<td>"+result.data[index].client_id+"</td>";
 	continer=continer+" <td>"+result.data[index].client_Name+"</td>";
 	continer=continer+" <td>"+result.data[index].mobile_No+"</td>";
 	continer=continer+" <td>"+result.data[index].gender+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.category+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.package_Duration+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.target+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.due_Amount+"</td>";
  	continer=continer+" <td>"+"<select name='feedback'  id='"+result.data[index].client_id+"'  onchange='feedback_update(this,this.name)' class='form-control custom-select'>";
  	 continer=continer+"<option selected disabled>Select one</option>";
	 continer=continer+"<option  class='option' value='1'>Yes</option>";
	 continer=continer+"<option  class='option' value='0'>No</option>";
	continer=continer+"</td>";
	continer=continer+"</tr>";
		$( "#enquiry_list" ).append(continer);	
});
	 $('.table').DataTable( {} );
  }
  }
  function feedback_update(feedback,key)
  {
        var request = new XMLHttpRequest();
	  	request.open("POST", base_path+"/Hawk_api_01/quick_view_update/");
     	var formData = new FormData();
     	formData.append (key,feedback.value);
     	formData.append ("update_code","2");
     	formData.append ('client_id',feedback.id);
        request.responseType = 'json';
        request.send(formData);
			request.onload = function() {
			sessionCheck(this.response);
				  location.reload(true);
			}

  }
  
/*  google_review_report*/
  function google_review_report_onload()
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/google_review_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
	  $( "#enquiry_list" ).empty();
    var result = JSON.parse(this.response);
	$.each(result.data, function(index) {		
 	var continer="<tr><td>"+(index+1)+"</td>";
 	continer=continer+"<td>"+result.data[index].client_id+"</td>";
 	continer=continer+" <td>"+result.data[index].client_Name+"</td>";
 	continer=continer+" <td>"+result.data[index].mobile_No+"</td>";
 	continer=continer+" <td>"+result.data[index].gender+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.category+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.package_Duration+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.target+"</td>";
 	continer=continer+" <td>"+result.data[index].payment_History.due_Amount+"</td>";
  continer=continer+" <td>"+"<select name='google_review'  id='"+result.data[index].client_id+"'  onchange='google_review_update(this)' class='form-control custom-select'>";
	
  continer=continer+"<option selected disabled>Select one</option>";
	 continer=continer+"<option  class='option' value='1'>Yes</option>";
	 continer=continer+"<option  class='option' value='0'>No</option>";
	 
 
	continer=continer+"</td>";
	continer=continer+"</tr>";
		$( "#enquiry_list" ).append(continer);	
});
	 $('.table').DataTable( {} );
  }
  }
  
  function google_review_update(feedback)
  {
        var request = new XMLHttpRequest();
	  	request.open("POST", base_path+"/Hawk_api_01/quick_view_update/");
     	var formData = new FormData();
     	formData.append ('google_review',feedback.value);
     	formData.append ('client_id',feedback.id);
     	formData.append ('update_code',"3");

        request.responseType = 'json';
        request.send(formData);
			request.onload = function() {
			sessionCheck(this.response);
				try { 	
					  location.reload(true);
			} catch (err) {
			console.log("ERROR :  "+err);
				} 
			}

  }
 /* admin_dashbord_onload*/

  function admin_dashbord_onload()
  {
	  var request = new XMLHttpRequest();
 	  	request.open("POST", base_path+"/Hawk_api_01/dasbord/");
  request.responseType = 'json';
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
	  var result=this.response;
	  if(result.visual_object.user_group==0)
	   $("#feedbackTab").empty();
	  
  	$("#enquiry_count").html(result.visual_object.enquiry_count);
  	$("#conversion_count").html(result.visual_object.conversion_count);
  	$("#workout_chart_followup_count").html(result.visual_object.workout_chart_followup_count);
	$("#pending_due_count").html(result.visual_object.pending_due_count+" - "+result.visual_object.pending_due_amount);
	$("#feedback_count").html(result.visual_object.feedback_count);
	$("#google_reviw_count").html(result.visual_object.google_reviw_count);
	$("#attendance_count").html(result.visual_object.attendance_count);	
	$("#insanity_assesment_count").html(result.visual_object.insanity_assesment_count);
	$("#renewal_count").html(result.visual_object.renewal_list_count);
	$("#client_count").html(result.visual_object.client_count);
	AreaChartplot(result.visual_object.client_details,result.visual_object.enquiry_details,result.visual_object.calendar_event,result.visual_object.client_details);	
  	  }  
  }
/*  calendar_report*/
  
  
  function calendar_report_onload(calendar_list)
  {
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/calendar_Info_report/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
	  $( "#enquiry_list" ).empty();
	  resultData = JSON.parse(this.response).data;
	$.each(resultData, function(index) {		
 	var continer="<tr><td>"+(index+1)+"</td>";
 	continer=continer+" <td>"+resultData[index].event_Date+"</td>";
 	continer=continer+" <td>"+resultData[index].title+"</td>";
 	continer=continer+" <td>"+resultData[index].message+"</td>";
 	continer=continer+" <td>"+resultData[index].font_color+"</td>";
 	continer=continer+" <td>"+resultData[index].backgroundColor+"</td>";	
 	continer=continer+" <td>"+resultData[index].status+"</td>";
 	continer=continer+"<td> <button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon '    onClick='calendar_editModal(this.id)'>Edit</button>";
 	continer=continer+" <button type='button' class='btn btn-danger remove glyphicon glyphicon-trash'    onClick='calendar_Info_delete("+resultData[index].id+")'>-</button></td>";
 	
	continer=continer+"</tr>";
		$( "#"+calendar_list).append(continer);	
});
	 $('.table').DataTable( {} );
  }
  }
  
  function calendar_Info_entry()
  {
  var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem("calendar_Info");
  	request.open("POST", base_path+"/Hawk_api_01/calendar_Info_entry/");
  	var formData = new FormData();
  var  oData = new FormData(formElement);
  request.responseType = 'json';
  request.send(oData);
  request.onload = function() {
  sessionCheck(this.response);
	  if(this.response.status==="1")
		  {
		  alert("Updated....");
		  }
	  else
		  {
		  alert("Invalid Update(ErrorCode-801)....");
		  }
	  location.reload(true);
  	  }
  }
  
  function calendar_editModal(id)
  {
	if(id)  
	  {
	var object_index = resultData.findIndex(obj => obj.id==id);
	
 $('[name="id"]').val(resultData[object_index].id); 
	$.each(resultData[object_index], function(key,value) {
	$("#"+key).val(value);	
	});
	$.each(resultData[object_index].payment_History, function(key,value) {
		$("#"+key).val(value);	
		});
	  }
		
	 	$("#calendar_editModal").modal();
	 	
  }
  function calendar_Info_delete(id)
  {
	  var request = new XMLHttpRequest();
  	request.open("GET", base_path+"/Hawk_api_01/calendar_Info_delete/"+id);
		request.send();
		request.onload = function() {
		sessionCheck(this.response);
				    var result = (JSON.parse(this.response).status);
				    if(result.includes("0"))
				    	{
				    	alert("Unable to Delete(ErrorCode-800).....,");
				    	}
				    else
				    	{
				    	alert("success to Delete.....,");
				    	}
		location.reload(true);
		}
		
  }
   function account_management_new_entry()
  {
  var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem("account_management");
  	request.open("POST", base_path+"/Hawk_api_01/account_management_entry/");
  	var formData = new FormData();
  var  oData = new FormData(formElement);
  request.responseType = 'json';
  request.send(oData);
  request.onload = function() {
  sessionCheck(this.response);
	  var result =(this.response).status;
		if(result&&result=="1")
		  {
			 alert("Completed...."); 
		  }
		else
			{
			alert("Invalid Entry....");
			}
		account_management_list_request();
  }
  }
  
  function account_management_list_request()
  {
	  $( "#enquiry_list" ).empty();
	  $('#month').empty().append('<option selected disabled>Select one</option>');
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/account_management_list/");
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
     account_management_list = JSON.parse(this.response).data;
    var present_date = new Date(); 
      const ONE_DAY = 1000 * 60 * 60 * 24;  
       account_management_list_filter(true);
     if(JSON.parse(this.response).user_roll=="admin")
    {  
     $( "#account_Management_Report" ).attr("hidden",false);
      $( "#revenue_Sources" ).attr("hidden",false);
    }
    else
    {
      $( "#account_Management_Report" ).attr("hidden",true);
      $( "#revenue_Sources" ).attr("hidden",true);
    
    }

  }
  }
  
  
  
  
  
  /*assement_module*/
  function new_assement_module_entry()
  {
  var request = new XMLHttpRequest();
  	var formElement = document.forms.namedItem("assesmentEntry");
  	request.open("POST", base_path+"/Hawk_api_01/assement_module_entry/");
  	var formData = new FormData();
  var  oData = new FormData(formElement);
  request.responseType = 'json';
  request.send(oData);
  request.onload = function() {
  sessionCheck(this.response);
	  alert("Submited....,");
	  //document.getElementById("assesmentEntry").reset();
	  window.location.reload();
  	  }
  }

  function serarch()
  {
  	  var request = new XMLHttpRequest();
      	request.open("POST", base_path+"/Hawk_api_01/search/");
      	var formData = new FormData();
      	formData.append ('client_id',$("#client_id").val());
      request.responseType = 'json';
      request.send(formData);
  	  request.onload = function() {
  	    var result =(this.response);
sessionCheck(this.response);
  		$.each(result.data, function(index,data) {
  			
  			if(index==='client_Quick_View')
  				{
  				$.each(data, function(index,data) {
  					$( "#"+index).val(data);
  				});
  				
  				}
  			else{
  				if(index==='client_id')
  				{
  					$("#client_id_assesment").val(data);
  				}
  				else{
  					$( "#"+index).val(data);
  				}	
  			}
  			
  		});
 }

  }
  


function sessionCheck(result)
{
if(result.sessionStatus==0)
	  {
	  redirection("");
	  return ;
	  }
}

	
	
	  function redirection(url) {
	  window.location.href =  base_path+"/"+url;
	}
	
	  function cus_date_check(cus_date)
  {	  
	  if(cus_date.checked)
	  {
		  document.getElementById("cus_date").disabled = false;;
		}	  
	  else
		  { 	
	 document.getElementById("cus_date").value="";
	  document.getElementById("cus_date").disabled = true;;
	
		  }
  }