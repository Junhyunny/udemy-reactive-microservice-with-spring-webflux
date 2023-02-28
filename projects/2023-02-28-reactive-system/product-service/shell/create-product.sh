curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"description": "hello world", "price": 9000}' \
 localhost:8091/product | jq .
