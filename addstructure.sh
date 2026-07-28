read -r -p "> Enter structures(s), space as splitter: " structure_names

resources_PATH="src/main/resources"

for structurename in $structure_names; do

	read -p "enter biomes for $structurename (with space as splitter) : " biomes
	read -p "enter separation for $structurename : " separation
	read -p "enter spacing for $structurename : " spacing

	biomes_json=$(echo "$biomes" | jq -R 'split(" ") | map(select(length > 0))')

  jq -n --arg n "miencrap3minerat:$structurename" --argjson biomes "$biomes_json" '{
      "type": "minecraft:jigsaw",
      "biomes": $biomes,
      "step": "surface_structures",
      "spawn_overrides": {},
      "terrain_adaptation": "beard_thin",
      "start_pool": $n,
      "size": 1,
      "start_height": {
        "absolute": 0
      },
      "project_start_to_heightmap": "WORLD_SURFACE_WG",
      "max_distance_from_center": 40,
      "use_expansion_hack": false
    }' > "$resources_PATH/data/miencrap3minerat/worldgen/structure/$structurename.json"

    jq -n --arg n "miencrap3minerat:$structurename" --argjson salt "$((10000000 + $RANDOM % 89999999))" --argjson separation "$separation" --argjson spacing "$spacing" '{
      "structures": [
        {
          "structure": $n,
          "weight": 1
        }
      ],
      "placement": {
        "type": "minecraft:random_spread",
        "salt": $salt,
        "spacing": $spacing,
        "separation": $separation
      }
    }
    ' > "$resources_PATH/data/miencrap3minerat/worldgen/structure_set/$structurename.json"

  jq -n --arg n "miencrap3minerat:$structurename" '{
      "name": $n,
      "fallback": "minecraft:empty",
      "elements": [
        {
          "weight": 1,
          "element": {
            "element_type": "minecraft:single_pool_element",
            "location": $n,
            "projection": "rigid",
            "processors": "minecraft:empty"
          }
        }
      ]
    }' > "$resources_PATH/data/miencrap3minerat/worldgen/template_pool/$structurename.json"
    
  echo "yay: $structurename"
done
echo "yay"
