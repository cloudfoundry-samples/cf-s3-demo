= CF S3 Demo

This is a simple example of using Amazon S3 for asset storage. It is an image catalog to which you can upload images and see them on the main page.

== Running Locally

* Create a file called `application.yml` in `src/main/resources`. It should have the following structure (replace the values with those appropriate for your environment):

     ---
     s3:
       aws_access_key: <YOUR_AWS_ACCESS_KEY>
       aws_secret_key: <YOUR_AWS_SECRET_KEY>
       bucket: the-bucket-name-you-want-to-use
     mongo:
       host: mongo-host
       port: 12345
       dbname: mongo-db
       username: mongo-user
       password: mongo-pw

* Run `./gradlew assemble`
* Run `java -jar build/libs/cf-s3-demo.jar`
* Browse to http://localhost:8080

== Running on Cloud Foundry

Assuming you already have an account at http://run.pivotal.io:

* Using `cf create-service`, create a MongoLab service.
* Using `cf create-service`, create a User-Provided service, making sure its name begins with "s3". It should have the following credentials (assign values appropriate for your environment):
    * `awsAccessKey`
    * `awsSecretKey`
    * `bucket1`
* Run `./gradlew assemble`
* Run `cf push --path build/libs/cf-s3-demo.jar`. When asked if you'd like to bind existing services, bind the two services you created.



