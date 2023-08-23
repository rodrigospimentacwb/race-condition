#!/bin/bash
xargs -I % -P 8 curl --location --request POST 'localhost:8080/projeto-rest/pay' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productName" : "Caneta",
    "amount" : "10.05"
}' < <(printf '%s\n' {1..200})
