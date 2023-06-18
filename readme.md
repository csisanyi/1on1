This project was made for a recruitment technical assesment for a senior Java/Spring developer position
Although I haven't heard back from the company, so I take it as a negative feedback and warn you not to use it as a blueprint if you get into a similar situation. 
It is a working MVP for the problem
Check instruction.md first

# Start
App is using MongoDB<br>
If you don't have mongodb running on port 27017 please use Docker compose file from 
the module folder to set up a mongo instance docker-compose.yaml with the command <br> docker-compose up -d

To run the project use: an IDE or execute the jar from the root module <br> java -jar homework-0.0.1-SNAPSHOT.jar

If the users collection does not contain any documents, the commandlineRunner
creates 4 Users and 6 1on1-s automatically

# Interaction
For the smoothest experience, interact with the app via swagger API UI<br>
http://localhost:8080/swagger-ui/index.html


# Auth
I wasn't really sure what you meant by assuming a specific header:
 > We can assume the authenticated user id is available in the **X-AUTHENTICATED-USER** request header.

Instead of assuming, I set the header value during login with a custom filter.

To authorize the user, please use <br>
http://localhost:8080/swagger-ui/index.html#/auth-controller/login_1 <br>
The following users are all set up, so you can use whichever you want,or create your own user
<br>{<br>
"email": "user@one.com",<br>
"password": "password"<br>
} <br> you can swap @one with any number from 1 to 6 in letter form

# P.S
I hope you know how to use swagger, but it's pretty straight forward anyways.
Please forgive me for not encrypting the passwords and let me know if you have any questions!



