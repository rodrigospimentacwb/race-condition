# race-condition

Project created to demonstrate how to show the error in a race condition case and a treatment for the problem using Lock with Redis.

## Prerequisites

* Java 17 installed
* Docker installed

## How to begin

Start by starting the docker-compose present in the root of this project by running the command:

```
    docker-compose up
```

Start the project by running the DemoApplication class.

## To see trouble happen

Use a database manager of your choice and log in Postman that is running on docker, in case of doubt, see the parameters in the docker-compose file.

Call the cUrl below 

````
xargs -I % -P 8 curl --location --request POST 'localhost:8080/projeto-rest/pay/without-lock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productName" : "Caneta",
    "amount" : "10.05"
}' < <(printf '%s\n' {1..200})
````

Analise the Payement table. You will notice that you will have several duplicate payment records. That's because cUrl will run multiple triggers on different threads forcing a race condition. Even with the find treatment before persisting in payment, with a forced delay to simulate a slow query, the system receives and handles calls in several threads, causing. the system does not behave as expected due to concurrency between requests.

## To see the problem resolved

Stop the project and upload it again, you will see that the table will be clean for a new test. Now call cUrl below.

````
xargs -I % -P 8 curl --location --request POST 'localhost:8080/projeto-rest/pay/with-lock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productName" : "Caneta",
    "amount" : "10.05"
}' < <(printf '%s\n' {1..200})
````

Analyzing the payment table, you will see that you will not have duplicate records, as the system manages a lock by a key, in this case if the key has already entered and is being processed, the others will be queued and will wait for its execution, avoiding executions of this key in competition.






