  function client_quick_view_list_onload_request()
  {
	  $( "#enquiry_list").empty();
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/client_quick_view_list/");
  request.send();
  
  request.onload = function() {
  sessionCheck(this.response);
    var result = JSON.parse(this.response);
var dataSet=[];
	$.each(result.data.client_quick_view, function(index) {		

	 var columns=[];
	      columns.push(index+1);
		  columns.push(GetFormattedDateDDMMYY(new Date(result.data.client_quick_view[index].renewal_Date)));
	     columns.push(moment((new Date(result.data.client_quick_view[index].renewal_Date))).format('YYYY-MM-DD'));
	      columns.push(result.data.client_quick_view[index].client_id);
	       columns.push(result.data.client_quick_view[index].client_Name);
		   columns.push(result.data.client_quick_view[index].mobile_No);  
		   columns.push(result.data.client_quick_view[index].payment_History.category);
	      columns.push(GetFormattedDateDDMMYY(new Date(result.data.client_quick_view[index].payment_History.renewal_Date)));
			columns.push(moment((new Date(result.data.client_quick_view[index].payment_History.renewal_Date))).format('YYYY-MM-DD'));
	 	  columns.push(result.data.client_quick_view[index].payment_History.target);
 	columns.push("<button id='"+result.data.client_quick_view[index].client_Quick_View.workout_Chart+"'class='btn btn-success'   name='"+result.data.client_quick_view[index].client_id+"'  onclick='workout_list(this.id,this.name)'>"+result.data.client_quick_view[index].client_Quick_View.workout_Chart+"</button>");
 	columns.push(result.data.client_quick_view[index].client_Quick_View.client_Status);
 	dataSet.push(columns);
 
});
	workout_view_fn(result.data.wod);

	 $('#bootstrap_table').DataTable( {
                     data: dataSet,
             columns: [
             { title: "Id"}, 
             { title: "Package Start",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
                   { title: "Client Id"},
										{ title: "Name"},
										{ title: "Mobile No"},
										{ title: "Category"},
										{ title: "Package End",'iDataSort': 8},
										 {title: "SysDate0", "bVisible": false},
										{ title: "Target"},
										{ title: "Workout Chart"},
										{ title: "Status"}
                 ]
         } );















  }
  }
  
  
  function client_attendance_report_onload()
  {
	  $( "#enquiry_list").empty();
 var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/attendance_report");
  		var formData = new FormData();
     	formData.append ("att_date",$("#att_date").val());
  request.send(formData);
  request.onload = function() {
  sessionCheck(this.response);
    var result = JSON.parse(this.response);
var resultData=result.data.client_quick_view;


var dataSet =[];
	$.each(resultData, function(index) {		
 			var columns=[];
	      columns.push(index+1);
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].create_Date)));
	     columns.push(moment((new Date(resultData[index].create_Date))).format('YYYY-MM-DD'));
	      columns.push("<button id='"+resultData[index].client_id+"'class='btn btn-success'   onclick='attendance_by_client(this.id)'>"+resultData[index].client_id+"</td>");
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
			   	columns.push("<button type='button'class='btn btn-danger remove glyphicon glyphicon-trash'  id='"+resultData[index].client_id+"'  onClick='client_delete_click(this.id)' ></button>");
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
										{ title: "Renewal Date",'iDataSort': 9},
										 {title: "SysDate0", "bVisible": false},
										{ title: "Package Amount"},
										{ title: "Due Amount"},
										{ title: "Status"},
										{ title: "End Date",'iDataSort': 14},
										 {title: "SysDate1", "bVisible": false},
										{ title: "Action"}
                 ]
         } ); 
  }
  }
  
  
  function client_attendance_leave_report_onload()
  {
	  $( "#enquiry_list").empty();
 var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/attendance_leave_report");
  		var formData = new FormData();
     	formData.append ("att_date",$("#att_date").val());
  request.send(formData);
  request.onload = function() {
  sessionCheck(this.response);
    var result = JSON.parse(this.response);
var resultData=result.data.client_quick_view;


var dataSet =[];
	$.each(resultData, function(index) {		
 			var columns=[];
	      columns.push(index+1);
	        columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].renewal_Date)));
	       	columns.push(moment((new Date(resultData[index].renewal_Date))).format('YYYY-MM-DD'));
	      columns.push("<label  id='"+resultData[index].client_id+"'class=''   onclick='attendance_by_client(this.id)'>"+resultData[index].client_id+"</td>");
	       columns.push(resultData[index].client_Name);
		   columns.push(resultData[index].mobile_No);
		   columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-danger remove glyphicon ' style=' width:100px;'>"+resultData[index].leave_count+"</button>");
		   columns.push(resultData[index].payment_History.category);
	      // columns.push(resultData[index].payment_History.package_Duration);
	       // columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].client_Quick_View.end_Date)));
	        //	columns.push(moment((new Date(resultData[index].renewal_Date))).format('YYYY-MM-DD'));
	        
     // columns.push((resultData[index].payment_History.traiff_Amount)-(resultData[index].payment_History.discount));
	columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-danger remove glyphicon ' style=' width:100px;' onClick='client_report_showModal(this.id)'>"+resultData[index].payment_History.due_Amount+"</button>");
 	 	columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].payment_History.renewal_Date)));
			columns.push(moment((new Date(resultData[index].payment_History.renewal_Date))).format('YYYY-MM-DD'));
			  // 	columns.push("<button type='button'class='btn btn-danger remove glyphicon glyphicon-trash'  id='"+resultData[index].client_id+"'  onClick='client_delete_click(this.id)' ></button>");
	 
