#cat src/main/resources/assets/miencrap3minerat/blockstates/backrooms.json

read -r -p "> Enter item(s), space as splitter: " items_names

resources_PATH="src/client/resources"

for itemname in $items_names; do
  jq -n --arg model "miencrap3minerat:item/$itemname" '{
    "model": {
      "type":"model",
      "model": $model
    }
  }' > "$resources_PATH/assets/miencrap3minerat/items/$itemname.json"

  echo "УРРАААААА: $itemname"
done
echo "[!] now add items in java (if you doesn't)."
