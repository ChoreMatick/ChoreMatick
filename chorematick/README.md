#ChoreMatick

##An Amazon Alexa Skill that allows parents to provide a list of chores for their children to do. 

 A parent can add a specific chore for a specific day, like, "Tidy your room" or "Clean the kitchen". <br>
 The child can ask Alexa which chore they need to do for the day. <br>
 The child tells Alexa once they have completed the chore. This triggers a notification to send a card to the parent's phone where the Alexa app is downloaded. The card says that the child has completed their chore, and provides a 4-digit password for the parent to tell Alexa. The password verification ensures that only the parent can confirm with Alexa that the chore was completed. <br>
 Once 10 chores are marked as completed, a card with an incentive recommended from Amazon is shown. In the future, we would like to implement Amazon's Advertising API in order to send customised recommendations based on the child's age, hobbies, etc.
------

#### Stack
* Java 8
* AWS DynamoDB
* AWS Lambda
* Amazon Skills Kit

#### Testing and Continuous Integration
* JUnit 5
* Mockito
* Coveralls
* Codeship
