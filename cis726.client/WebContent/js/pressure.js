/*
 * Preassure widget.
 * 
 * Functions should call elements of main page using document.getElementById("elementID")
 * Author: Luis Carranco
 */
var pr = 30;
var pressure = {
	rcvMessage : function(message) {
	    try
	    {
			pr = pr + 1;
			var words = message.nodeValue.split("#");
			var signal = words[1]; //second value after deviceId#
			if(isPressureWarning(signal)) //Check if signal is a warning
			{
				//Call widget Main blinking function
				fireWidgetWarning("widgetPressure","titlePressure");
			}
			else
			{
				var pressureValues = signal.split("/");
				// Setting the data for amchart
				var dt = "<chart><series><value xid='" + pr + "'>" + pr
						+ "</value></series><graphs><graph gid='1'><value xid='" + pr
						+ "'>" + pressureValues[1] + "</value></graph><graph gid='2'><value xid='" + pr
						+ "'>" + pressureValues[0] + "</value></graph></graphs></chart>";
				// Get widget flashmovie 
				var flashMovie = getFlashMovieByChartId('chartPressure');
				// Start listening and moving the graph (data already load from data.xml file)
				// Appending data to amchart
				flashMovie.appendData(dt, "1");
				// Updating widget text value
				document.getElementById("valuePressure").innerHTML = signal;
				document.getElementById("valueHeaderPressure").innerHTML = signal;
			}
	    }
	    catch(e)
	    {
	      alert(e);
	    }
	}
};

function isPressureWarning(signal){
	//TODO: recognize when is a pressure warning.
/*	if(isNaN(signal)){ //Warning detected when the value is not a number 
		return true;
	}
	*/
	return false;
}