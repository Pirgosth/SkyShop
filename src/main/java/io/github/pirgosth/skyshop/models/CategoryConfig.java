package io.github.pirgosth.skyshop.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CategoryConfig implements ConfigurationSerializable {

    public String name;
    public String description;
    public String material;
    public int x;
    public int y;
    public List<ItemConfig> items;

    public CategoryConfig(String name, String description, String material, int x, int y, List<ItemConfig> items) {
        this.name = name;
        this.description = description;
        this.material = material;
        this.x = x;
        this.y = y;
        this.items = items;
    }

    public CategoryConfig(Map<String, Object> map) {
        this(
                (map.get("name") instanceof String) ? (String) map.get("name") : "",
                (map.get("description") instanceof String) ? (String) map.get("description") : "",
                (map.get("material") instanceof String) ? (String) map.get("material") : "",
                (map.get("x") instanceof Integer) ? (Integer) map.get("x") : -1,
                (map.get("y") instanceof Integer) ? (Integer) map.get("y") : -1,
                List.class.isAssignableFrom(map.get("items").getClass()) ? CategoryConfig.safetyItemsCast((List<?>) map.get("items")) : new ArrayList<>()
        );
    }

    private static List<ItemConfig> safetyItemsCast(List<?> rawItems) {
        List<ItemConfig> items = new ArrayList<>();

        for (Object obj : rawItems) {
            if (Map.class.isAssignableFrom(obj.getClass())) {
                Map<String, Object> values = new LinkedHashMap<>();
                for(Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                    if(entry.getKey() instanceof String) values.put((String) entry.getKey(), entry.getValue());
                }

                items.add(new ItemConfig(values));
            }
        }

        return items;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new LinkedHashMap<>();
        serialized.put("name", this.name);
        serialized.put("description", this.description);
        serialized.put("material", this.material);
        serialized.put("x", this.x);
        serialized.put("y", this.y);
        List<Map<String, Object>> serializedItems = new ArrayList<>();
        for(ItemConfig item : this.items) serializedItems.add(item.serialize());
        serialized.put("items", serializedItems);
        return serialized;
    }

    public InsertionResult addItem(@NotNull ItemConfig item) {
        if(this.getItemAt(item.x, item.y) != null) return InsertionResult.DUPLICATE_POSITION;
        this.items.add(item);
        return InsertionResult.SUCCESS;
    }

    @Nullable
    public ItemConfig getItem(@NotNull String name) {
        for(ItemConfig item : this.items) {
            if (item.name.equals(name)) return item;
        }
        return null;
    }

    @Nullable
    public ItemConfig getItemAt(int x, int y) {
        for(ItemConfig item : this.items) {
            if (item.x == x && item.y == y) return item;
        }
        return null;
    }

    public boolean removeItem(@NotNull ItemConfig item) {
        return this.items.remove(item);
    }

}
