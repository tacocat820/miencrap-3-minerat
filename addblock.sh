#cat src/main/resources/assets/miencrap3minerat/blockstates/backrooms.json

#blockname="name"
#read -r -p "> Enter block name: " blockname

echo "[?] run with -t to create loot tables too."
ltable=0

while getopts "t" opt; do
  case ${opt} in
    t )
      echo "| Loot table: ON"
      ltable=1
      ;;
    \? )
      exit 1
      ;;
  esac
done

read -r -p "> Enter block(s), space as splitter: " blocks_names

client_resources_PATH="src/client/resources"
server_resources_PATH="src/main/resources"

for blockname in $blocks_names; do

  jq -n --arg model "miencrap3minerat:block/$blockname" '{
    "variants": {
      "": {
        "model": $model
      }
    }
  }' > "$client_resources_PATH/assets/miencrap3minerat/blockstates/$blockname.json"

  #дасукаблятьпочему оно не работает
  jq -n --arg model "miencrap3minerat:block/$blockname" '{
    "model": {
      "type":"model",
      "model": $model
    }
  }' > "$client_resources_PATH/assets/miencrap3minerat/items/$blockname.json"

  if [ "$ltable" = "1" ]; then
    jq --compact-output -n --arg item_id "miencrap3minerat:$blockname" '{
      "pools": [
        {
          "rolls": 1,
          "entries": [
            {
              "type": "minecraft:item",
              "name": $item_id
            }
          ]
        }
      ]
    }' > "$server_resources_PATH/data/miencrap3minerat/loot_table/blocks/$blockname.json"
  fi
  
  upperReg=$(echo "$blockname" | tr '[:lower:]' '[:upper:]')

  echo "public static RegisteredBlock $upperReg = new GenericBlock(\"$blockname\", Block::new, SoundType.STONE, 1.0f,1.0f).reg();"
done
echo "[!] now add blocks in java (if you doesn't)."