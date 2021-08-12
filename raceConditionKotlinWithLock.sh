#!/bin/bash
xargs -I % -P 8 curl --location --request POST 'localhost:8080/race-conditional/api/kotlin/customers/lock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Etevaldo",
    "secondName" : "Ratimbum",
    "age" : 18
}' < <(printf '%s\n' {1..200})
