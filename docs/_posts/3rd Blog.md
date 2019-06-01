---
layout: post
title: "ISEE 2019 -- Advanced Prototype"
date: 2019-06-04
---

<html>
        <body style="background-color:powderblue;">
<h1>Design Pattern</h1><br>
<h3>The objective of our App is to keep track of money transactions on a daily basis and also to give the summarized report.</h3> 
                 <hr><br>
                
<h3>It is very difficult to create your User interfaces, Control logic and Databases together. We chose to follow MVC architecture in the development of our App to overcome this issue. MVC is an acronym for Model-View-Control. By following this architecture, we can separate our Model (database), Views (Activities), and Controller (Control logic) and it allows each to change without affecting the others. </h3>
 
                  <hr><br> 
 
<p>The CashRecord class is our core entity and every transaction is an object of this class. The created objects will be saved in the database. </p>
<p>The CashRecordDatabase is our Model it extends RoomDatabase and will be used for interaction with the Database. </p>
<p>MoneyControlManager is our main controller. Based on the request from the user it will perform operations on the model and modify the display accordingly.</p>
<p>Our controller follows singleton pattern, since it interacts with the database and we want this interaction to be synchronised. Meaning multiple objects of database should not interact with database simultaneously. </p>

<h1>Coding Conventions</h1>
<p>Coding conventions will help in easing the understanding, readability and debugging of code. For having consistent data everyone should adhere to same coding conventions. “This project is in line with the standards provided in the Google Java Style Guide. " </p>


1.	Packaging:
a.	
2.	File Names
a.	ClassNames: example: CashRecord.java
b.	ResourceName: example: activity.main.xml
3.	Source file code
a.	Indentations:// TODO: snippet
b.	Comments: 
i.	Description of class using java comments

<br><br><hr>
Example: /**
 * CashRecord is main model which represents each transaction.
 */
@Entity(tableName = "cashrecord")
public class CashRecord implements Parcelable {
i.	Description of methods along with the parameters using Javadoc.
addCashRecord()
ii.	In line comments to explain the code logic if necessary
iii.	//Raw query to retrieve all cash records. Conditions are added only if necessary
SimpleSQLiteQuery simpleSQLiteQuery = new SimpleSQLiteQuery("SELECT * FROM cashrecord WHERE uid NOT NULL "+
        (cashRecordFilter.isAmountRangeFilter()?"AND amount BETWEEN '"+cashRecordFilter.getStartAmount()+"' AND '"+cashRecordFilter.getEndAmount()+"' ":"")+
        (cashRecordFilter.isDateRangeFilter()?"AND timeStamp BETWEEN '"+cashRecordFilter.getStartTimeStamp()+"' AND '"+cashRecordFilter.getEndTimeStamp()+"' ":"")+
        (cashRecordFilter.isCategoryFilter()?"AND categoryID = '"+category.getCategoryID()+"' AND catagoryName = '"+category.getCatagoryName()+"' AND imageID = '"+category.getImageID()+"' ":"")+
        (cashRecordFilter.isPaymentFilter()?"AND paymenttype = '"+ cashRecordFilter.getPaymentType()+"'":""));
a.	
iv.	In case partial code is getting merged comment needs to have ‘Todo’
Example: cashRecord.setTimeStamp(new Date().getTime());// TODO: Make sure to replace actual value instead of current value

<h1>Variable declarations</h1>
•	Descriptive variable names
•	Camel case
•	Names for: class,method,variable,functions,constants

4.	Annotations
5.	Generalisation:
a.	Strings stored in resources: @String/app_name
1.	R.String.euro
b.	Colors and Dimensions
6.	Notation for imports
7.	File ownership
8.	/**
 * Created by Madhu on 5/10/19.
 */
public class PinActivity extends AppCompatActivity {


<p>Initially everyone is briefed up on what coding conventions that will be followed in our project and also after each developer raises a merge request, the reviewers will check the changes thoroughly and give necessary comments and actions to be taken care of before merging. Once the comments are taken care then the code will be merged into main branch. By this way we make sure that everyone follows the necessary coding conventions.</p>

 
 

<h1>Context of use</h1>
<p>It is important to identify the target users for developing a relevant and useful application. It helps us in identifying what key features and functionalities need to be included in our app. Following are the two personas we have identified for our App who represents majority of our user group, by answering the following questions. </p> 
1.	What is the highest level of education this person has received?
2.	What is the professional background of the user?
3.	What is the purpose/goal of user to use our App?
4.	When and where will the user use our App?
5.	What are user’s expectations from the App?

<h1>Design principles Implemented</h1>
<p>Good design makes a product useful • A product is bought to be used. It has to satisfy certain criteria, not only functional, but also psychological and aesthetic. Good design emphasizes the usefulness of a product whilst disregarding anything that could possibly detract from it.</p>
<p>Good design is as little design as possible • Less, but better – because it concentrates on the essential aspects, and the products are not burdened with non-essentials.</p>

<h1>Error preventions</h1>
 <h3>Drop down lists, Date and time pickers</h3>
<p>In order to restrict any random inputs from user, wherever we need a specific format of inputs from user we implemented either a list of entries from dropdown or we included a time & date picker so that we get only the required input format</p>

<h1>Error reporting</h1>
Tool tips: Tool tips are added where the user might unknowingly submit wrong data. Like when user tried to create record without amount then the tool tip “amount field cannot be empty” will appear on screen.

<br><hr>

Toast messages: Necessary Toast messages are provided either it is a successful action or if it’s a unsuccessful action then the message will tell what went wrong.

 <br><hr>
Toggles • allow the user to change a setting between two states • most effective when the on/off states are visually distinct

 <br><br><hr>
<h1>User Control and Freedom</h1>
<p>All dialogs have a cancel option where he can just cancel the operation if he doesn’t want to continue with the operation</p>
<h1>Buttons</h1>
<p>All buttons are given proper label which describes clearly what action will be performed by clicking it.</p>

• good graphic design 
• few, well-chosen colours and fonts 
• follow colour guidelines 
• group with whitespace 
• align controls sensibly

<h1>Colour Theory</h1>

<h1>Summary of changes</h1>
<p>In comparison to basic prototype there have been few changes and additions in the design. 
Changes: 
Now the user can enable or disable the pin login option from settings window and change pin option have been shifted to settings screen. The option for selecting the currency have been added in the settings screen.  </p>
<h1>Additions</h1>
<h3>Categories should be shown with both icon and name. User should be able create new categories</h3>
//TODO:Show class diagram change for cashrecord
//Changes in database, created a separate db for category.
//While adding custom category, suggest a predefined list of different icons for the new category.(can be user story)
User should be able to delete an existing record. 
//TODO: screenshots of detail activity before and after delete option

User should be able to set some threshold/limit for amount of money to be spent on each category. Beyond the threshold every transaction of that record should be shown in Red colour. It should also be reflected in graph.
We need at least two charts for visualising the transactions
//The layouts were changed to handle multiple charts/graphs
Monthly summary need to be exported as PDF and should be sent in E-mail.
//The MoneycontrolManager was added with another method to convert the database into pdf.
monyeControllManager.getPdf();


