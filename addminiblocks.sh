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

read -r -p "> Enter block(s) to make miniblocks for, space as splitter: " blocks_names

client_resources_PATH="src/client/resources"
server_resources_PATH="src/main/resources"

for blockname in $blocks_names; do

  #registering blockstates
  jq -n --arg model "miencrap3minerat:block/$(blockname)_miniblocks" '{
    "multipart": [
      {
        "apply": {"uvlock": true, "model":$model},
        "when": {"blk_000": "true"}
      },
      {
        "apply": {"uvlock": true, "model":$model, "z":90},
        "when": {"blk_001": "true"}
      },
      {
        "apply": {"uvlock": true, "model":$model, "x":90},
        "when": {"blk_010": "true"}
      },
      {
        "apply": {"uvlock": true, "model":$model, "x":180},
        "when": {"blk_011": "true"}
      },
      {
        "apply": {"uvlock": true, "model":$model, "y":90},
        "when": {"blk_100": "true"}
      },
      {
        "apply": {"uvlock": true, "model":$model, "z":180},
        "when": {"blk_101": "true"}
      },
      {
        "apply": {"uvlock": true, "model":$model, "y":180},
        "when": {"blk_110": "true"}
      },
      {
        "when": {"blk_111": "true"},
        "apply": {"uvlock": true, "model":$model, "x": 90, "z": 180}
      }
    ]
  }' > "$client_resources_PATH/assets/miencrap3minerat/blockstates/$(blockname)_miniblocks.json"

  #item model
  jq -n --arg model "miencrap3minerat:block/$(blockname)_miniblocks" '{
    "model": {
      "type":"model",
      "model": $model
    }
  }' > "$client_resources_PATH/assets/miencrap3minerat/items/$(blockname)_miniblocks.json"

  #block model too
  jq -n --arg original_block_model "miencrap3minerat:block/$(blockname)" '{
    "parent": "miencrap3minerat:templates/miniblocks",
    "textures": {
      "block": $original_block_model
    }
  }' > "$client_resources_PATH/assets/miencrap3minerat/items/$(blockname)_miniblocks.json"

  #loot table
  if [ "$ltable" = "1" ]; then
    jq --compact-output -n --arg item_id "miencrap3minerat:$(blockname)_miniblocks" '{
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
    }' > "$server_resources_PATH/data/miencrap3minerat/loot_table/blocks/$(blockname)_miniblocks.json"
  fi
  
  upperReg=$(echo "$(blockname)_miniblocks" | tr '[:lower:]' '[:upper:]')

  #put into java!!!
  echo "public static RegisteredBlock $upperReg = new GenericBlock(\"$(blockname)_miniblocks\", MiniBlocksBlock::new, SoundType.STONE, 1.0f,1.0f).reg();"
done
echo "[!] now add blocks in java (if you doesn't)."