# encoding: UTF-8
Feature: Login 

	As a user
I want to login

Scenario: Login Failure 
	Given I open the url "localhost:8080" 
	When I input my user name and password with "philipp" and "password" 
	Then I should see an error 

Scenario: Login Success 
	Given I open the url "localhost:8080" 
	When I input my user name and password with "philipp" and "test" 
	Then I should not see an error 
     