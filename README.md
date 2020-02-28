# Projects

To add the tag to Coldfusion
1. Save the GifSequenceWriter.java in the WEB_INF/classes directory
2. Compile the source code using JDK
   a. javac -classpath cf_root\WEB-INF\lib\cfx.jar GifSequenceWriter.java
   
REGISTER A JAVA CFX TAG IN THE COLDFUSION ADMINISTRATOR
In the ColdFusion Administrator, select Extensions > CFX Tags.
Click Register Java CFX.
Enter the tag name (for example, cfx_AnimatedGif).
Enter the class name without the .class extension (for example, GifSequenceWriter).
(Optional) Enter a description.
Click Submit.
You can now call the tag from a ColdFusion page.

example: <cfx_AnimatedGif>

<body>
<cfset imageList = "C:/ColdFusion2016/cfusion/wwwroot/2017164_1100rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1130rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1200rb.png,C:/ColdFusion2016/cfusion/wwwroot/2017164_1230rb.png">
<cfset imageList2 = "\\\\192.168.1.114\\cmsc495\\images/2017164_1100rb.png,\\\\192.168.1.114\\cmsc495\\images/2017164_1130rb.png,\\\\192.168.1.114\\cmsc495\\images\\2017164_1200rb.png,\\\\192.168.1.114\\cmsc495\\images\\2017164_1230rb.png">
<cfoutput>
<cfx_AnimatedGIF  files="#imageList2#" delay="300" output="C:/ColdFusion2016/cfusion/wwwroot/testSatellite3.gif">
</cfoutput>
<img src="testSatellite3.gif">
</body>
</html>