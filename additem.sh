#cat src/main/resources/assets/miencrap3minerat/blockstates/backrooms.json

read -r -p "> Enter item(s), space as splitter: " items_names

resources_PATH="src/client/resources"
echo ""
for itemname in $items_names; do
  jq -n --arg model "miencrap3minerat:item/$itemname" '{
    "model": {
      "type":"model",
      "model": $model
    }
  }' > "$resources_PATH/assets/miencrap3minerat/items/$itemname.json"

  # echo "УРРАААААА: $itemname"
  upperReg=$(echo "$itemname" | tr '[:lower:]' '[:upper:]')

  echo "public static RegisteredItem<?> $upperReg = ModItems.register(\"$itemname\", Item::new, new Item.Properties());"
done
echo ""
echo "[!] now add items in java (if you doesn't)."
