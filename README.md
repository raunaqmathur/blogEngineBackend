# blogEngine backend
blogEngine is an application which act as a backend for creating a blogs engine website.
This applicaiton can be used to interface with backend DB [MongoDb] to store and manage blogs related information. 
This application exposes Rest API's, which can be leveraged from a front end app [please refer https://github.com/raunaqmathur/blog-engine-ui].

## pre-requisites
1) java 1.8 
2) maven
3) git
4) install mongodb local [follow post - https://docs.mongodb.com/manual/administration/install-community/]
5) download mongodb compass from https://www.mongodb.com/try/download/compass [it provides an UI to connect to mongoDb server and run queries and visualize data]

## build instruction
1) start mongodb server - after following pre-requisites [point 4 and 5]
2) From mongodb compass - create a DB named - "blogEngine"
3) inside DB "blogEngine" create four collections "User", "Post", "Blog", "Comment"
4) open commandline and move to desired folder where you want to clone code repo in your machine [git clone https://github.com/raunaqmathur/blogEngineBackend.git]
5) cd blogEngineBackend
6) mvn clean install
7) mvn spring-boot:run [Note: this will start tomcat server on port 8080, make sure this port is free]
8) server should be up on port 8080
