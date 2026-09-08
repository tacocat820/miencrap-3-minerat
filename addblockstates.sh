blockstates_type=none
echo "===== ? ====="
echo "-h    =   HorizontallyRotatingBlock"
echo "-p    =   RotatedPillarBlock"
echo "===== ? ====="

while getopts "h p" opt; do
  case ${opt} in
    h )
      blockstates_type=horizontally
      ;;
    p )
      blockstates_type=pillar
      ;;
    \? )
      exit 1
      ;;
  esac
done
echo "YOUR CHOICE: $blockstates_type"

client_resources_PATH="src/client/resources"
server_resources_PATH="src/main/resources"

read -r -p "> Enter block(s), space as splitter: " blocks_names

for blockname in $blocks_names; do
    if [ "$blockstates_type" = "horizontally" ]; then
        jq -n --arg model "miencrap3minerat:block/$blockname" '{
            "variants": {
                "facing=north": {
                "model": $model
                },
                "facing=south": {
                "model": $model,
                "y": 180
                },
                "facing=east": {
                "model": $model,
                "y": 90
                },
                "facing=west": {
                "model": $model,
                "y": 270
                }
            }
        }' > "$client_resources_PATH/assets/miencrap3minerat/blockstates/$blockname.json"
    fi

    if [ "$blockstates_type" = "pillar" ]; then
        jq -n --arg model "miencrap3minerat:block/$blockname" '{
            "variants": {
                "axis=y": {
                "model": $model
                },
                "axis=z": {
                "model": $model,
                "x": 90
                },
                "axis=x": {
                "model": $model,
                "z": 90
                }
            }
        }' > "$client_resources_PATH/assets/miencrap3minerat/blockstates/$blockname.json"
    fi
done