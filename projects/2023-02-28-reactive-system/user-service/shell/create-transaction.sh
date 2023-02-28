curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"userId": 1, "amount": 200}' \
 localhost:8092/user/transaction | jq .