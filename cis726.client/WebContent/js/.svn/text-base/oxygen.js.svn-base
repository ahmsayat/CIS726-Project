/*
 * Oxygen widget.
 * 
 * Functions should call elements of main page using document.getElementById("elementID")
 * Author: Luis Carranco
 */
var ol = 100;
var oxygen = {
	rcvMessage : function(message) {
	    try
	    {
			ol = ol + 1;
			var words = message.nodeValue.split("#");
			var signal = words[1]; //second value after deviceId#
			if(isOxygenWarning(signal)) //Check if signal is a warning
			{
				//Call widget Main blinking function
				fireWidgetWarning("widgetOxygen","titleOxygen");
			}
			else
			{
				// Setting the data for amchart        
				var dt = "<chart><series><value xid='" + ol + "'>" + ol
						+ "</value></series><graphs><graph gid='1'><value xid='" + ol
						+ "'>" + signal + "</value></graph></graphs></chart>";
				// Get widget flashmovie 
				var flashMovie = getFlashMovieByChartId('chartOxygen');
				// Start listening and moving the graph (data already load from data.xml file)
				// Appending data to amchart
				flashMovie.appendData(dt, "1");
				// Updating widget text value
				document.getElementById("valueOxygen").innerHTML = signal;
			}
	    }
	    catch(e)
	    {
	      alert(e);
	    }
	}
};

function isOxygenWarning(signal){
	if(isNaN(signal)){ //Warning detected when the value is not a number 
		return true;
	}
	return false;
}