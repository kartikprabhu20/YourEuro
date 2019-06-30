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
<p> Hello Folks, Welcome to our last blog of the series for ‘YourEuro’ - A money control app. In this blog we discuss about the reflections of our whole experience as working in team and difficulties encountered while developing the app.</p><hr>

<h1>Technical challenges our team encountered</h1><br>
<p>Technical challenges, is less a solution to all of problems than a puzzle that needs to be constantly assessed and configured to make sure everything is going in a pace. by constantly getting called upon to adapt learnings and break down new barriers.
</p> 
    <ul>
        <li><p>Three of our team members were not well aware of the android platform and MVC architecture, learning and understanding the concepts took time initially. Hence development burden was on single person.</p></li>
        <li><p>Due to our misconception in requirements, made us to rebuild few of the functionalities.</p></li>
        <li><p>Creating blog and adding libraries through Github was challenging.</p></li>
        <li><p>Debugging the errors took more time than expected.</p></li>
        <li><p>Faced difficulties while working and merging the code parallelly in Git. </p></li>
    </ul>
<br>

<h1>Benefits the team experienced by working on a software project as a team</h1><br>
    <ul>
        <li><p>Knowledge sharing is major take away from the project.</p></li>
        <li><p>Dividing the task made work easier.</p></li>
        <li><p>Driving each other to work, made deadlines less scary.</p></li>
        <li><p>Learning was fun when you learn from friends.</p></li>
        <li><p>Approaching the problem with different perspective lead us to a better solution. </p></li>
        <li><p>Working in a team developed not technical skills but also improved management and communication competence.</p></li>
    </ul>
<br>

<h1>Challenges the team experienced by working on a software project as a team</h1><br>
<p>Due to the nature of working in teams, group members can sometimes find that they are not working effectively, which negatively impacts on their progress, and their ability to be successful.  Some common problems identified by individuals working in teams are:</p> 
    <ul>
        <li><p>Tasks were not being completed on time which led to Doppler effect and too much work for single person</p></li>
        <li><p>Communication is not always easy, that's how humans are!</p></li>
        <li><p>Collabaration of team member for perticular task on common time was challenging</p></li>
        <li><p>Learning is fun, but its a long process.</p></li>
    </ul>
<br>


<h1>How the course did meet your needs or fall short?</h1><br>
   <ul>
        <li><p>This course helped us to know more about the Android App development, agile methodology and team work</p></li>
        <li><p>We got the chance to work and gain skills in java,android studio, html and learnt more about Github. </p></li>
        <li><p>Learning how an entire software engineering project in agile works, from requirements analysis, designing, development  until the testing of the application.</p></li>
        <li><p>This project gave an abstarct idea of work culture in corparate sector.</p></li>
    </ul>
    <p>Our team 'ARTExceptionals' would like to thank Dr. Schulze for granting us the oppurtunity to develop our skills in a very practical approach.We would also like to thank Dean D'souza for bringing the best out the best out of us by his regular inputs and feedbacks.</p> 
<p> 
To know more about our app and to download it, please visit our next blog!!
</p> 
<br>

<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_testing_phases.png?raw=true" width="675" height="300" class="center">

<figure>
     <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_Trello_bugs.png?raw=true" style="width:100%">
      <figcaption>Fig: Logged bugs in Trello</figcaption>
</figure>







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






