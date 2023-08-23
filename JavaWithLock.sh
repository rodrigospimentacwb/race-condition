#!/bin/bash
xargs -I % -P 8 curl --location --request POST 'localhost:8080/race-conditin/pay/with-lock' \
--header 'Content-Type: application/json' \
--data-raw '{
    "productName" : "Caneta",
    "amount" : "10.05"
}' < <(printf '%s\n' {1..200})
