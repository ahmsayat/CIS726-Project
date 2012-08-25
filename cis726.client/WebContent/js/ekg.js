var tick = 0;
var ekgArray = [0, 0, 0, 0, 1, 0.2, 0, -0.8, 5, -3, 0, 0, 0.5, 0.5, 0, 0, 0, 0, 1, 0.2, 0, -0.8, 5, -3, 0, 0, 0.5, 0.5];
var ekgTimer = 85;

// TODO: Replace with code that communicates with activemq
function readEKG(){
	lecture = ekgArray[tick];
	if (tick == 27)
		tick = 0;
	else
		tick++;
	return lecture;
}


function loadEKGChart(){
	var chart_id = "ekg";
	var so = new SWFObject("amline/amline.swf", 'ekgFlash', "350", "275", "8", "#000000");
	so.addVariable("path", "amline/");
	so.addVariable("settings_file", encodeURIComponent("config/ekg_settings.xml"));
	so.addVariable("chart_data", "0;0");

	so.write(chart_id);
}

function ekgTick(){
	var flashmovie = document.getElementById('ekgFlash');
	var data = tick + ';' + readEKG();
	flashmovie.appendData(data, "1");
	setTimeout("ekgTick();", ekgTimer);
}

/*var ekgLoaded = false;
function amChartInited(chart_id){
	if (!ekgLoaded){
		setTimeout("ekgTick();", ekgTimer);
		ekgLoaded = true;
	}	
}*/

var startRemovingTicks = false;

var ekg = {
	rcvMessage : function(message) {
	    try
	    {
			if (tick == 27)
				tick = 0;
			else
				tick++;
			// Setting the data for amchart
			var dt = tick + ';' + message.nodeValue.split('#')[1];
			// Get widget flashmovie
			var flashMovie = document.getElementById('ekgFlash');
			// Start listening and moving the graph (data already load from data.xml file)
			// Appending data to amchart
			if (startRemovingTicks)
				flashMovie.appendData(dt, "1");
			else
				flashMovie.appendData(dt);
			
			
			if (tick == 26)
				startRemovingTicks = true;
	    }
	    catch(e)
	    {
	      alert(e);
	    }
	}
};

