curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"productId": "63fe6fcad3e6124f7887c141", "userId": 3}' \
 localhost:8080/order | jq .
