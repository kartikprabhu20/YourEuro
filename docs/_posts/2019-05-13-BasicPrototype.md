---
layout: post
title: "Basic Prototype"
date: 2019-05-13
---
<html>
<body style="background-color:powderblue;">
<h1>Project Basic Prototype</h1><br>
  
 <hr><br>
 
 <h1>Overview</h1><br>
 <p>Hello, welcome to the second blog of our project. In the previous blog, we introduced our team and which android app we will proceed with. In this blog, we will discuss about the customer requirements and user stories of our app.</p>
 <br><hr><br>

<h1>Requirements</h1>

<p>Requirement gathering is a first and essential step for developing an android app. We gathered requirements from client over series of meetings and we have created a questionnaire for customer to fill it as per their requirement. The gathered requirements have been analysed and categorized as Essential, Necessary and Desirable Requirements</p>
<h2>1. Essential Requirements:</h2>
	
<p>• Details/Parameters of expense/income? (Category, note, date, place, camera for receipt)<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; a. Customisable category, deleting option<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; b. Payment method of income and expenditure<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c. Free text note<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; d. Date<br>
  • The application should work offline<br>
		• Application should store the data locally, one user is fine<br>
		• Security feature(Pin login for the app)<br>
		• Statistics<br></p> 	
<h2>2. Necessary Requirements:</h2>
 <p>	• Security question to retrieve forgotten password. Take the answer for security question while setting the pin<br>
		• The data needs to be filtered based on time, type of payment, category, range of money<br>
		• Currency change support<br></p> 
<h2>3. Desirable Requirements:</h2>
 <p>	• Sharing expense with other users<br>
		• Summarise monthly expenses in mail<br>
		• Store data on server/firebase<br>
		• Sync at particular time with firebase server<br></p> 
<hr>
<h1>Wrong Assumptions</h1>
<p>By exploring various money control apps, we have presumed certain features need to be added in the app but after meeting client, we got clarification what to include and what to exclude. Some of our wrong assumptions are:<br>
		• Our assumption was to include multiple bank accounts in the app but the requirement is to monitor transactions of only one account<br>
		• Barcode scanning is not required<br>
		• We assumed that Login authentication should be given using google, fb or email whereas the client requirement is pin authentication<br>
		• We got to know filtering the expenses based on Payment type, Range of money and category is required along with just date</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/Wrongassumption.jpg?raw=true" width="250" height="480"> 
<div class="imageClass">
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/corrected.jpg?raw=true" width="250" height="480" >
</div>
<br><hr><br>

<h1>User Stories</h1>
<p> Our project is divided in to four Epics, each Epic consists of Stories and each story will be further divided as Tasks. The same Tasks have been carried to our Scrum board. User stories diagram of our project is as shown below: </p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/UserStoriesFinal.png?raw=true" width="1000" height="800">
<hr><br>
<h1>Estimated Time</h1>
<p>Time management is one of the challenges that we are facing. So, we have assigned estimated time for each task and estimated number of sprints for user stories. 
We have divided our User stories into four Epics (milestones or stages). Each Epic is categories in Stories and stories are further categorized as sub task. 
We have provided three sprints for each Epic, one and a half sprints for each story. For fast and smooth purpose of our work, we have divided our development teams into two groups, each working on one Epic. 
The time estimation of two other Epics will be re-calculated depending on first two Epics.</p>

<hr><br>

<h1>Use Case Diagram</h1>
<p>From Home screen user can navigate to four different activities:<br> 
•	Balance which shows you the remaining funds<br>
•	Recent shows you the list of records of all recent transactions including date, Payment type and category of purchased item<br>
•	Navigation bar which displays the activities such as Settings, About us, Statistics and History of all the previous transactions<br>
•	Settings where the user can Add or Delete categories and in Security settings the user can Enable or Disable Lock screen or change pin</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/useCaseDiagram.png?raw=true" width="1000" height="600">

<br><hr><br>
	
