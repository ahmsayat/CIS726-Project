<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="cis726.web.manager.*" %>
<%@ page import="cis726.entities.*" %>
<%@ page import="cis726.jsp.*" %>
<%@ page import="java.util.*" %>
<%@ page import="cis726.db.dbClass.PatientProfile" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="css/screen.css" type="text/css" rel="stylesheet" media="screen,projection" />
		<!-- Stupid IE hacks>
    <!--[if lte IE 6]>
    <link href="css/msie.css" type="text/css" rel="stylesheet" media="screen,projection" />
    <![endif]-->
    <link rel="stylesheet" media="print" type="text/css" href="css/print.css" />
	<script type="text/javascript" src="amq/amq.js"></script>
	<script type="text/javascript">amq.uri='amq';</script>
	<script type="text/javascript" src="amline/swfobject.js"></script>

<!-- Initial  code Java script (used by widgets js)-->
<script type="text/javascript">
	/* General JavaScript Section.
	* Auhor: Luis Carranco
	* This functions/variables should be used from different .js widgets files
	* the JS variables should store values drawn from DB.
	*/
	function getFlashMovieByChartId(chartId){
		return document.getElementById(chartId);
	}
	//Add a function handler to listen device signal
	function addListenerWidget(topic, handler){
		amq.addListener(topic, 'topic://'+topic, handler);
	}
	//Remove listener of a topic
	function removeListenerWidget(topic){
	  	amq.removeListener(topic, 'topic://'+topic);
	}

	function showWidgetWarning(widgetDivId,titleDivId) // blink "on" state
	{
		document.getElementById(widgetDivId).className = "widgetWarning";
		document.getElementById(titleDivId).className = "titleWarning";
	}
	function hideWidgetWarning(widgetDivId,titleDivId)// blink "off" state
	{
		document.getElementById(widgetDivId).className = "widget";
		document.getElementById(titleDivId).className = "title";
	}
	function fireWidgetWarning(widgetDivId,titleDivId){
		try
	    {
			// Code from http://www.w3.org/TR/WCAG-TECHS/SCR22.html
			// toggle "on" and "off" states every 450 ms to achieve a blink effect
			// end after 6000 ms
			for(var i=900; i < 6000; i=i+900)
			{
				setTimeout(function(){showWidgetWarning(widgetDivId,titleDivId)},i);
				setTimeout(function(){hideWidgetWarning(widgetDivId,titleDivId)},i+450);
			}
	    }
	    catch(e)
	    {
	      alert(e);
	    }
	}

	//Variables that match with device types.
	//will set the name of device channel from DB
	var topicTemperature = '';
	var topicPressure = '';
	var topicPulse = '';
	var topicOxygen = '';
	var topicRespirationRate = '';
    var topicEkg = '';
    var topicInputOutput = '';
	//will set the deviceId from DB
	var deviceIdTemperature = '';
	var deviceIdPressure = '';
	var deviceIdPulse = '';
	var deviceIdOxygen = '';
	var deviceIdRespirationRate = '';
    var deviceIdEkg = '';
    var deviceIdInputOutput = '';


<%!PatientProfile profile=null ;%>
<%
/*
* JSP initiall code
* Author: Luis Carrano
* This java code will initialize screen with DB data.
* Here we can add more objetecs if needed for more widgets.
*/
	String currentDirectory = request.getRealPath(".");
	//get URL ?room=xxx
    String room = request.getParameter("room");
    int roomId = Integer.parseInt(room);
	//set controller for the screen.
	Controller controller = new Controller(roomId, currentDirectory);
	profile=controller.getProfile();
	//Get devices for room.
	//TODO: match this with authorization/authentication with login page
	ArrayList<RegisteredDevice> devices = controller.getRoomDevices("token12343");
    controller.InitWidgetData();
		for(Iterator<RegisteredDevice> it = devices.iterator();it.hasNext();){
			RegisteredDevice device = it.next();
%>
topic<%= device.getDeviceType() %>='<%= device.getChannel() %>';
deviceId<%= device.getDeviceType() %>='<%= device.getDeviceId() %>';
<%	}%>
</script>
		<!--
			HERE WE WOULD INCLUDE THE REFERENCES TO THE JAVASCRIPT FILES FOR EACH WIDGET
		-->
	<script type="text/javascript" src="js/temperature.js"></script>
	<script type="text/javascript" src="js/pulse.js"></script>
	<script type="text/javascript" src="js/pressure.js"></script>
	<script type="text/javascript" src="js/oxygen.js"></script>
	<script type="text/javascript" src="js/respiration.js"></script>
    <script type="text/javascript" src="js/ekg.js"></script>
    <title>Cerner &ndash; Smart Room</title>

