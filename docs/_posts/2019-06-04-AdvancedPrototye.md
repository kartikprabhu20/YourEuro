---
layout: post
title: "ISEE 2019 -- Advanced Prototype"
date: 2019-06-04
---

<html>
        <body style="background-color:powderblue;">
                <h1>Overview</h1>
                <p> Hello, welcome to our third blog. In the previous blog we introduced about the requirements, class diagrams and development strategy. In this blog we discuss about our design pattern, Implementation and User interface design.</p><hr><br>
<h1>Design Pattern</h1><br>
<p>The objective of our App is to keep track of money transactions on a daily basis and also to give the summarized report.</p> 
                <p><b>MVC architecture:</b> It is very difficult to create your User interfaces, Control logic and Databases together. We chose to follow MVC architecture in the development of our App to overcome this issue. MVC is an acronym for Model-View-Control. By following this architecture, we can separate our Model (database), Views (Activities), and Controller (Control logic) and it allows each to change without affecting the others. </p>
  
<p> The CashRecord class is our core entity and every transaction is an object of this class. The created objects will be saved in the database. </p>

<p><b>Model:</b> CashRecordDatabase is our Model which extends RoomDatabase and will be used for interaction with the Database. </p>

<p><b>Controller:</b> MoneyControlManager is our main controller. Based on the request from the user it will perform operations on the model and modify the display accordingly.</p>

<p><b>View:</b> DetailInputActivity is our main view through which user gives the transaction details and it notifies MoneyControlManager when user presses save button. </p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/MVCarchitecture.jpeg?raw=true" width="700" height="300"><br>
<p><b>Builder pattern:</b> Alert dialogs are created using builder patterns. Using builder pattern multiple representations can be made using same construction method. You can include only the required fields while constructing a Aler dialog using builder pattern.</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.AlertDialog.PNG?raw=true" width="750" height="325">
<br>
<p><b>Adapter design pattern:</b> Adapter design pattern are used for setting recycler views. Adapter controls both Recycler view as well as database. Our CashRecordAdapter will be used to set data in recycler views in MainActivity and it will be used to set data in CashRecordDatabase. MoneyControlManager will have CashRecordAdapter instance (MVC pattern).</p>
<br>

<p><b>Singleton pattern:</b> Our controller follows singleton pattern, since it interacts with the database and we want this interaction to be synchronised. Meaning multiple objects of database should not interact with database simultaneously. </p>
<br>
<p><b>Observer design pattern:</b> Observer design patterns are used whenever an event should be performed automatically. We are using this pattern for recording recurring events.<br>
        <b>Broadcast receiver:</b> It can be used to register for a required event. All registered receivers will be notified by android runtime once the required event happens.<br>
        <b>Pending intent:</b> It can be passed as a token to another application/event. This will give the permission for the other application to perform some predefined task.<br>
We are using pending intent to set specific time and using broadcast receiver we can trigger recurring events.</p>


<h1>Coding Conventions</h1>
<p>Coding conventions will help in easing the understanding, readability and debugging of code. For having consistent data everyone should adhere to same coding conventions. We have followed Google Java Style Guide in our project.</p>

<h2>File names</h2>
<p><b>Class file names:</b> follows UpperCamelCase and with extension “.java” example: CashRecord.java<br>
<b>Resource file names:</b> written in lower case example: activity.main.xml </p>
<br>
<h2>Source code</h2>
<p><b>Indentations:</b> Whenever new block of code is opened, a proper indentation is provided and when the block ends, the indent returns to the previous indent level. This is applied for both code and comments throughout the block.</p>
<br>
<p><b>Comments:</b> <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a. Description of each class is written using java comments<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b. Description of methods along with their parameters written using Javadoc.<br>        
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c. In line comments to explain the code logic if necessary<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; d. In case partial code is getting merged comment needs to have ‘Todo’</p>
<br>
<p><b>Naming conventions:</b> <br>
<b>Variables:</b> All variable names are descriptive and written in lowerCamelCase<br>
<b>Methods:</b> All method names are written lowerCamelCase<br>
<b>Constants:</b> All constants are written in UPPER_CASE </p>
<br>
        
<h2>Packaging</h2> 
<p>Similar classes will be grouped and will be packaged in one folder.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a.	All model classes are in one package<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b.	All adapter classes are in one package<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c.	All database classes are in one package<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; d.	All other classes will be kept in main package</p>
<br>

<h2>Annotations</h2> 
<p>Annotation applying to a method or constructor appear right above the method declaration, and each annotation is listed on a line of its own. Example: A method is marked with the @Override annotation whenever a class method overriding a superclass method or class method implementing an interface method.</p>
<br>

<h2>Generalisation</h2>
<p><b>Strings:</b> All strings should be stored in strings.xml resource file and need to be referenced from there. <br>example: R.string.euro, @string/app_name<br>
<b>Colors:</b> All colors need to be stored in colors.xml resource file and need to be referenced from there. <br>Example: @color/black<br>
<b>Dimensions:</b> All dimensions used in project need to be saved in dimens.xml resource file and need to be referenced from there. <br>Example: @dimen/unit1</p>
<br>

