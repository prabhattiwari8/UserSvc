UserSvc
=========

UserSvc uses HSQLDB as inmemory database and supports following 3 operations.

1. createUser
   ----------
   Takes input as JSON.
   
   Creates the user if not already available in the datastore.
   
   Throws error if user already exists.
   
2.  getAllUsers
    -----------
   
    Gives the list of all users that are in the datastore
   
3.  updateUser
    ------------
   
    Takes input as JSON
   
    Finds the user from the data store
   
    if found, updates the required fields
   
    if not found, sends back a 404 saying user not found

Build
-----
Run command to build and refresh dependencies from UserSvc home directory 
```./gradlew clean build --refresh-dependencies ``` 

Run
---
Run below command to run UserSvc
```java -jar build/libs/UserSvc-0.0.1-SNAPSHOT.jar```

Test Input
----------
JSON for create user (URL : ```http://localhost:8080/user/``` POST Mapping)

```{
    
     "firstName": "testFirstname",
     "middleName": null,
     "lastName": "testlastname",
     "age": 10,
     "gender": "M",
     "phone": "1234567890",
     "zip": 0
   }
```
