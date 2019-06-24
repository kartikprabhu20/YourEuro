---
layout: post
title: "Beta Prototype"
date: 2019-06-25
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
.column2 {
  float: left;
  width: 20%;
  padding: 0px;
}

.column3 {
  float: left;
  width: 80%;
  padding: 0px;
}

.column4 {
  float: left;
  width: 90%;
  padding: 0px;
}
.column5 {
  float: left;
  width: 10%;
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
<p> <i>“It’s time for some testing!”</i><br>
Hello and welcome to our penultimate blog for the series of ‘YourEuro’ - A money control app. In this blog we discuss in detail the process of testing which we followed to make our app flawless and make it Playstore ready.</p><hr>

<h1>Test Case Design</h1><br>
<p>Testing is a default part of software development package. It helps in enhancing the quality of the product, make it more reliable, give an effective performances and flawless experience. In the end these measures are required to stay in business.<br><br>  
In a team of 4, one of the members will be the developer for a feature while two other will be the code reviewer. The fourth person will not participate in the code review and thereby be ignorant of the structure of the source code. He will act as a tester and write test cases for blackbox testing. And the same is repeated with different developer and tester for different features.
</p> 

<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_tester.png?raw=true" width="350" height="250" class="center"><br>

<p>We followed standard industrial norms of testing a product, and following were the different types of testing we executed.
    <ul>
        <li><p>Unit Testing</p></li>
        <li><p>Integration Testing</p></li>
        <li><p>Functional Testing</p></li>
        <li><p>System Testing</p></li>
        <li><p>Acceptance Testing</p></li>
    </ul>
</p><br>

<p>Some of these test methods can be further classified into:<br>
    <ul>
        <li><p><b>White-Box Testing:</b> A software testing process where the tester will have knowledge of internal   structure/ design/ implementation of the produce under consideration.</p></li>
        <li><p><b>Black-Box Testing:</b> A software testing process where the tester will NOT have knowledge of internal structure/ design/ implementation of the produce under consideration </p></li>
    </ul>
 </p><br>

<p><b>Unit Testing:</b>  Unit testing is the foundation of testing process. This can be executed in parallel while the feature is implemented by the developer. And since the developer is expected to know A-Z of what he has written in the source code, white-box testing was followed. Android supports many libraries that support unit testing. In YourEuro app we have used JUnit and Mockito.<br>
<i>Example: A simple model class like the CashRecord has multiple fields to be set before saving it to database. Using Mockito the values in constructor is called and JUnit helps to assert the set values.</i>
<br><br>

<b>Integration Testing:</b>  In this phase we test combination of basic unit of the app. In the previous blog we discussed about MVC pattern and how our MoneyControlManager(which is a controller) acts as a mediator between models and Views. Testing the controller would cover more than 50% of our integration testing. We used Robolectric framework along with Mockito to execute the same. Robolectric helps us mock the methods of dependency classes and thus integration of several components in the controller can be tested. Apart from this white box testing, one of our team members who didn’t participate in the code review would do a blackbox testing.<br>
<i>Example: Validating category threshold with the statistics charts.</i>
<br><br>

<b>Functional Testing:</b>  The product development process followed our user stories and functional test cases were derived from the same user stories. While one of our developers worked on implementing the features, another team member would design the test cases following black box testing.<br>
<i>Example: As a user I want to set my transactions as recurring.</i>
<br><br>

<b>System Testing:</b>  The product is tested in different environments and devices.<br>
<i>Example: UI testing was done on devices of different screen size and different android version using emulators.</i>
<br><br>

<b>Acceptance Testing:</b>  Prototype was created for each user story/feature and demonstrated to the client in weekly meetings. Starting from UI to different features, client feedback was taken as acceptance. Any changes from the developer was immediately notified to the client and any requests from the client was taken into consideration and implemented in the following development sprint planning.<br>
 <i>Example: Filters for history was created with single category, while the customer feedback was to have multiple selection of categories.</i>
<br><br>

To summarise testing phases:</p>
<img src=" https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_testing_phases.png?raw=true" width="675" height="300" class="center">

<p>Trello is used to log a bug found during any phase of testing by the tester in black-box testing. Each bug is given priority level depending on the severity of the issue.</p> 
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

<h3>1. PinActivity: Validation of user credential</h3>
<div class="row">
    <div class="column3">
      <div class="row">
        <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_whitebox_testing_1.png?raw=true" style="width:100%">
      </div>
      <div class="row">
        <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_wbt_test_1.png?raw=true" style="width:100%">
      </div>
    </div>
    <div class="column2">
        <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_line_flow_1.png?raw=true" style="width:100%">
    </div>
</div>


<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_wbt_1.png?raw=true" style="width:100%">

<h3>2. DetailsInputActivity: Field inputs</h3>
<div class="row">
    <div class="column3">
         <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_whitebox_testing_2.png?raw=true">
    </div>
    <div class="column2">
        <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_line_flow_2.png?raw=true">
    </div>
</div>

<h3>3. CategoryActivity: New custom category name</h3>
<div class="row">
    <div class="column3">
         <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_whitebox_testing3.png?raw=true">
    </div>
    <div class="column2">
         <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_line_flow_3.png?raw=true">
    </div>
</div>

<h3>4. Security Settings: Setting new PIN</h3>
<div class="row">
    <div class="column3">
         <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_whiteboxtesting_4.png?raw=true">
    </div>
    <div class="column2">
        <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_line_flow_4.png?raw=true">
    </div>
</div>

<h3>5. Statistics Filter: Changing filter fo charts</h3>
<div class="row">
    <div class="column3">
         <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_whiteboxtesting_5.png?raw=true">
    </div>
    <div class="column2">
        <img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_line_flow_5.png?raw=true">
    </div>
</div>


<hr>
<h1>Black box testing</h1>
<p>Black box testing is a testing process where the tester is not familiar with the internal structure or flow of the source code. He will just be familiar with the input and expected output and using them will create various test cases covering simple and edge cases.As explained earlier one of our team members will be a tester for a particular feature. But along with that person, we even asked some of our friends to use the app and give us feedback on the same. We concentrated on the below 5 features which covers different aspects of our product.</p>

<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_black_box_testing.png?raw=true"  class="center" style="width:100%"><br>
 <ul>
        <li><p>Scenarios 1,2 and 4 describes equivalence class where the Data input 1 column are valid inputs  and Data input 2 column are invalid input(example: empty input)</p></li>
        <li><p>Scenario 5 is Boundary value tests, where adding a range which is not present will give empty list to view.</p></li>
    </ul>

                
