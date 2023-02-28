curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{"description": "hello world(update)", "price": 12000}' \
 localhost:8091/product/$1 | jq .