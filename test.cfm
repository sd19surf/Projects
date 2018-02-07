<body>
<cfset imageList = "C:/ColdFusion2016/cfusion/wwwroot/2017164_1100rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1130rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1200rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1230rb.png">
<cfoutput>
<cfx_AnimatedGIF  files="#imageList#" delay="300" output="C:/ColdFusion2016/cfusion/wwwroot/testSatellite2.gif">
</cfoutput>
<img src="testSatellite2.gif">
</body>
</html>