/*
 * Temperature widget.
 * 
 * Functions should call elements of main page using document.getElementById("elementID")
 * Author: Luis Carranco
 */
var kl = 60;
var temperature = {
	rcvMessage : function(message) {
	    try
	    {
			kl = kl + 1;
			var words = message.nodeValue.split("#");
			var signal = words[1]; //second value after deviceId#
			if(isTemperatureWarning(signal)) //Check if signal is a warning
			{
				//Call widget Main blinking function
				fireWidgetWarning("widgetTemperature","titleTemperature");
			}
			else
			{
				// Setting the data for amchart        
				var dt = "<chart><series><value xid='" + kl + "'>" + kl
						+ "</value></series><graphs><graph gid='1'><value xid='" + kl
						+ "'>" + signal + "</value></graph></graphs></chart>";
				// Get widget flashmovie
				var flashMovie = getFlashMovieByChartId('chartTemperature');
				// Start listening and moving the graph (data already load from data.xml file)
				// Appending data to amchart
				flashMovie.appendData(dt, "1");
				// Updating widget text value
				document.getElementById("valueTemperature").innerHTML = signal + 'F';
				document.getElementById("valueHeaderTemperature").innerHTML = signal + 'F';
			}
	    }
	    catch(e)
	    {
	      alert(e);
	    }
	}
};

function isTemperatureWarning(signal){
	if(isNaN(signal)){ //Warning detected when the value is not a number 
		return true;
	}
	return false;
}