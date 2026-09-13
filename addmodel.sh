echo "[!] -i for item, -b for block"
while getopts "i b" opt; do
  case ${opt} in
    i )
      echo "| Item models."
      modeltype="item"
      ;;
    b )
      echo "| Block models."
      modeltype="block"
      ;;
    \? )
      modeltype="none"
      exit 1
      ;;
  esac
done

resources_PATH="src/client/resources"

read -r -p "> Enter texture name(s) for ${modeltype}(s), space as splitter : " textures_names

for tex_name in $textures_names; do
  if [ "$modeltype" = "item" ]; then
    jq -n --arg texture "miencrap3minerat:$modeltype/$tex_name" '{
      "parent": "item/generated",
      "textures": {
        "layer0": $texture
      }
    }' > "$resources_PATH/assets/miencrap3minerat/models/item/$tex_name.json"
  fi
  if [ "$modeltype" = "block" ]; then
    jq -n --arg texture "miencrap3minerat:$modeltype/$tex_name" '{
      "parent": "block/cube_all",
      "textures": {
      "all": $texture
      }
    }' > "$resources_PATH/assets/miencrap3minerat/models/block/$tex_name.json"
  fi
done
