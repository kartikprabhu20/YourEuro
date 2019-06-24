---
layout: post
title: "Advanced Prototype"
date: 2019-06-04
---

<html>
        <body style="background-color:powderblue;">
                <h1>Overview</h1>
                <p> Hello, welcome to our third blog. In the previous blog we introduced about the requirements, class diagrams and development strategy. In this blog we discuss about our design pattern, Implementation and User interface design.</p><hr><br>
<h1>Design Pattern</h1><br>
<p>The objective of our App is to keep track of money transactions on a daily basis and also to give the summarized report.</p> 
                <p><b>MVC architecture:</b> It is very difficult to create the user interfaces, control logic and databases together. We chose to follow MVC architecture in the development of our App to overcome this issue. MVC is an acronym for Model-View-Controller. By following this architecture, we can separate our Model (database), Views (activities), and Controller (control logic) and it allows each to change without affecting the other. </p>
  
<p> The CashRecord class is our core entity and every transaction is an object of this class. The created objects will be saved in the database. </p>

<p><b>Model:</b> CashRecordDatabase is our Model which extends RoomDatabase and will be used for interaction with the Database. </p>

<p><b>Controller:</b> MoneyControlManager is our main controller. Based on the request from the user it will perform operations on the model and modify the display accordingly.</p>

<p><b>View:</b> DetailInputActivity is our main view through which user gives the transaction details and it notifies MoneyControlManager when user presses save button. </p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/MVCArchitecture.jpeg?raw=true" width="800" height="350"><br>
<p><b>Builder pattern:</b> Alert dialogs are created using builder patterns. Using builder pattern multiple representations can be made using same construction method. We can include only the required fields while constructing a Alert dialog using builder pattern.</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.AlertDialog.PNG?raw=true" width="750" height="325">
<br>
<p><b>Adapter design pattern:</b> Adapter design pattern are used for setting recycler views. Adapter controls the Recycler view by getting data directly from the database. Our CashRecordAdapter will be used to set data in recycler views in MainActivity and it will get the data from CashRecordDatabase. MoneyControlManager will have CashRecordAdapter instance (MVC pattern).</p>

<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.AdapterviewFinal.jpeg?raw=true" width="750" height="325">
<br>

<p><b>Singleton pattern:</b> Our controller follows singleton pattern, since it interacts with the database and we want this interaction to be synchronised. Meaning multiple objects of the controller should not interact with database simultaneously. </p>
<br>
<p><b>Observer design pattern:</b> Observer design patterns are used whenever an event should be performed automatically. We are using this pattern for executing recurring events.<br>
        Broadcast receiver: It can be used to register for a required event. All registered receivers will be notified by android runtime once the required event happens.<br>
        Pending intent: It can be passed as a token to another application/event. This will give the permission for the other application to perform some predefined task.<br>
We are using pending intent to get triggered at specific time and using broadcast receiver we can execute recurring events.</p>


<h1>Coding Conventions</h1>
<p>Coding conventions will help in easing the understanding, readability and debugging of code. For having consistent data everyone should adhere to same coding conventions. We have followed Google Java style guide in our project.</p>

<h2>File names</h2>
<p><b>Class file names:</b> Follows UpperCamelCase and with extension “.java”. Example: CashRecord.java<br>
<b>Resource file names:</b> Written in lower case seperated by underscore. Example: activity_main.xml </p>
<br>
<h2>Source code</h2>
<p><b>Indentations:</b> Whenever new block of code is opened, a proper indentation is provided and when the block ends, the indent returns to the previous indent level. This is applied for both code and comments throughout the block.</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.IndentationFinal.PNG?raw=true" width="750" height="325">
<br>
<p><b>Comments:</b> <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a. Description of each class is written using java comments<br>     
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b. Description of methods along with their parameters written using Javadoc<br>        
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c. Inline comments to explain the code logic if necessary<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; d. In case partial code is getting merged, comment needs to have ‘TODO:’</p>
<p>Class comments snapshot: </p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.DescriptionClassFinal.PNG?raw=true" width="750" height="125">
<p>Method comments snapshot: </p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.DescriptionMethodFinal.PNG?raw=true" width="750" height="200">
<p>Partial comments snapshot: </p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.partialcodeFinal.PNG?raw=true" width="750" height="125">
<br>
<h2>Naming conventions:</h2>
<p><b>Variables:</b> All variable names are descriptive and written in lowerCamelCase<br>
<b>Methods:</b> All method names are written in lowerCamelCase<br>
<b>Constants:</b> All constants are written in UPPER_CASE </p>
<br>       
<h2>Packaging</h2> 
<p>Similar classes will be grouped and will be packaged in one folder.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a.	All model classes are in one package<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b.	All adapter classes are in one package<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c.	All database classes are in one package<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; d.	All other classes will be kept in main package</p>
<p>Packaging screenshot:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.packaging.PNG?raw=true" width="250" height="250">
<br>
<h2>Annotations</h2> 
<p>Annotations applicable to a method or constructor appears right above the its declaration, and each annotation is listed on a line of its own. Example: A method is marked with the @Override annotation whenever a class method overriding a superclass method or class method implementing an interface method.</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.annotationFinal.PNG?raw=true" width="750" height="150">
<br>

