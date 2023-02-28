curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"description": "hello world", "price": 100}' \
 localhost:8091/product | jq .