<h2>File ownership</h2>
<p>file ownership for each file is provided using Javadoc comments</p><br>
<p>Sample screenshots</p>
<p>Indentation and Inline comments:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.Indentation.PNG?raw=true" width="650" height="400"><br>
<p>Class comments:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.Descriptionclass.PNG?raw=true" width="400" height="160"><br>
<p>Method comments:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.Descriptionmethod.PNG?raw=true" width="650" height="250"><br>
<p>Partial Code:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.partialcode.PNG?raw=true" width="650" height="130">
<p>Packaging:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.packaging.PNG?raw=true" width="250" height="250">
<p>Annotation:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.annotation.PNG?raw=true" width="350" height="150">
<p>Ownership:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.OwnerShip.PNG?raw=true" width="350" height="100">
<br>
<p>Initially everyone is briefed up on what coding conventions that will be followed in our project and also after each developer raises a merge request, the reviewers will check the changes thoroughly and give necessary comments and actions to be taken care of before merging. Once the comments are taken care then the code will be merged into main branch. By this way we make sure that everyone follows the necessary coding conventions.</p>

 <img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/Advanced_Prototype1.png?raw=true" width="800" height="250"><br><br>
 
 <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/AdvancedPrototype2.png?raw=true" width="800" height="300">
<hr><br>

<h1>Context of use</h1>
<p>It is important to identify the target users for developing a relevant and useful application. It helps us in identifying what key features and functionalities need to be included in our app. Following are the two personas we have identified for our App who represents majority of our user group, by answering the following questions. <br><br>
1. What is the highest level of education this person has received?<br>
2. What is the professional background of the user?<br>
3. What is the objective of user to use our App?<br>
4. When and where will the user use our App?<br>
5. What are user’s expectations from the App?</p>
<br>
<p><b>Persona Student: John Snow</b></p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.persona1.PNG?raw=true" width="600" height="600"><br><br>
<p><b>Persona Professionals: Penny Hofstetter</b></p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/3.persona2.PNG?raw=true" width="600" height="600"><br>
<hr>

<h1>Design principles Implemented</h1>
<p>We followed Android design principles developed by Android User Experience team whose main objective is to keep user’s best interests in mind. We implemented which ever design principles applicable to our project.</p>
<p>Its not just about creating an application that works and serves the purpose but also, we must consider keeping user happy and satisfied with the application.</p>
<br>
<p><b>Keeping it brief:</b> Always keeping the messages and pop ups simple and short. User tends to skip reading long phrases.<br>
Tool tips: Tool tips are added where the user might unknowingly submit wrong data. Like when user tried to create record without amount then the tool tip “amount field cannot be empty” will appear on screen.<br>
Toast messages: Necessary Toast messages are provided for execution of certain actions. This will help the user understand the cause of failure.</p>
<br>
<p><b>Only show what user need and when he needs it:</b> User will be overwhelmed if he sees too much of options at the first screen. So, break the tasks and hide the unnecessary options. Only show options which are really needed and slowly teach the user about advanced tasks as he progresses.</p>
<br>
<p><b>Never loose any customization data created by user:</b> Always save the what user took time to create. Always remember the settings like the login pin, currency selection set by user and personal touches like customised categories created by user.</p>
<br>
<p><b>Do the heavy lifting for users:</b> Let the user pick his input from dropdown list or by providing time & date pickers wherever necessary. This way we can also restrict any random inputs from user, wherever we need a specific format of inputs from user and user can successfully complete the task with minimal efforts.</p>
<br>
<p><b>User Control and Freedom:</b> This application is provided with Navigation bar using which user can easily launch any activity.  All dialogs have a cancel option where he can cancel if he doesn’t want to continue with the operation.</p>
<hr><br>

<h1>Summary of changes</h1>
<p>In comparison to basic prototype there have been few changes and additions in the design. 
Changes: 
Now the user can enable or disable the pin login option from settings window and change pin option have been shifted to settings screen. <p>The option for selecting the currency have been added in the settings screen.  </p>
<h1>Additions</h1>
<p>Categories should be shown with both icon and name. User should be able create new categories</p>
//TODO:Show class diagram change for cashrecord<br>
//Changes in database, created a separate db for category.<br>
//While adding custom category, suggest a predefined list of different icons for the new category.(can be user story)<br>
User should be able to delete an existing record. <br><br>
//TODO: screenshots of detail activity before and after delete option

User should be able to set some threshold/limit for amount of money to be spent on each category. Beyond the threshold every transaction of that record should be shown in Red colour. It should also be reflected in graph.<br>
We need at least two charts for visualising the transactions<br>
//The layouts were changed to handle multiple charts/graphs<br>
Monthly summary need to be exported as PDF and should be sent in E-mail.<br>
//The MoneycontrolManager was added with another method to convert the database into pdf.<br>
monyeControllManager.getPdf();<br></p>