<h2>Generalisation</h2>
<p><b>Strings:</b> All strings should be stored in 'strings.xml' resource file and need to be referenced from there itself. Hard coding of strings needs to be avaoided. This will help when the application is globalised and multiple languages are supported. <br>Example: R.string.euro, @string/app_name<br>
<b>Colors:</b> All colors need to be stored in 'colors.xml' resource file and need to be referenced from there. <br>Example: @color/black<br>
<b>Dimensions:</b> All dimensions used in project need to be saved in 'dimens.xml' resource file and need to be referenced from there. Example: @dimen/unit1</p>
<br>

<h2>File ownership</h2>
<p>File ownership for each file is provided using Javadoc comments. This will be useful to identify the developer, when we see any bugs or customer comes with an escalation.</p><br>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.OwnerShipFinal.PNG?raw=true" width="750" height="125">
<br>
<p>Initially everyone is briefed on what coding conventions that will be followed in our project and also after each developer raises a merge request, the reviewers will check the changes thoroughly and give necessary comments and actions to be taken care of before merging. Once the comments are taken care then the code will be merged into main branch. By this way we make sure that everyone follows the necessary coding conventions.</p>

 <img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/Advanced_Prototype1.png?raw=true" width="800" height="270"><br><br>
 
 <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/AdvancedPrototype2.png?raw=true" width="800" height="350">
<hr><br>

<h1>Context of use</h1>
<p>It is important to identify the target users for developing a relevant and useful application. It helps us in identifying what key features and functionalities need to be included in our app. Following are the two personas we have identified for our App who represents majority of our user group, by answering the following questions. <br><br>
1. What is the education qualification of the user?<br>
2. What is the professional background of the user?<br>
3. What are the intentions of user to use our App?<br>
4. How will the user use our App?<br>
5. What does the user expect from the App?</p>
<br>
<p><b>Persona Student: John Snow</b></p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.persona1.PNG?raw=true" width="550" height="600"><br><br>
<p><b>Persona Professionals: Penny Hofstetter</b></p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.persona2.PNG?raw=true" width="550" height="600"><br>
<hr>
<h1>Design solutions</h1>
<p>Find the design solutions provided for two user stories with sequence of screens</p>
<p><b>Design solution 1:</b> User should be able to add new cash record. Click on add new cash record. Then add the transaction details and click on save button. User should be able to see new cash record displayed in home screen.</p>
<div class="imageClass">	
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.1.MainPage.jpeg?raw=true" width="250" height="480"> 
<div class="imageClass">
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.2.DetailActivity.jpeg?raw=true" width="250" height="480">
	<div class="imageClass">
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.4.Added.jpeg?raw=true" width="250" height="480">
</div>
	</div>
</div>

<p><b>Design solution 2:</b> User should be able to configure app from Settings screen. First go to settings screen. Click on Enable Pin. Then set the desired pin in Set pin dialog. User should get "Your PIN has been set" message.</p>
<div class="imageClass">
	<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.5.SettingsScreen.jpeg?raw=true" width="250" height="480">
	<div class="imageClass">
		<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.6.SetPIN.jpeg?raw=true" width="250" height="480">
		<div class="imageClass">
			<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.7.PINsetsuccesful.jpeg?raw=true" width="250" height="480">
		</div>
	</div>
	</div>
	
