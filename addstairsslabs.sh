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

read -r -p "> Enter block(s) to make slabs / stairs for, with space as splitter: " blocks_names

client_resources_PATH="src/client/resources"
server_resources_PATH="src/main/resources"

for blockname in $blocks_names; do

  jq -n --arg stairs "miencrap3minerat:block/${blockname}_stairs" --arg stairs_outer "miencrap3minerat:block/${blockname}_stairs_outer" --arg stairs_inner "miencrap3minerat:block/${blockname}_stairs_inner" '{
  "variants": {
    "facing=east,half=bottom,shape=inner_left": {
      "model": $stairs_inner,
      "uvlock": true,
      "y": 270
    },
    "facing=east,half=bottom,shape=inner_right": {
      "model": $stairs_inner
    },
    "facing=east,half=bottom,shape=outer_left": {
      "model": $stairs_outer,
      "uvlock": true,
      "y": 270
    },
    "facing=east,half=bottom,shape=outer_right": {
      "model": $stairs_outer
    },
    "facing=east,half=bottom,shape=straight": {
      "model": $stairs
    },
    "facing=east,half=top,shape=inner_left": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180
    },
    "facing=east,half=top,shape=inner_right": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180,
      "y": 90
    },
    "facing=east,half=top,shape=outer_left": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180
    },
    "facing=east,half=top,shape=outer_right": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180,
      "y": 90
    },
    "facing=east,half=top,shape=straight": {
      "model": $stairs,
      "uvlock": true,
      "x": 180
    },
    "facing=north,half=bottom,shape=inner_left": {
      "model": $stairs_inner,
      "uvlock": true,
      "y": 180
    },
    "facing=north,half=bottom,shape=inner_right": {
      "model": $stairs_inner,
      "uvlock": true,
      "y": 270
    },
    "facing=north,half=bottom,shape=outer_left": {
      "model": $stairs_outer,
      "uvlock": true,
      "y": 180
    },
    "facing=north,half=bottom,shape=outer_right": {
      "model": $stairs_outer,
      "uvlock": true,
      "y": 270
    },
    "facing=north,half=bottom,shape=straight": {
      "model": $stairs,
      "uvlock": true,
      "y": 270
    },
    "facing=north,half=top,shape=inner_left": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180,
      "y": 270
    },
    "facing=north,half=top,shape=inner_right": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180
    },
    "facing=north,half=top,shape=outer_left": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180,
      "y": 270
    },
    "facing=north,half=top,shape=outer_right": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180
    },
    "facing=north,half=top,shape=straight": {
      "model": $stairs,
      "uvlock": true,
      "x": 180,
      "y": 270
    },
    "facing=south,half=bottom,shape=inner_left": {
      "model": $stairs_inner
    },
    "facing=south,half=bottom,shape=inner_right": {
      "model": $stairs_inner,
      "uvlock": true,
      "y": 90
    },
    "facing=south,half=bottom,shape=outer_left": {
      "model": $stairs_outer
    },
    "facing=south,half=bottom,shape=outer_right": {
      "model": $stairs_outer,
      "uvlock": true,
      "y": 90
    },
    "facing=south,half=bottom,shape=straight": {
      "model": $stairs,
      "uvlock": true,
      "y": 90
    },
    "facing=south,half=top,shape=inner_left": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180,
      "y": 90
    },
    "facing=south,half=top,shape=inner_right": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180,
      "y": 180
    },
    "facing=south,half=top,shape=outer_left": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180,
      "y": 90
    },
    "facing=south,half=top,shape=outer_right": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180,
      "y": 180
    },
    "facing=south,half=top,shape=straight": {
      "model": $stairs,
      "uvlock": true,
      "x": 180,
      "y": 90
    },
    "facing=west,half=bottom,shape=inner_left": {
      "model": $stairs_inner,
      "uvlock": true,
      "y": 90
    },
    "facing=west,half=bottom,shape=inner_right": {
      "model": $stairs_inner,
      "uvlock": true,
      "y": 180
    },
    "facing=west,half=bottom,shape=outer_left": {
      "model": $stairs_outer,
      "uvlock": true,
      "y": 90
    },
    "facing=west,half=bottom,shape=outer_right": {
      "model": $stairs_outer,
      "uvlock": true,
      "y": 180
    },
    "facing=west,half=bottom,shape=straight": {
      "model": $stairs,
      "uvlock": true,
      "y": 180
    },
    "facing=west,half=top,shape=inner_left": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180,
      "y": 180
    },
    "facing=west,half=top,shape=inner_right": {
      "model": $stairs_inner,
      "uvlock": true,
      "x": 180,
      "y": 270
    },
    "facing=west,half=top,shape=outer_left": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180,
      "y": 180
    },
    "facing=west,half=top,shape=outer_right": {
      "model": $stairs_outer,
      "uvlock": true,
      "x": 180,
      "y": 270
    },
    "facing=west,half=top,shape=straight": {
      "model": $stairs,
      "uvlock": true,
      "x": 180,
      "y": 180
    }
  }
}' > "$client_resources_PATH/assets/miencrap3minerat/blockstates/${blockname}_stairs.json"

  jq -n --arg texture "miencrap3minerat:block/${blockname}" '{
  "parent": "minecraft:block/stairs",
  "textures": {
    "bottom": $texture,
    "side": $texture,
    "top": $texture
  }
}' > "$client_resources_PATH/assets/miencrap3minerat/models/block/${blockname}_stairs.json"

  jq -n --arg texture "miencrap3minerat:block/${blockname}" '{
  "parent": "minecraft:block/inner_stairs",
  "textures": {
    "bottom": $texture,
    "side": $texture,
    "top": $texture
  }
}' > "$client_resources_PATH/assets/miencrap3minerat/models/block/${blockname}_stairs_inner.json"

  jq -n --arg texture "miencrap3minerat:block/${blockname}" '{
  "parent": "minecraft:block/outer_stairs",
  "textures": {
    "bottom": $texture,
    "side": $texture,
    "top": $texture
  }
}' > "$client_resources_PATH/assets/miencrap3minerat/models/block/${blockname}_stairs_outer.json"

  jq -n --arg model "miencrap3minerat:block/${blockname}_stairs" '{
    "model": {
      "type":"model",
      "model": $model
    }
  }' > "$client_resources_PATH/assets/miencrap3minerat/items/${blockname}_stairs.json"

  if [ "$ltable" = "1" ]; then
    jq --compact-output -n --arg item_id "miencrap3minerat:${blockname}_stairs" '{
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
    }' > "$server_resources_PATH/data/miencrap3minerat/loot_table/blocks/${blockname}_stairs.json"
  fi

  jq -n --arg original "miencrap3minerat:block/${blockname}" --arg slab "miencrap3minerat:block/${blockname}_slab" --arg slab_top "miencrap3minerat:block/${blockname}_slab_top" '
  {
  "variants": {
    "type=bottom": {
      "model": $slab
    },
    "type=double": {
      "model": $original
    },
    "type=top": {
      "model": $slab_top
    }
  }
}' > "$client_resources_PATH/assets/miencrap3minerat/blockstates/${blockname}_slab.json"


  jq -n --arg texture "miencrap3minerat:block/${blockname}" '{
  "parent": "minecraft:block/slab",
  "textures": {
    "bottom": $texture,
    "side": $texture,
    "top": $texture
  }
}' > "$client_resources_PATH/assets/miencrap3minerat/models/block/${blockname}_slab.json"


  jq -n --arg texture "miencrap3minerat:block/${blockname}" '{
  "parent": "minecraft:block/slab_top",
  "textures": {
    "bottom": $texture,
    "side": $texture,
    "top": $texture
  }
}' > "$client_resources_PATH/assets/miencrap3minerat/models/block/${blockname}_slab_top.json"


  jq -n --arg model "miencrap3minerat:block/${blockname}_slab" '{
    "model": {
      "type":"model",
      "model": $model
    }
  }' > "$client_resources_PATH/assets/miencrap3minerat/items/${blockname}_slab.json"

  if [ "$ltable" = "1" ]; then
    jq --compact-output -n --arg item_id "miencrap3minerat:${blockname}_slab" '{
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
    }' > "$server_resources_PATH/data/miencrap3minerat/loot_table/blocks/${blockname}_slab.json"
  fi
  
  upperParent=$(echo "${blockname}" | tr '[:lower:]' '[:upper:]')
  upperReg=$(echo "${blockname}_stairs" | tr '[:lower:]' '[:upper:]')
  upperSlabReg=$(echo "${blockname}_slab" | tr '[:lower:]' '[:upper:]')

#public static RegisteredBlock FALL_STAIRS = new GenericBlock("fall_stairs", p -> new StairBlock(FALL_PLANKS.block.defaultBlockState(), p), SoundType.WOOD,1.5f,2.0f).copyOf(FALL_PLANKS.block).ignitedByLava().reg();
  echo "public static RegisteredBlock $upperReg = new GenericBlock(\"${blockname}_stairs\", p -> new StairBlock(${upperParent}.block.defaultBlockState(), p), SoundType.STONE, 1.0f,1.0f).copyOf($upperParent.block).reg();"
  echo "public static RegisteredBlock $upperSlabReg = new GenericBlock(\"${blockname}_slab\", SlabBlock::new, SoundType.STONE, 1.0f,1.0f).copyOf($upperParent.block).reg();"

  # public static RegisteredBlock FALL_SLAB = new GenericBlock("fall_slab", SlabBlock::new, SoundType.WOOD,1.5f,2.0f).copyOf(FALL_PLANKS.block).ignitedByLava().reg();
done
echo "[!] now add blocks in java (if you haven't)."