#!/bin/bash
xargs -I % -P 8 curl --location --request POST 'localhost:8080/race-conditional/api/java/customers' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name" : "Godofredo",
    "secondName" : "Ratimbum",
    "age" : 75
}' < <(printf '%s\n' {1..200})