</head>

  <body>

    <div id="layout">
      <div id="header">

        <div id="nav" class="box">
          <span id="logo"><a href="./" title="Company">smart<span class="light">room</span></a></span>
          <ul>
            <li><div id="vital"><%=profile.getPatientName()%><br /><span>Patient Name</span></div></li>
            <li><div id="vital"><%=profile.getPatientId()%><br /><span>Id</span></div></li>
            <li><div id="vital"><%=profile.getAge() %><br /><span>Age</span></div></li>
            <li><div id="vital"><%=profile.getWeight()%><br /><span>Weight</span></div></li>
	  				<li><div id="vital"><div id="valueHeaderPressure"><%= controller.getLastSignal("Pressure") %></div><span>Blood Pressure</span></div></li>
	  				<li><div id="vital"><div id="valueHeaderTemperature"><%= controller.getLastSignal("Temperature") %>F</div><span>Temperature</span></div></li>
	  				<li><div id="vital"><div id="valueHeaderPulse"><%= controller.getLastSignal("Pulse") %></div><span>Heart Rate</span></div></li>
	  			</ul>
        </div>

      <div id="container" class="box">

        <div id="obsah" class="content box">
          <div class="in">

            	<!--
							THE DIVS WITH THE WIDGETS FOLLOW HERE
						  -->
							<div id="widgetPulseEKG" class="widget">
								<div id="titlePulseEKG" class="title"><h1>Pulse Rate & EKG</h1></div>
								<div id="valuePulseEKG" class="value"><%= controller.getLastSignal("Pulse") %></div>
								<div id="ekg" class="chart">
									<strong>You need to upgrade your Flash Player</strong>
									<script type="text/javascript">
										loadEKGChart();
									</script>
								</div>
							</div>
							<div id="widgetPressure" class="widget">
								<div id="titlePressure" class="title"><h1>Blood Pressure</h1></div>
								<div id="valuePressure" class="value"><%= controller.getLastSignal("Pressure") %></div>
								<div id="chartPressureflashcontent" class="chart">
									<strong>You need to upgrade your Flash Player</strong>
									<script type="text/javascript">
										var so = new SWFObject("amline/amline.swf", "chartPressure", "350", "275", "8", "#FFFFFF");
										so.addVariable("path", "amline/");
										so.addVariable("chart_id", "chartPressure");
										so.addVariable("settings_file", encodeURIComponent("config/pressure_settings.xml"));
										so.addVariable("data_file", encodeURIComponent("config/pressureDevice"+deviceIdPressure+".xml"));
										so.write("chartPressureflashcontent");
									</script>
								</div>
							</div>
							<div id="widgetOxygen" class="widget">
								<div id="titleOxygen" class="title"><h1>Oxygen Levels</h1></div>
								<div id="valueOxygen" class="value"><%= controller.getLastSignal("Oxygen") %></div>
								<div id="chartOxygenflashcontent" class="chart">
									<strong>You need to upgrade your Flash Player</strong>
									<script type="text/javascript">
										var so = new SWFObject("amline/amline.swf", "chartOxygen", "350", "275", "8", "#FFFFFF");
										so.addVariable("path", "amline/");
										so.addVariable("chart_id", "chartOxygen");
										so.addVariable("settings_file", encodeURIComponent("config/oxygen_settings.xml"));
										so.addVariable("data_file", encodeURIComponent("config/oxygenDevice"+deviceIdOxygen+".xml"));
										so.write("chartOxygenflashcontent");
									</script>
								</div>
							</div>
							<div id="widgetTemperature" class="widget">
								<div id="titleTemperature" class="title"><h1>Temperature</h1></div>
								<div id="valueTemperature" class="value"><%= controller.getLastSignal("Temperature") %>F</div>
								<div id="chartTemperatureflashcontent" class="chart">
									<strong>You need to upgrade your Flash Player</strong>
									<script type="text/javascript">
										var so = new SWFObject("amline/amline.swf", "chartTemperature", "350", "275", "8", "#FFFFFF");
										so.addVariable("path", "amline/");
										so.addVariable("chart_id", "chartTemperature");
										so.addVariable("settings_file", encodeURIComponent("config/temperature_settings.xml"));
										so.addVariable("data_file", encodeURIComponent("config/temperatureDevice"+deviceIdTemperature+".xml"));
										so.write("chartTemperatureflashcontent");
									</script>
								</div>
							</div>
							<div id="widgetIOBalance" class="widget">
								<div id="titleIOBalance" class="title"><h1>I/O Balance</h1></div>
								<div id="ioBalance" class="chart">
									<strong>You need to upgrade your Flash Player</strong>
                                    <script type="text/javascript">
                                        // <![CDATA[
                                        var so = new SWFObject("amcolumn/amcolumn.swf", "ioBalance", "510", "275", "8", "#FFFFFF");
                                        so.addVariable("path", "amcolumn/")
                                        so.addVariable("settings_file", encodeURIComponent("config/io_balance_settings.xml"));
                                        so.addVariable('data_file', escape('config/io_balance_data.xml'));
                                        so.addVariable("preloader_color", "#999999");
                                        so.addParam("wmode", "opaque");
                                        so.write("ioBalance");
                                        // ]]>
                                    </script>

								</div>
							</div>
							<div id="widgetRespiration" class="widget">
								<div id="titleRespiration" class="title"><h1>Respiration Rate</h1></div>
								<div id="valueRespiration" class="value"><%= controller.getLastSignal("RespirationRate") %></div>
								<div id="chartRespirationflashcontent" class="chart">
									<strong>You need to upgrade your Flash Player</strong>
									<script type="text/javascript">
										var so = new SWFObject("amline/amline.swf", "chartRespiration", "350", "275", "8", "#FFFFFF");
										so.addVariable("path", "amline/");
										so.addVariable("chart_id", "chartRespiration");
										so.addVariable("settings_file", encodeURIComponent("config/respiration_settings.xml"));
										so.addVariable("data_file", encodeURIComponent("config/respirationDevice"+deviceIdRespirationRate+".xml"));
										so.write("chartRespirationflashcontent");
									</script>
								</div>
							</div>
          </div>
        </div>

        <div id="panel-right" class="box panel">
          <div class="in">

          <p>
            <span>Medical History</span><br />

                      <%=JSPSupport.printList(profile.getMedicalHistory()) %>


          </p>

          <p>
            <span>Allergies</span><br />

                    <%=JSPSupport.printList(profile.getAllergies())%>
          </p>

					<p>
            <span>To Do's</span><br />
						<!--
							PLACE THE TODOS IN HERE
						-->
            <ul>
            	<li>First item on the list</li>
							<li>Second item on the list</li>
              <li>Last item on the list</li>
            </ul>
          </p>

          </div>
        </div>

      </div>

        <div id="footer">
          <span class="f-left">&copy; 2009 <a href="http://www.ksu.edu">Kansas State University</a> | </span> <span class="f-left">Design: <a href="http://www.davidkohout.cz">David Kohout</a></span>
        </div>
    </div></div>

<!--  Code to start listening topics / widgetHandlers   -->
<script type="text/javascript">
	// Adding a listener to the topics when load page
	addListenerWidget(topicTemperature,temperature.rcvMessage);
	addListenerWidget(topicPressure,pressure.rcvMessage);
	addListenerWidget(topicPulse,pulse.rcvMessage);
	addListenerWidget(topicOxygen,oxygen.rcvMessage);
	addListenerWidget(topicRespirationRate,respiration.rcvMessage);
    addListenerWidget(topicEkg, ekg.rcvMessage);

</script>

  </body>
</html>