<br>
<hr>
<h1>Design principles Implemented</h1>
<p>We followed Android design principles developed by Android User Experience team whose main objective is to keep user’s best interests in mind. We implemented which ever design principles applicable to our project.</p>
<p>Its not just about creating an application that works and serves the purpose but also, we must consider keeping user happy and satisfied with the application.</p>
<br>
<p><b>Keeping it brief:</b> Always keeping the messages and pop ups simple and short. User tends to skip reading long phrases.<br>
Tool tips: Tool tips are added where the user might unknowingly submit wrong data. Like, when user tries to save a transaction without inputting an amount, then the tool tip “amount field cannot be empty” will appear on screen.<br>
Toast messages: Necessary Toast messages are provided for execution of certain actions. This will help the user understand the cause of any failures.</p>
<div class="imageClass">
	<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.8.ToolTip.jpeg?raw=true" width="250" height="480">
	<div class="imageClass">
		<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.9.Wrong password.jpeg?raw=true" width="250" height="480">
	</div>
	</div>
<br>
<p><b>Only show what user need and when he needs it:</b> User will be overwhelmed if he sees too much of options at the first screen. So, break the tasks and hide the unnecessary options. Only show options which are really needed and slowly teach the user about advanced tasks as he progresses.</p>
<div class="imageClass">
	<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.2.DetailActivity.jpeg?raw=true" width="250" height="480">
	<div class="imageClass">
		<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.15Recurringactivity.jpeg?raw=true" width="250" height="480">
	</div>
	</div>
<br>
<p><b>Never lose any customization data created by user:</b> Always save the data what user took time to create. Always remember the settings like the login pin, currency selection set by user and personal touches like customised categories created by user.</p>
<br>
<p><b>Do the heavy lifting for users:</b> Let the user pick his input from dropdown list or by providing time & date pickers wherever necessary. Constrain to numerical pads instead of qwerty pad, where only number input are expected. This way we can also restrict any random inputs from user, wherever we need a specific format of inputs from user and user can successfully complete the task with minimal efforts.</p>
<div class="imageClass">
	<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.10.TimePicker.jpeg?raw=true" width="250" height="480">
	<div class="imageClass">
		<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.3.Datepicker.jpeg?raw=true" width="250" height="480">
		<div class="imageClass">
		<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.13.Category.jpeg?raw=true" width="250" height="480">
		</div>
	</div>
	</div>
<br>
<p><b>User Control and Freedom:</b> This application is provided with Navigation bar using which user can easily launch any activity. All dialogs have a cancel option where he can cancel if he doesn’t want to continue with the operation.</p>
<div class="imageClass">
	<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.11.NavigationBar.jpeg?raw=true" width="250" height="480">
	<div class="imageClass">
		<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.12CancelOption.jpeg?raw=true" width="250" height="480">
	</div>
	</div>
<hr><br>

<h1>Summary of changes</h1>
<p>In comparison to basic prototype there have been few changes and additions in the design.</p> 
<h2>Changes: </h2>
<p>1. Now the user can enable or disable the pin login option from settings screen. <br> 
2. Change pin option have been shifted to settings screen. <br>
3. The option for selecting the currency have been added in the settings screen. <br>
4. User can be able to set transaction as recurring in detail input screen. <br>
5. User can view history of transactions in history page and filter based on Category, Payment type, Date range, Amount range.  </p>

<h2>Additions</h2>
<p>Newly added essential requirements:<br>
1. Categories should be shown with name and icon<br>
2. User should be able to add new categories<br>
3. User should be able to delete existing records<br>
4. Two charts are required for visualization of data<br>
5. Monthly summary need to be exported as PDF</p>

<p>Because of these added features we have changes in our user stories and Class diagram</p>
<p>Changes in User stories: There have been addition of few stories in user stories due to added requirements</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.UserStoriesChanged.png?raw=true" width="550" height="450">
<p>Changes in class Diagram: There have been some modifications and additions of classes<br>
1. Added long threshold in category class for setting threshold.<br>
2. Added new class for category database<br>
3. Added new mothods exportDbpdf() and getPDF() in MoneyControllerManager</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.NewClassDiagram.jpeg?raw=true" width="900" height="650"><br><hr>
<h1>App screenshots</h1>
<p>Most of the actual app screen shots are already added above. Please find some extra app screenshots.</p>
<p>First image shows history screen where user can see history of transactioins<br>In second screen user can visualise his transactions in the form of PIE graph.</p>
<div class="imageClass">
	<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.14.History.jpeg?raw=true" width="250" height="480">
	<div class="imageClass">
		<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.PIEChart.jpeg?raw=true" width="250" height="480">
	</div>
	</div><hr><br>
<h1>Working Prototype</h1>
  <p> Please find the downloadable application link here: <a href="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/releases/download/release/app-debug.apk">Downlaod</a></p>
<br><hr>
<h3><i><strong>Thank You for visiting our blog!! That’s all for Now. Stay tuned for more updates on our project!!</strong></i></h3>
