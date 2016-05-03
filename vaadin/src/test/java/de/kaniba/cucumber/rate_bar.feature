Feature: Rate a bar 

	As a user
  I want to rate a bar

Scenario: Rate a bar 
	Given I am in "Bar Details" of the bar "Stüvchen" 
	And I have not rated it 
	When I click on 5 stars 
	Then I see the new rating of 5 stars 
	And I see the message "Thank you for rating" 
	
Scenario: Redo the rating 
	Given I am in "Bar Details" of the bar "Stüvchen" 
	And I have already rated it with 5 stars 
	When I click on 3 stars 
	Then I see the new rating of 3 stars 
	And I see the message "Thank you for rating" 
			