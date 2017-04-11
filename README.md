# Dynamodb example

## Local dynamodb

First you will need to download a local version fo the dynamodb jar. You can do this by going to the url below and follow the instructions on that page

[http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html](http://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html)

## Start Local dynamoDB

To start the local version, go to the directory where you unpacked the zip and run this command

```sh
$ java -Djava.library.path=./DynamoDBLocal_lib -jar DynamoDBLocal.jar -sharedDb
```

This will start up the dynamoDB on local port 8000 and respond with a output that should look like this

```sh
Initializing DynamoDB Local with the following configuration:
Port:	8000
InMemory:	false
DbPath:	null
SharedDb:	true
shouldDelayTransientStatuses:	false
CorsParams:	*
```
## Creating the tables

Because you normally first need to crreate the table, some code from the example is used inside the unit-test to create the table and add some data to it.

This is a junit test, which can either be run by IDE or by running

```sh
$  mvn clean install
```

## Starting spring boot

You can either use the IDE to start it or use maven to start the spring boot app

```sh
$  mvn spring-boot:run
```

This will boot up the app on [localhost](http://localhost:8080/) on port 8080

## The problem

When you go the to the repository [users](http://localhost:8080/users) , this wil return an error 

```javascript
{
"cause": null,
"message": "PersistentEntity must not be null!"
}
```

## Need help

Could you have a look why this is happing or what i am doing wrong?