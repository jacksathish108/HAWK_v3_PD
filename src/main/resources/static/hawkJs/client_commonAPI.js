function exportTableToExcel(tableID, filename = ''){
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
/*function logout()
{
	request.open("POST", base_path+"/Hawk_api_01/client_logout/");
request.responseType = 'json';
request.send();
request.onload = function() {
	var result=this.response;	
	     if(result.status==0)
	    	 {
	    	 window.location.replace("/"+result.view);	    	 
	    	 }
	  }
}*/
function GetFormattedDate(date) {
    const d_date = new Date(date);
    const year = new Intl.DateTimeFormat('en', { year: 'numeric' }).format(d_date)
    const month = new Intl.DateTimeFormat('en', { month: '2-digit' }).format(d_date)
    const day = new Intl.DateTimeFormat('en', { day: '2-digit' }).format(d_date)

    return (year + "-" + month + "-" + day);

}