dataSet.push(columns);	
});
	
	  
	 $('#bootstrap_table0').DataTable( {
                     data: dataSet,
             columns: [
             { title: "Id"}, 
             { title: "Joining Date",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
                  					 { title: "Client Id"},
										{ title: "Name"},
										{ title: "Mobile No"},
										{ title: "Leave Count"},
										{ title: "Category"},
										//{ title: "Package"},
										//{ title: "Renewal Date",'iDataSort': 10},
										// {title: "SysDate0", "bVisible": false},
										//{ title: "Package Amount"},
										{ title: "Due Amount"},
										{ title: "End Date",'iDataSort': 10},
										 {title: "SysDate1", "bVisible": false},
										
                 ]
         } ); 
  }
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

  
  function client_delete_click(id)
  { 
	  var request = new XMLHttpRequest();
  	request.open("GET", base_path+"/Hawk_api_01/client_delete/"+id);
		request.send();
		request.onload = function() {
		sessionCheck(this.response);
			try { 	
			alert(id + "  Deleted");
				client_report_onload();
		} catch (err) {
		console.log("ERROR :  "+err);
			} 
			client_report_onload();
		}
  }
  function workout_list(planner,client_id)
  {  
  	if(client_id)
  		{
  		workout_client_id=client_id;
  		}	
  		if(planner=="")
  		{
  		planner="null";
  		}	
  var request = new XMLHttpRequest();
  	request.open("POST", base_path+"/Hawk_api_01/workout_report/"+planner+"/"+workout_client_id);
  request.send();
  request.onload = function() {
  sessionCheck(this.response);
	  $( "#tbl_posts_body" ).empty();
    var result_data = JSON.parse(this.response);
	    try {
			attendance_Info(result_data.suporting_data.attendance_Info,result_data.suporting_data.calendar_event);
						} catch (err) {
			console.log("attendance_Info:: error : "+err);
		}
        result_data = JSON.parse(this.response).data;
    
         $.each(result_data, function(index) {
  	
			
		var workoutcontiner = "<tr><td><label id='id'  name='"+(result_data[index].id)+"'    class='sn'>"+(index + 1)+"</label> </td>";
		workoutcontiner = workoutcontiner+ "<td><input type='text'  class='form-control text-danger' id='client_module"+index+"' name='client_module' value="+ (result_data[index].client_module)+ "  readonly='readonly'></td>"; 
	    workoutcontiner = workoutcontiner+ "<td> <select  id='day"+index+"' name='day' class='form-control custom-select'>"+days_count(result_data[index].client_module)+"</select></td>";
		workoutcontiner = workoutcontiner+ "<td> <textarea type='text'  class='form-control text-danger' id='work_group"+index+"'  name='work_group'>"+ (result_data[index].work_group)+ "</textarea></td>";
		workoutcontiner = workoutcontiner+ "<td>  <textarea type='text'   style='min-width: 200px;' class='form-control text-danger' id='workout"+index+"' name='workout' >"+(result_data[index].workout)+"</textarea></td>";
		workoutcontiner = workoutcontiner+ "<td>"+"<input type='text'   id='rep_Time"+index+"' name='rep_Time' class='form-control text-danger' value='"+result_data[index].rep_Time+"' >"+"</td>";
		workoutcontiner = workoutcontiner+ "<td>"+"<input type='text'   id='video_link"+index+"' name='video_link' class='form-control text-primary' value='"+result_data[index].video_link+"' >"+"</td>";

		workoutcontiner = workoutcontiner + "</div></div>";
		workoutcontiner=workoutcontiner+ "<td><div id='workout_Planner' name='"+planner+"' class='row'>";
		if(result_data[index].client_id)
			{
			workoutcontiner=workoutcontiner+ " <div class='col-sm-6'><button type='button' id='"+result_data[index].id+"' class='btn btn-danger remove glyphicon glyphicon-trash'    onClick='delete_click_workout(this.id)'>-<i class='fa fa-minius'></i></button></div>";
			}
		workoutcontiner=workoutcontiner+ "<div class='col-sm-6'>";
		workoutcontiner = workoutcontiner+" <button  class='btn btn-primary remove glyphicon glyphicon-pencil'  id='"+(index + 1)+"' onClick='workout_update_click(this.id)'>Update</button></div></div>";
		workoutcontiner = workoutcontiner+"</td>";

		workoutcontiner = workoutcontiner+ "</tr>";
		$("#tbl_posts_body").append(workoutcontiner);
		 $('#'+('day'+index)).val(result_data[index].day);		 
	
	
    });	
    
         data_plot(JSON.parse(this.response).suporting_data.assessment_module,JSON.parse(this.response).suporting_data.assessment_info);
         workout_list_by_client(JSON.parse(this.response).suporting_data.workout_list_by_client);
         $("#bootstrap_table_workout").dataTable();
  }
  }
  function days_count(client_module)
  {
	  var days_count=0;
  
	  if(client_module==('WOD'))
	  {
		  days_count=1;
	  }
	  else
		  {
		  days_count=30;
		  }
	  

	  var option_container="<option selected disabled>Select one</option>";
	  
	  for(var i=1;i<=days_count;i++)
	  {
		  option_container=option_container+"<option class='option' value='"+i+"'>"+i+"</option>";
	  }

	  return option_container;
  }
	function workout_update_click(row)
	{
	      var request = new XMLHttpRequest();
		request.open("POST",base_path+"/Hawk_api_01/workout_update/");
				var formData = new FormData();	  
				formData.append ("client_id", workout_client_id);
				var data_val=(document.getElementById("bootstrap_table_workout").rows[row]); 
			
				   $.each(data_val.cells, function(index) {
					   
					  var data_field=data_val.cells.item(index).innerHTML;
					  if($(data_field).attr('id')!=null){
						  if($(data_field).attr('id').includes('id')||$(data_field).attr('id').includes('workout_Planner'))
							{
							formData.append ($(data_field).attr('id'),  $(data_field).attr('name'));
							
							   ////console.log($(data_field).attr('id')+" ::  "+$(data_field).attr('name'));
							}
						  
						else
							{
							var dc_id=$("#"+($(data_field).attr('id'))).val();
							formData.append ($(data_field).attr('name'), dc_id);	
							 			}
						  
					  }
					
					 					   
					   
				   });	
		  	
		     	
		     	request.responseType = 'json';
		request.send(formData);
		request.onload = function() {
		sessionCheck(this.response);
			alert("workout_update success...");

			workout_list(formData.get("workout_Planner"),null);
		}
		
	}
	 
	  function delete_click_workout(id)
	  {
		  var request = new XMLHttpRequest();
	  	request.open("GET", base_path+"/Hawk_api_01/workout_delete/"+id);
			request.send();
			request.onload = function() {
			sessionCheck(this.response);
			 	try { 
					    var result = JSON.parse(this.response).status;
					    if(status==="0")
					    	{
					    	alert("Unable to Delete, Workout palner Mapped to Client.....,");
					    	workout_list(id);
					    	}
					    else
					    	{
					    	alert("success to Delete.....,");
					    	workout_list(id);
					    	}
					 
			 } catch (err) {
			console.log("ERROR :  "+err);
				}  
			}
	  }
	  
	  function new_enquiry_follow()
	  {
	  var request = new XMLHttpRequest();
	  	var formElement = document.forms.namedItem("new_client_form");
	  	request.open("POST", base_path+"/Hawk_api_01/enquiry_follow/");
	  	
	  	var formData = new FormData();
	  var  oData = new FormData(formElement);
	  request.responseType = 'json';
	  request.send(oData);
	  request.onload = function() {
	  sessionCheck(this.response);
		  try { 	
		    var result = ((this.response).status);
		    if(result.includes("0"))
		    	{
		    	alert("Unable to Save(Mobile number already exits).....,");
		    	}
		    else
		    	{
		    	alert("Success To Save.....,");
		    	document.getElementById("new_client_form").reset(); 
		    	}
	} catch (err) {
	console.log("ERROR :  "+err);
	} 
	  	  }
	  }
		function new_friday_challenge_entry() {
			var request = new XMLHttpRequest();
			var formElement = document.forms.namedItem("new_client_form");
			request.open("POST", base_path+"/Hawk_api_01/friday_challenge_entry/");
			var formData = new FormData();
			var oData = new FormData(formElement);
			request.responseType = 'json';
			request.send(oData);
			request.onload = function() {
			sessionCheck(this.response);
				friday_challenge_report_list_request();
			}
		}
		function friday_challenge_report_list_request() {
		var dataSet=[];
			var request = new XMLHttpRequest();
			request.open("POST", base_path+"/Hawk_api_01/friday_challenge_list/");
			request.send();
			request.onload = function() {
			sessionCheck(this.response);
				var result = JSON.parse(this.response);
				$.each(result.data.friday_challenge_list,function(index) {
									
									
					 var columns=[];
	      columns.push(index+1);
									columns.push(GetFormattedDateDDMMYY(new Date(result.data.friday_challenge_list[index].event_date)));
									//columns.push(GetFormattedDate_DDMMYY_to_YYMMDD(result.data.friday_challenge_list[index].event_date));
									columns.push(result.data.friday_challenge_list[index].wod);
									columns.push(result.data.friday_challenge_list[index].reps_count);
									columns.push(result.data.friday_challenge_list[index].target_Limit);
									columns.push("<button type='button' id='"+GetFormattedDateDDMMYY(new Date(result.data.friday_challenge_list[index].event_date))+"' name='"+result.data.friday_challenge_list[index].reps_count+"' class='btn btn-success'    onClick='friday_challenge_event_find_by_date(this)'>View</button>");
								dataSet.push(columns);	
											}); 
				   $('#bootstrap_table').DataTable( {
                     data: dataSet,
             columns: [
             { title: "Id"}, 
                    { title: "Event Date",'iDataSort': 2 },
             {title: "SysDate", "bVisible": false},
                    { title: "WOD"},
                    { title: "Time/Reps count"},
                    { title: "Target"},
                      { title: "Rank"}
                 ]
         } );
				  
		  
				  
				  
				$('#wOD').find('option').remove();
				$("#wOD").append(
						" <option selected disabled>Select one</option>");

				$.each(result.data.wod, function(index) {
					$("#wOD").append(
							"<option class='option' value='"+result.data.wod[index].workout_Planner+"'>"
									+ result.data.wod[index].workout_Planner
									+ "</option>");

				});
			}
		}
		 function friday_challenge_event_find_by_date(event)
		  { 
				$("#rank_list").empty();
			 let formData = new FormData();
			 formData.append('event_date', event.id);
			 formData.append('Reps_count', event.name);
			 
			  var request = new XMLHttpRequest();
		  	request.open("POST", base_path+"/Hawk_api_01/friday_challenge_event");	 
			request.send(formData);
				request.onload = function() {
				sessionCheck(this.response);
					try { 	
						var result = JSON.parse(this.response).data;
						 	$.each(result,function(index) {	  
						  
							var continer = " <tr><td>"+ (index + 1)+ "</td>";
							continer=continer+ "<td>"+ result[index].client_id+ "</td>";
							continer=continer+ "<td>"+ result[index].wod+ "</td>";
							continer=continer+ "<td>"+ result[index].reps_count+ "</td>";
							continer=continer+ "<td>"+ result[index].target_Limit+ "</td>";
							continer=continer+ "<td>"+ result[index].competition+ "</td>";
							
							
							continer=continer+ "</tr>";
							$("#rank_list").append(continer);
						  
						  
						}); 
						 	
						 	$("#model_event_date").text(event.id);
					 	$("#fridayChallengeBord").modal();
					 	 $('.table').DataTable( {} );
					 	
					 			 	
				} catch (err) {
				console.log("ERROR :  "+err);
					} 
				}
		  }
		  function insanity_assesment_report_onload()
		  {
		  var request = new XMLHttpRequest();
		  	request.open("POST", base_path+"/Hawk_api_01/insanity_assesment_report/");
		  request.send();
		  request.onload = function() {
		  sessionCheck(this.response);
			  var dataSet=[];
			  resultData = JSON.parse(this.response).data;
			$.each(resultData, function(index) {
			var columns=[];
			columns.push(index+1);
				columns.push("<button type='button' id='"+resultData[index].id+"' class='btn btn-success remove glyphicon '    onClick='renewal_report_editModal(this.id,0)'>Assessment</button>");
		  columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].create_Date)));
	     columns.push(moment((new Date(resultData[index].create_Date))).format('YYYY-MM-DD'));
	      columns.push(resultData[index].client_id);
	       columns.push(resultData[index].client_Name);
		   columns.push(resultData[index].mobile_No);
		   columns.push(resultData[index].payment_History.category);
	       columns.push(resultData[index].payment_History.package_Duration);
	       columns.push(resultData[index].payment_History.target);
	       ////console.log(JSON.stringify(resultData[index]));
	       columns.push(resultData[index].client_Quick_View.client_Status);
	        columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].renewal_Date)));
	        columns.push("<p class='text-danger'>"+resultData[index].payment_History.due_Amount+"</p>");
			columns.push(GetFormattedDateDDMMYY(new Date(resultData[index].payment_History.renewal_Date)));
			columns.push(moment((new Date(resultData[index].payment_History.renewal_Date))).format('YYYY-MM-DD'));
	 	   	   dataSet.push(columns);
			
		});
			 $('#bootstrap_table').DataTable( {
                     data: dataSet,
             columns: [
                                      
                           				{ title: "Id"}, 
                           				{ title: "Action"},
                   						{ title: "Joining Date",'iDataSort': 3 },
            						    {title: "SysDate", "bVisible": false},
           								{ title: "Client Id"},
										{ title: "Name"},
										{ title: "Mobile No"},
										{ title: "Category"},
										{ title: "Target"},
										{ title: "Status"},
										{ title: "Package"},
										{ title: "Renewal Date"},
										{ title: "Pending Due"},
										{ title: "End Date",'iDataSort': 13},
										 {title: "SysDate1", "bVisible": false},
										      
                 ]
         } );
		  }
		  $("#insanity_assesment_report").appendTo($("#parent_continer"));
		  }
		  function new_assement_information_module_entry()
		  {
		  var request = new XMLHttpRequest();
		  	var formElement = document.forms.namedItem("new_client_form");
		  	request.open("POST", base_path+"/Hawk_api_01/assement_information_module_entry/");
		  	
		  	
		  	 var couter=$("#test_count").val();
		  	

		  	var formData = new FormData();
		  var  oData = new FormData(formElement);
		  
			 request.responseType = 'json';
			 
			
			
			
			for(var i=1;i<=couter;i++)
			 {
			 oData["test"]=$("#test_"+i).val();
			 
			 
			  request.send(oData);
			  request.onload = function() {
			  sessionCheck(this.response);
				  var result = (JSON.parse(this.response).status);
				    if(result.includes("0"))
				    	{
				    	alert("Invalid Data.....,");
				    	}
				    else
				    	{
				    	alert("Success to Save.....,");
				    	  report_list_request();
				    	}
				  
				  
				  report_list_request();
			  	  }
			 
			 }

		  }
		  function assement_information_module_list()
		  {
			  $( "#enquiry_list" ).empty();
		  var request = new XMLHttpRequest();
		  	request.open("POST", base_path+"/Hawk_api_01/assement_information_module_list/");
		  request.send();
		  request.onload = function() {
		  sessionCheck(this.response);
		    var result = JSON.parse(this.response);
			$.each(result.data, function(index) {			
		 	var continer=" <tr><td>"+(index+1)+"</td>"+" <td>"+result.data[index].assement_Type+"</td>"+" <td>"+result.data[index].test+"</td><td> <button type='button' id='"+result.data[index].id+"' class='btn btn-danger remove glyphicon glyphicon-trash'    onClick='insanity_workout_module_delete(this.id)'>-</button></td></tr>";
				$( "#enquiry_list" ).append(continer);
		});
		  }
		  }
		  
		  
		  function insanity_workout_module_delete(id)
		  {
			  var request = new XMLHttpRequest();
		  	request.open("GET", base_path+"/Hawk_api_01/insanity_workout_module_delete/"+id);
				request.send();
				request.onload = function() {
				sessionCheck(this.response);
					try { 	
						    var result = (JSON.parse(this.response).status);
						    if(result.includes("0"))
						    	{
						    	alert("Unable to Delete, Workout Module Mapped to Client.....,");
						    	}
						    else
						    	{
						    	alert("success to Delete.....,");
						    	  assement_information_module_list();
						    	}
						 
				} catch (err) {
				console.log("ERROR :  "+err);
					} 
				}
		  }
		  function get_client_details()
		  {
		  var request = new XMLHttpRequest();
		  	request.open("get", "/sudo/Hawk_api_01/get_client_details/",true);
		 	  request.onload = function () {
		 	  sessionCheck(this.response);
		 // alert(" data   :: "+this.response);
		  var result = JSON.parse(this.response);
		 // alert(" result   :: "+result);
		 
			$.each(result, function(index) {
				
				
				var continer="<div class='col-12 col-sm-6 col-md-4 d-flex align-items-stretch'><div class='card bg-light'><div class='card-header text-muted border-bottom-0'>"+result[index].client_id+"</div><div class='card-body pt-0'><div class='row'><div class='col-7'> <h2 class='lead'><b>"+result[index].client_Name+"</b></h2><p class='text-muted text-sm'><b>Package: </b> "+result[index].package+"</p><p class='text-muted text-sm text-danger'><b>Due_Amount: </b>"+result[index].due_Amount+"<ul class='ml-4 mb-0 fa-ul text-muted'><li class='small'><span class='fa-li'><i class='fas fa-lg fa-building'></i></span> Address: "+result[index].address+"</li> <li class='small'><span class='fa-li'><i class='fas fa-lg fa-phone'></i></span> Phone #: "+result[index].mobile_No+"</li></ul></div><div class='col-5 text-center'><img src='./dist/img/user1-128x128.jpg' alt='' class='img-circle img-fluid'></div></div></div><div class='card-footer'><div class='text-right'><a href='#' class='btn btn-sm bg-teal'><i class='fas fa-comments'></i></a><a href='#' class='btn btn-sm btn-primary'><i class='fas fa-user'></i> View Profile</a></div></div></div></div>";
				
				$( "#client_details_continer" ).append(continer);
		});
		} 
		request.send();
		  }
		  
		  
		  
		  
		  
