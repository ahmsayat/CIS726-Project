/*
 * Respiration widget.
 * 
 * Functions should call elements of main page using document.getElementById("elementID")
 * Author: Luis Carranco
 */
var rl = 10;
var respiration = {
	rcvMessage : function(message) {
	    try
	    {
			rl = rl + 1;
			var words = message.nodeValue.split("#");
			var signal = words[1]; //second value after deviceId#
			if(isRespirationWarning(signal)) //Check if signal is a warning
			{
				//Call widget Main blinking function
				fireWidgetWarning("widgetRespiration","titleRespiration");
			}
			else
			{
				// Setting the data for amchart        
				var dt = "<chart><series><value xid='" + rl + "'>" + rl
						+ "</value></series><graphs><graph gid='1'><value xid='" + rl
						+ "'>" + signal + "</value></graph></graphs></chart>";
				// Get widget flashmovie 
				var flashMovie = getFlashMovieByChartId('chartRespiration');
				// Start listening and moving the graph (data already load from data.xml file)
				// Appending data to amchart
				flashMovie.appendData(dt, "1");
				// Updating widget text value
				document.getElementById("valueRespiration").innerHTML = signal; 
			}
	    }
	    catch(e)
	    {
	      alert(e);
	    }
	}
};

function isRespirationWarning(signal){
	if(isNaN(signal)){ //Warning detected when the value is not a number 
		return true;
	}
	return false;
}