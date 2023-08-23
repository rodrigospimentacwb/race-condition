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

Use a database manager of your choice and log into Postman, which is running on Docker. In case of doubt, refer to the parameters in the docker-compose file.

Call the cUrl below 

````
xargs -I % -P 8 curl --location --request POST 'localhost:8080/race-condition/pay/without-lock' \
--header 'Content-Type: application/json' \
--header 'X-Idempotency-Key: d4d73218-472d-4092-b365-bd1619f90232' \
--data-raw '{
    "productName" : "Caneta",
    "amount" : "10.05"
}' < <(printf '%s\n' {1..200})
````

Analyze the Payment table. You will notice that there are several duplicated payment records. This occurs because cURL will execute multiple triggers on different threads, resulting in a race condition. Even with the proper handling before persisting payments in the table, including a deliberate delay to simulate a slow query, the system receives and processes calls across multiple threads. As a result, the system does not behave as expected due to the concurrency between requests.

## To see the issue resolved.

Stop the project and start it again; you will observe that the table is cleared for a new test. Now, execute the following cURL command.
```
xargs -I % -P 8 curl --location --request POST 'localhost:8080/race-condition/pay/with-lock' \
--header 'Content-Type: application/json' \
--header 'X-Idempotency-Key: d4d73218-472d-4092-b365-bd1619f90232' \
--data-raw '{
    "productName" : "Caneta",
    "amount" : "10.05"
}' < <(printf '%s\n' {1..200})
```

Analyzing the payment table, you will see that you will not have duplicate records, as the system manages a lock by a key, in this case if the key has already entered and is being processed, the others will be queued and will wait for its execution, avoiding executions of this key in competition.