function assement_type_change()
  {	
		 $("#assement_type").children("option:selected").each(function(i){
	       var assement_type=$(this).val();
	     	var continer="<div class='row'><div class='col-md-2'><div class='form-group'><input name='assement_Type' class='form-control' value='"+assement_type+"'readonly/> </div></div>";
	     		{		
     continer=continer+"<div class='col-md-2'><div class='form-group'><select  name='test' class='form-control custom-select'>";
var drop_downlist=dropdown_list[assement_type].split(",");
continer=continer+"<option selected disabled>Select one</option>";     		
$.each(drop_downlist, function(index) {
	 continer=continer+"<option class='option' value='"+drop_downlist[index]+"'>"+drop_downlist[index]+"</option>";
});
		    	continer=continer+"</select></div></div>";
	     		}
	    	  	continer=continer+("<div class='col-md-2'><div class='form-group'><input type='text' name='measurement' class='form-control'></div></div>");
	    	  	continer=continer+("<div class='col-md-3'><div class='form-group'><input type='text' name='remarks' class='form-control'></div></div>");
	    	  	continer=continer+("<div class='col-md-3'><div class='form-group'><button type='button' id='"+"1"+"' class='btn btn-danger remove glyphicon glyphicon-trash'    onClick='delete_row(this)'><i class='fa fa-minius'>-</i></button></div></div></div>");
	     	$("#assement_continer").append(continer);
	        });	  
  }
  
	///attendance_Info
	  function attendance_Info(attendance_details,calendar_event)
	{
		
		  $( "#attendance_calendar").empty();
			var dates_list = [];
			$.each((attendance_details), function(list_idex) {
				dates_list.push({
					title : 'P',
					start : attendance_details[list_idex],
					description : '',
					className : 'blue main'
				});
			});
			
			$.each((calendar_event), function(list_idex) {
							dates_list.push({
					title : calendar_event[list_idex].title,
					start : GetFormattedDate(calendar_event[list_idex].event_Date),
					description : calendar_event[list_idex].message,
					className : 'success'
					
				});
			});

			
			
			
			
			var initialTimeZone = 'local';
			var calendarEl = document.getElementById('attendance_calendar');
			var calendar = new FullCalendar.Calendar(calendarEl, {
				plugins : [ 'interaction', 'dayGrid', 'timeGrid', 'list' ],
				events : dates_list
			});
			calendar.render();
		}
		
	  
function delete_row(row)
{
	  $(row).closest(".row").remove();
}