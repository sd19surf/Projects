<body>
<cfset imageList = "C:/ColdFusion2016/cfusion/wwwroot/2017164_1100rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1130rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1200rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1230rb.png">
<cfset imageList2 = "\\\\192.168.1.114\\cmsc495\\images/2017164_1100rb.png,\\\\192.168.1.114\\cmsc495\\images/2017164_1130rb.png,\\\\192.168.1.114\\cmsc495\\images\\2017164_1200rb.png,\\\\192.168.1.114\\cmsc495\\images\\2017164_1230rb.png">
<cfoutput>
<cfx_AnimatedGIF  files="#imageList2#" delay="300" output="C:/ColdFusion2016/cfusion/wwwroot/testSatellite3.gif">
</cfoutput>
<img src="testSatellite3.gif">
</body>
</html>