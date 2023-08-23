#!/bin/bash
xargs -I % -P 8 curl --location --request POST 'localhost:8080/race-condition/pay/with-lock' \
--header 'Content-Type: application/json' \
--header 'X-Idempotency-Key: d4d73218-472d-4092-b365-bd1619f90232' \
--data-raw '{
    "productName" : "Caneta",
    "amount" : "10.05"
}' < <(printf '%s\n' {1..200})
