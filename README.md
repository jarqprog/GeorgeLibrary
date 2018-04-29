# 'Text repository' is a java web application to manage texts/notes (add, modify, remove, get access to note's history)
--------------------------------

# User stories:

As regular User I can:\
    - register account via website;\
    - ask for remove account;\
    - login into my account via website;\
    - create repository for texts;\
    - add note;\
    - remove note;\
    - see my notes;\
    - modify chosen note;\
    - see history of chosen note (modifications, various versions);\
    - remove repository.

As Admin I have all the options of an ordinary user and in addition:\
    - can see list of users;\
    - can see chosen User's basic data;\
    - can see basic info about chosen User's repositories (eg. size);
    - suspend User's account (so account isn't removed, but User can't login on it);\
    - remove User's account;\
    - remove User's repository.

-------------
# to compile & run:

mvn clean package

java -jar target/TextRepository-1.0-SNAPSHOT-shaded.jar

used patterns: mvc, dao, factory pattern, sql database
