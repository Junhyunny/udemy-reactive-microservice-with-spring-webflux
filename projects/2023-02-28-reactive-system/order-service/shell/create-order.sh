curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"productId": "63fe7867f78f4e4076d4e2f9", "userId": 3}' \
 localhost:8080/order | jq .
