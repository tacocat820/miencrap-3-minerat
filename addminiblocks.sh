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

read -r -p "> Enter block(s) to make miniblocks for, space as splitter: " blocks_names

client_resources_PATH="src/client/resources"
server_resources_PATH="src/main/resources"

namespace="miencrap3minerat"
textures_namespace=$namespace
if [ "$vanilla" = "1" ]; then
  textures_namespace="minecraft"
fi

for blockname in $blocks_names; do
  _blockname_reduced=$(echo "$blockname" | sed 's/_block//') #'_block' suffic deleting
  new_block_name="${_blockname_reduced}_miniblocks"
  #registering blockstates
  jq -n --arg model "$namespace:block/$new_block_name" '{
    "multipart": [
      {
        "apply": {
          "uvlock": true,
          "model": $model
        },
        "when": {
          "blk_000": "true"
        }
      },
      {
        "apply": {
          "uvlock": true,
          "model": $model,
          "z": 90
        },
        "when": {
          "blk_010" : "true"
        }
      },
      {
        "apply": {
          "uvlock": true,
          "model": $model,
          "y": -90
        },
        "when": {
          "blk_001" : "true"
        }
      },
      {
        "apply": {
          "uvlock": true,
          "model": $model,
          "x": 180
        },
        "when": {
          "blk_011": "true"
        }
      },
      {
        "apply": {
          "uvlock": true,
          "model": $model,
          "y": 90
        },
        "when": {
          "blk_100": "true"
        }
      },
      {
        "apply": {
          "uvlock": true,
          "model": $model,
          "z": 180
        },
        "when": {
          "blk_110" : "true"
        }
      },
      {
        "apply": {
          "uvlock": true,
          "model": $model,
          "y": 180
        },
        "when": {
          "blk_101" : "true"
        }
      },
      {
        "when": {
          "blk_111": "true"
        },
        "apply": {
          "uvlock": true,
          "model": $model,
          "x": 90,
          "z": 180
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
  jq -n --arg original_block_texture "$textures_namespace:block/$blockname" --arg parent "$namespace:templates/miniblocks" '{
    "parent": $parent,
    "textures": {
      "block": $original_block_texture
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

  echo "public static RegisteredBlock $upperReg = new GenericBlock(\"$new_block_name\", p -> new MiniBlocksBlock(p, $block_statement)).copyOf($block_statement).reg();" # new
  # echo "public static RegisteredBlock $upperReg = new GenericBlock(\"$new_block_name\", MiniBlocksBlock::new).copyOf($block_statement).reg();" # old
done
echo "[!] now add blocks in java (if you doesn't)."