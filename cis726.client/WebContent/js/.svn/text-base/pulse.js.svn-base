/*
 * Pulse widget.
 * 
 * Functions should call elements of main page using document.getElementById("elementID")
 * Author: Luis Carranco
 */
/*
 * Pulse widget.
 *
 * Functions should call elements of main page using document.getElementById("elementID")
 * Author: Luis Carranco
 */
var pulse = {
	rcvMessage : function(message) {
	    try
	    {
			var words = message.nodeValue.split("#");
			var signal = words[1]; //second value after deviceId#
			if(isPulseWarning(signal)) //Check if signal is a warning
			{
				//Call widget Main blinking function
				fireWidgetWarning("widgetPulseEKG","titlePulseEKG");
			}
			else
			{
				// Updating widget text value
				document.getElementById("valuePulseEKG").innerHTML = signal;
				document.getElementById("valueHeaderPulse").innerHTML = signal;
			}
	    }
	    catch(e)
	    {
	      alert(e);
	    }
	}
};

function isPulseWarning(signal){
	if(isNaN(signal)){ //Warning detected when the value is not a number 
		return true;
	}
	return false;
}