---
layout: post
title: "Reflection"
date: 2019-07-02
---

<html>
<head>
<style>
* {
  box-sizing: border-box;
}

.column{
  float: left;
  width: 50%;
  padding: 0px;
}

/* Clearfix (clear floats) */
.row::after {
  display: table;
}
</style>
</head>
    <body style="background-color:powderblue;">
                
<h1>Overview</h1>
<p> Hello Folks,welcome to our last blog of the series for ‘YourEuro’ - A money control app. In this blog we discuss about the reflections of our whole experience as working in team and difficulties encountered while developing the app.</p><hr>

<h1>Technical challenges our team encountered</h1><br>
<p>
</p> 
    <ul>
        <li><p>Three of our team members were not well aware of the android platform and MVC architecture, learning and understanding the concepts took time initially. Hence development burden was on single person.</p></li>
        <li><p>Due to our misconception in requirements, made us to rebuild few of the functionalities.</p></li>
        <li><p>Creating blog and adding libraries through Github was challenging.</p></li>
        <li><p>Debugging the errors took more time than expected.</p></li>
        <li><p>Faced difficulties while working and merging the code parallelly in Git. </p></li>
    </ul>
</p><br>

<h1>Benefits the team experienced by working on a software project as a team</h1><br>
<p>
</p> 
    <ul>
        <li><p>Knowledge sharing is major take away from the project.</p></li>
        <li><p>Dividing the task made work easier.</p></li>
        <li><p>Driving each other to work, made deadlines less scary.</p></li>
        <li><p>Learning was fun when you learn from friends.</p></li>
        <li><p>Approaching the problem with different perspective lead us to a better solution. </p></li>
        <li><p>Working in a team developed not technical skills but also improved management and communication competence.</p></li>
    </ul>
</p><br>

<h1>Challenges the team experienced by working on a software project as a team</h1><br>
<p>Due to the nature of working in teams, group members can sometimes find that they are not working effectively, which negatively impacts on their progress, and their ability to be successful.  Some common problems identified by individuals working in teams are:</p> 
    <ul>
        <li><p>Tasks were not being completed on time which led to Doppler effect and too much work for single person</p></li>
        <li><p>Communication is not always easy, that's how humans are!</p></li>
        <li><p>Collabaration of team member for perticular task on common time was challenging</p></li>
        <li><p>Learning is fun, but its a long process.</p></li>
    </ul>
</p><br>


<h1>How the course did meet your needs or fall short?</h1><br>
<p>
</p> 
    <ul>
        <li><p>This course helped us to know more about the Android App development, agile methodology and team work</p></li>
        <li><p>We got the chance to work and gain skills in java,android studio, html and learnt more about Github. </p></li>
        <li><p>Learning how an entire software engineering project in agile works, from requirements analysis, designing, development  until the testing of the application.</p></li>
        <li><p>This project gave an abstarct idea of work culture in corparate sector.</p></li>
    </ul>
</p><br>







<p>Some of these test methods can be further classified into:<br>
    <ul>
        <li><p><b>White-Box Testing:</b> A software testing process where the tester will have knowledge of internal   structure/ design/ implementation of the produce under consideration.</p></li>
        <li><p><b>Black-Box Testing:</b> A software testing process where the tester will NOT have knowledge of internal structure/ design/ implementation of the produce under consideration </p></li>
    </ul>
 </p><br>

<p><b>Unit Testing:</b>  Unit testing is the foundation of testing process. This can be executed in parallel while the feature is implemented by the developer. And since the developer is expected to know A-Z of what he has written in the source code, white-box testing was followed. Android supports many libraries that support unit testing. In YourEuro app we have used JUnit and Mockito.<br>
<i>Example:</i> A simple model class like the CashRecord has multiple fields to be set before saving it to database. Using Mockito the values in constructor is called and JUnit helps to assert the set values.
<br><br>

<b>Integration Testing:</b>  In this phase we test combination of basic unit of the app. In the previous blog we discussed about MVC pattern and how our MoneyControlManager(which is a controller) acts as a mediator between models and Views. Testing the controller would cover more than 50% of our integration testing. We used Robolectric framework along with Mockito to execute the same. Robolectric helps us mock the methods of dependency classes and thus integration of several components in the controller can be tested. Apart from this white box testing, one of our team members who didn’t participate in the code review would do a blackbox testing.<br>
<i>Example:</i> Validating category threshold with the statistics charts.
<br><br>

<b>Functional Testing:</b>  The product development process followed our user stories and functional test cases were derived from the same user stories. While one of our developers worked on implementing the features, another team member would design the test cases following black box testing.<br>
<i>Example:</i> As a user I want to set my transactions as recurring.
<br><br>

<b>System Testing:</b>  The product is tested in different environments and devices.<br>
<i>Example:</i> UI testing was done on devices of different screen size and different android version using emulators.
<br><br>

<b>Acceptance Testing:</b>  Prototype was created for each user story/feature and demonstrated to the client in weekly meetings. Starting from UI to different features, client feedback was taken as acceptance. Any changes from the developer was immediately notified to the client and any requests from the client was taken into consideration and implemented in the following development sprint planning.<br>
 <i>Example:</i> Filters for history was created with single category, while the customer feedback was to have multiple selection of categories.
<br><br>

To summarise testing phases:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_testing_phases.png?raw=true" width="675" height="300" class="center">