<h1>Class Diagram</h1>
<p>1. CashRecord is our core entity, it consists Balance, notes, Currency, Date, cashRecordType, paymentType and category. It has getter and setter methods for each attribute mentioned above.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;	•	Category is a custom class which contains categoryName and imageID. We provide set of predefined categories and if &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;customer wants new category other than listed, then there will  be an add option where he can add his own category. It has &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;setter and getter methods.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		•	All attributes defined in Constants will be static variables.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		•	cashRecordType contains Income and Expense<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		•	PaymentType contains Cash, DebitCard, CreditCard and BankAccount<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;		•	Currency type will give type of currency used for transaction.<br>
	2. MoneyControlManager will act as a controller for our MVC design pattern. It has all the functional requirements, it sets the views using CashRecordAdapter and it access the database and models using CashRecordDao.<br>
	3. CashRecord will save its objects in database using CashRecordDao.<br>
	4. CashRecordDao which has all the necessary data manipulation and data querying methods for database which are implemented using RoomDatabase.</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/ClassDiagram.png?raw=true" width="900" height="600">
<br><hr><br>

<h1>Activity Diagrams</h1>
<p>Following are the two activity diagrams for two of the features of our app. </p><br>
<h2>Activity diagram of Navigation Bar:</h2>
<p>Here, the user will start with Pin Login, if the pin is correct then the user will be directed to Home Screen else it will be direct back to Pin Login. Once the user clicks on Home Screen, he will get Navigation bar menu which will navigate to History menu, clicking on History button will direct to Filter menu. 
In which the user can do three kinds of filtering for the transactions based on Time, Category and RangeOfMoney.Once he is done with these options, he can stop</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/ActivityDiagram-2Final.jpeg?raw=true" width="1000" height="600"><br>
<h2>Activity diagram of Settings:</h2>
<p>Here, the user will start by clicking on Settings, then Security Settings. User will be given Security Settings menu in which the user can Change Pin or he can enable or disable EnableLockScreen. After doing the changes he can stop.</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/ActivityDiagram-1.jpeg?raw=true" width="1000" height="600">
<br><hr><br>

<h1>Development Strategy</h1>
<h2>Sprint Handling</h2>
<p>In scrum board all the tasks will be picked from user stories for each sprint and all the boards/tasks will be assigned a priority and a requirement label. Using Backlog board, we will be tracking all the pending tasks and bugs. For next sprint tasks will be taken from Backlog board based on priority</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/ScrumBoardTasksLabels.png?raw=true" width="800" height="600">
<p>Scrum Board</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/Backlog.png?raw=true" width="800" height="600" >
<p>Backlog Board</p>
<h2>GitHub Handling</h2>	
<p>We have created a developer_1 branch so that master will not get affected until & unless you pull the changes in to master. 
Again, each developer will create his own subbranch from developer_1 while working on the app and once he is finished, he will commit his changes to his working subbranch with proper comments. 
Then he will raise a pull request to developer_1. With this all the team members will be alerted, then the master will investigate the changes and check if it is affecting the existing code anyway. 
Then he will approve the pull request. In this way we can always assure the integrity of master and any release branches.</p><br>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/branching2.png?raw=true" width="1000" height="300">
<p>Active Branches</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/GitPullRequest.jpeg?raw=true" width="1000" height="300">
<p>Pull Request Page</p>
<hr><br>

<h1>App Mockups</h1>
<p> Home screen 1 & 2 has Balance, Recent and Statistics views, you will get Home screen 2 by scrolling down Home screen 1. By clicking on floating button in Home screen you will get the Details screen. Here the user can record his Transaction with respective details</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/homescreen.png?raw=true" width="250" height="480"> 
<div class="imageClass">
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/homescreen_2.png?raw=true" width="250" height="480" >
	<div class="imageClass">
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/details.png?raw=true" width="250" height="480"  >
</div>
  </div>
  <p>a) Home Screen 1 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;b) Home Screen 2&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; c)Detail Activity</p><br>
  <hr>
  <h1>Working Prototype</h1>
  <p> Please find the downloadable application link here: <a href="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/releases/download/release/app-debug.apk">Downlaod</a></p>
<br><hr>
<h3><i><strong>Thank You for visiting our blog!! That’s all for Now. Stay tuned for more updates on our project!!</strong></i></h3>





