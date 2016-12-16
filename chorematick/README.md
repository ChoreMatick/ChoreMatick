##ChoreMatick

<h1>Amazon Alexa Skill that allows parents to provide a list of chores for their child.</h1>

The child can ask Alexa for their specific chore for the day, like, "Tidy your room" or "Clean the kitchen". The child tells Alexa once they have completed the chore. This triggers a notification to send a card to the parent's phone where the Alexa app is downloaded. The card says that the child has completed their chore, and provides a 4-digit password for the parent to tell Alexa. The password verificaiton ensures that only the parent can confirm with Alexa that the chore was completed. Once 10 chores are marked as completed, a card with an incentive recommended from Amazon is shown. In the future, we would like to implement Amazon's Advertising API in order to send customised recommendations based on the child's age, etc.
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
* Codeships
