# Introduction

This project is a simple **anonymous** feedback system, e.g. to be used in lectures. 

## Installation

This project is meant to be deployed to [heroku](http://www.heroku.com), although other deployment strategies for standard spring-boot applications
are applicable. To configure and deploy this application, you have to create applications-heroku.properties and change the value of one I18N key.
 
### application-heroku.properties

Store a new file under src/main/resources/application-heroku.properties and fill in the values for the following fields

    # GMail credentials
    email.username=
    email.password=
    
    # Postgresql credentials.
    spring.datasource.url=jdbc:postgresql://
    spring.datasource.username=
    spring.datasource.password=
    
    # Receiver email and subject.
    email=
    subject=
    
You can view your postgresql credentials by executing ```heroku config```.    
    
### I18N
    
Change the text in the files src/main/resources/messages(_de).properties for the fields
 
    title=
    index.project.name=
    about.message=
    
## Deployment
    
After you have configured the aforementioned properties, a commit and
    
    git push heroku master
    
should suffice.
    
## Limitations    

Currently, only GMail is supported as the email provider.
