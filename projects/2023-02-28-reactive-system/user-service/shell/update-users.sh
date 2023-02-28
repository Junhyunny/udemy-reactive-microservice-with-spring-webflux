curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{"name": "Junhyunny", "balance": 10000}' \
 localhost:8092/user/$1 | jq .