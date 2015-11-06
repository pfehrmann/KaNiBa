# encoding: UTF-8
Feature: This is a sample feature 

	As an bar administrator
  I want to insert information about my bar
  So that it will be shown correctly on KaNiBa

@caseid1 @caseid2 
Scenario: Insert Correct information 
	Given I am logged in as "Bar Administrator" 
	And I am on the "Insert Bar Inormation" site 
	When I insert "Stüvchen" in the field "Name" 
	And I insert "Beispielstraße 17, 76138 Karlsruhe" in the field "Addresse" 
	And I insert "Das Stüvchen ist eine nette kleine Bar im Herzen von Karlsruhe." in the field Description 
	And I submit a correct image 
	Then I see the message "Ihre Bar wurde erfolgreich in die Datenbank aufgenommen" 
	
Scenario: Insert already existing Bar 
	Given I am logged in as "Bar Administrator" 
	And I am on the "Insert Bar Inormation" site 
	When I insert "Agosthea" in the field "Name" 
	And I insert "Rüppurer Straße 17, 76137 Karlsruhe" in the field "Addresse" 
	And I insert "Disco mit zwei Floors" in the field "Beschreibung" 
	And I submit a correct image 
	Then I see the message "Die Bar gibt es bereits." 	
			