<p>All these testing needs to be documented so that we avoid duplicate efforts and at the same time with newer versions the same test cases can be executed again to make sure the features are still functioning as desired. Since we did not have any access to a test management tool, we used whatsapp to share the excel files in which test cases were written module-wise. Moving further we will share all the files in GIT so that the test cases are visible globally. Trello is used to log a bug found during any phase of testing by the tester in black-box testing. Each bug is given priority level depending on the severity of the issue.</p> 
    <ul>
        <li><p><b><i>P0</i></b> are blockers or crashes which under no circumstances be present in release version.</p></li>
        <li><p><b><i>P1</i></b> are slightly lower issues, which also needs to be resolved before releasing the product</p></li>
        <li><p><b><i>P2</i></b> are least priority bugs, over which features can take precedence.</p></li>
    </ul>
<p>But in the end, everyone would expect a bug-free flawless app and all the bugs in resolved status.</p>

<figure>
     <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_Trello_bugs.png?raw=true" style="width:100%">
      <figcaption>Fig: Logged bugs in Trello</figcaption>
</figure>

<hr>
<h1>Unit testing in Android Studio</h1><br>
<p>Android studio shows the coverage of unit test line-by-line. The red colour on the margins tells us that the corresponding line number is not covered by unit testing. After covering the line the coverage turns green as in the next image. We try to cover 70% of the class under testing so that we don’t overfit the test cases. Under packages we even get class coverage once we run the entire unit test cases.</p>

<div class="row">
    <div class="column">
             <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_UntestedCategory.PNG?raw=true" style="width:100%">
    </div>
    <div class="column" width="20">
    </div>
    <div class="column">
            <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_testedCategory.PNG?raw=true" style="width:100%">
    </div>
</div>

<figure>
    <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_modelClassesTested.PNG?raw=true" width="400" height="100" class="center">
     <figcaption class="center" >Fig: Class and line coverage of model package</figcaption>
</figure><br>
                                                                                                                               <hr>        
<h1>White box testing </h1>
<p>White box testing is testing process where the tester knows the internal structure of the code. The test coverage can be done by statement-by-statement or branch-by-branch basis. The following are the 5 scenarios which was tested using white-box testing.</p>

<h2>1. PinActivity: Validation of user credential</h2>
<p>PinActivity is the screen where the user enters the security pin to enter the app. We have tested 3 scenarios with empty pin, correct pin and invalid pin. We have restricted the input to be number and provided only number pad so that user doesn't enter any random strings.</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_wbt_1.png?raw=true" style="width:100%">

<h2>2. DetailsInputActivity: Field inputs</h2>
<p>This is the core page of our app where user enters the transaction details. Since we do not have edit screen as of now, we want to make sure user enters all the mandatory fields before saving it. In case any field is untouched, then we throw an error and indicate the user to fill it. We have five mandatory fields and for simplicity sake we have tested 2 scenarios where all the details are filled in one case and some of the fields are not changed in the other case. </p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_wbt_2.jpeg?raw=true" style="width:100%">

<h2>3. CategoryActivity: New custom category name</h2>
<p>This is a unique scenario where the 'Save' button is enabled or disabled dynamically with respect to the custom category name. The save button is enabled only when the text field is not empty and an icon is selected. Thus we have 2 test scenarios for the same.</p> 
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_wbt_3.jpeg?raw=true" style="width:100%">

<h2>4. Security Settings: Setting new PIN</h2>
<p>In this test case we have considered a parallel event. While setting a new pin, the user can either select 'SET' or 'CANCEL' button. Hence it is the parallel representation. We have executed 3 test cases for this feature. For empty pin, for any pin and for cancel selection.</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_wbt_4.jpeg?raw=true" style="width:100%">

<h2>5. Statistics Filter: Changing filter fo charts</h2>
<p>Even though this part of the code has a nested IF statements, we considered only the one line in the deepest part that decides which filter to modify. Since we have 2 charts we tested it with 2 test cases.</p>
<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_wbt_5.jpeg?raw=true" style="width:100%">


<hr>
<h1>Black box testing</h1>
<p>Black box testing is a testing process where the tester is not familiar with the internal structure or flow of the source code. He will just be familiar with the input and expected output and using them will create various test cases covering simple and edge cases.As explained earlier one of our team members will be a tester for a particular feature. But along with that person, we even asked some of our friends to use the app and give us feedback on the same. We concentrated on the below 5 features which covers different aspects of our product.</p>

<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_black_box_testing.png?raw=true"  class="center" style="width:100%"><br>
 <ul>
        <li><p>Scenarios 1, 2 and 4 describes equivalence class where the Data input 1 column are valid inputs  and Data input 2 column are invalid input(example: empty input)</p></li>
        <li><p>Scenario 5 is Boundary value tests, where adding a range which is not present will give empty list to view.</p></li>
    </ul>
<hr>
<h1>Summary of changes</h1>

<h2>New features introduced after Advanced prototype:</h2>
<ul>
     <li><p>Threshold set for categories</p></li>
     <li><p>Export transaction summary pdf via e-mail</p></li>
      <li><p>Recurring transactions</p></li>
</ul>
    
<h2>Modifications after Advanced prototype:</h2>
<ul>
        <li><p>Category thresholds are represented as line graph over existing bar graph</p></li>
        <li><p>The axis values for charts changes depending on filter selection</p></li>
    </ul>
                
<br><hr>
<h3><i><strong>Thank You for visiting our blog!! That’s all for Now. Stay tuned for our final blog where we reflect on our journey in creating a dream app!!!</strong></i></h3>
