curl --header "Content-Type: application/json" \
  --request PUT \
  --data '{"description": "TV-86544518-36e4-458b-8f27-77e0d097489a", "price": 170}' \
 localhost:8091/product/$1 | jq .