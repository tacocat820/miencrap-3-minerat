#cat src/main/resources/assets/$namespace/blockstates/backrooms.json

#blockname="name"
#read -r -p "> Enter block name: " blockname

echo "[?] run with -t to create loot tables too."
echo "[?] run with -v to create for vanilla blocks."
ltable=0
vanilla=0

while getopts "t v" opt; do
  case ${opt} in
    v )
      echo "| Vanilla: ON"
      vanilla=1
      ;;
    t )
      echo "| Loot table: ON"
      ltable=1
      ;;
    \? )
      exit 1
      ;;
  esac
done

read -r -p "> Enter block(s) to make connected blocks for, space as splitter: " blocks_names

client_resources_PATH="src/client/resources"
server_resources_PATH="src/main/resources"

namespace="miencrap3minerat"
textures_namespace=$namespace
if [ "$vanilla" = "1" ]; then
  textures_namespace="minecraft"
fi

#decoratiob
DECORATION="miencrap3minerat:block/decoration/default"

for blockname in $blocks_names; do
  _blockname_reduced=$(echo "$blockname" | sed 's/_block//') #'_block' suffic deleting
  new_block_name="connected_${_blockname_reduced}"
  #registering blockstates
  jq -n --arg model "$namespace:block/$new_block_name" --arg DECORATION "$DECORATION" '{
    "multipart": [
      {
        "apply": {
          "model": $model
        }
      },
      {
        "apply": {
          "model": $DECORATION
        },
        "when": {
          "north": "false"
        }
      },
      {
        "apply": {
          "model": $DECORATION,
          "y":-90
        },
        "when": {
          "west": "false"
        }
      },
      {
        "apply": {
          "model": $DECORATION,
          "y":90
        },
        "when": {
          "east": "false"
        }
      },
      {
        "apply": {
          "model": $DECORATION,
          "y":180
        },
        "when": {
          "south": "false"
        }
      },
      {
        "apply": {
          "model": $DECORATION,
          "x":90
        },
        "when": {
          "down": "false"
        }
      },
      {
        "apply": {
          "model": $DECORATION,
          "x":-90
        },
        "when": {
          "up": "false"
        }
      }
    ]
  }' > "$client_resources_PATH/assets/$namespace/blockstates/$new_block_name.json"

  #item model
  jq -n --arg model "$namespace:block/$new_block_name" '{
    "model": {
      "type":"model",
      "model": $model
    }
  }' > "$client_resources_PATH/assets/$namespace/items/$new_block_name.json"

  #block model too
  jq -n --arg original_block_texture "$textures_namespace:block/$blockname" '{
    "parent": "block/cube_all",
    "textures": {
      "all": $original_block_texture
    }
  }' > "$client_resources_PATH/assets/$namespace/models/block/$new_block_name.json"

  #loot table
  if [ "$ltable" = "1" ]; then
    jq --compact-output -n --arg item_id "$namespace:$new_block_name" '{
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
    }' > "$server_resources_PATH/data/$namespace/loot_table/blocks/$new_block_name.json"
  fi
  
  upperReg=$(echo "$new_block_name" | tr '[:lower:]' '[:upper:]')
  originalUpperReg=$(echo "$blockname" | tr '[:lower:]' '[:upper:]')

  #put into java!!!
  block_statement="$originalUpperReg.block"
  if [ "$vanilla" = "1" ]; then
    block_statement="Blocks.$originalUpperReg"
  fi

  echo "public static RegisteredBlock $upperReg = new GenericBlock(\"$new_block_name\", ConnectedTextureBlock::new).copyOf($block_statement).reg();"
done
echo "[!] now add blocks in java (if you doesn't)."