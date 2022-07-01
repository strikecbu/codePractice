curl --location --request POST 'http://localhost:8080/v1/libraryEvents' \
--header 'Content-Type: application/json' \
--data-raw '{
    "eventId": null,
    "book"  :{
        "id" : "12333",
        "name": "Elder Ring",
        "authorName": "Andy"
    }
}'

curl --location --request POST 'http://localhost:8080/v2/libraryEvents' \
--header 'Content-Type: application/json' \
--data-raw '{
    "eventId": null,
    "book"  :{
        "id" : "12333",
        "name": "Elder Ring",
        "authorName": "Andy"
    }
}'
