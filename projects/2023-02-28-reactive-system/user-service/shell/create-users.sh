curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name": "in", "balance": 200}' \
 localhost:8092/user | jq .