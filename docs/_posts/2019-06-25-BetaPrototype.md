---
layout: post
title: "Beta Prototype"
date: 2019-06-25
---

<html>
    <body style="background-color:powderblue;">
                
<h1>Overview</h1>
<p> “It’s time for some testing!”<br>
Hello and welcome to our penultimate blog for the series of ‘YourEuro’ - A money control app. In this blog we discuss in detail the process of testing which we followed to make our app flawless and make it Playstore ready.</p><hr><br>

<h1>Test Case Design</h1><br>
<p>Testing is a default part of software development package. It helps in enhancing the quality of the product, make it more reliable, give an effective performances and flawless experience. In the end these measures are required to stay in business.<br><br>  
In a team of 4, one of the members will be the developer for a feature while two other will be the code reviewer. The fourth person will not participate in the code review and thereby be ignorant of the structure of the source code. He will act as a tester and write test cases for blackbox testing. And the same is repeated with different developer and tester for different features.
</p> 

<img src="https://github.com/DBSE-teaching/isee2019-ARTexceptionals/blob/master/docs/images/4_tester.png?raw=true" width="400" height="250"><br>

<p>We followed standard industrial norms of testing a product, and following were the different types of testing we executed.
<ul>
    <li><pr>Unit Testing</pr></li>
  <li><pr>Integration Testing</pr></li>
  <li><pr>Functional Testing</pr></li>
  <li><pr>System Testing</pr></li>
  <li><pr>Acceptance Testing</li>
</ul></p><br><br>

<p>Some of these test methods can be further classified into
    <ul>
    <li><pr><b>White-Box Testing:</b> A software testing process where the tester will have knowledge of internal structure/ design/ implementation of the produce under consideration.</pr></li>
  <li><pr><b>Black-Box Testing:</b> A software testing process where the tester will NOT have knowledge of internal structure/ design/ implementation of the produce under consideration </pr></li>
</ul></p><br>

<pr><b>Unit Testing:</b>  Unit testing is the foundation of testing process. This can be executed in parallel while the feature is implemented by the developer. And since the developer is expected to know A-Z of what he has written in the source code, white-box testing was followed. Android supports many libraries that support unit testing. In YourEuro app we have used JUnit and Mockito. Example: A simple model class like the CashRecord has multiple fields to be set before saving it to database. Using Mockito the values in constructor is called and JUnit helps to assert the set values.</p><br>

<pr><b>Integration Testing:</b>  In this phase we test combination of basic unit of the app. In the previous blog we discussed about MVC pattern and how our MoneyControlManager(which is a controller) acts as a mediator between models and Views. Testing the controller would cover more than 50% of our integration testing. We used Robolectric framework along with Mockito to execute the same. Robolectric helps us mock the methods of dependency classes and thus integration of several components in the controller can be tested. Apart from this white box testing, one of our team members who didn’t participate in the code review would do a blackbox testing.
Example: Validating category threshold with the statistics charts.</p><br>

<pr><b>Functional Testing:</b>  The product development process followed our user stories and functional test cases were derived from the same user stories. While one of our developers worked on implementing the features, another team member would design <pr><b>the test cases following black box testing. Example: As a user I want to set my transactions as recurring.</p><br>

<pr><b>System Testing:</b>  The product is tested in different environments and devices. Example: UI testing was done on devices of different screen size and different android version using emulators.</p><br>

<pr><b>Acceptance Testing:</b>  Prototype was created for each user story/feature and demonstrated to the client in weekly meetings. Starting from UI to different features, client feedback was taken as acceptance. Any changes from the developer was immediately notified to the client and any requests from the client was taken into consideration and implemented in the following development sprint planning. Example: Filters for history was created with single category, while the customer feedback was to have multiple selection of categories.</p><br>

<pr>To summarise testing phases:</p>


                
