package io.github.pirgosth.skyshop.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class ItemConfig implements ConfigurationSerializable {

    public String name;
    public String material;
    public int x;
    public int y;
    public float buy;
    public float sell;

    public ItemConfig(String name, String material, int x, int y, float buy, float sell) {
        this.name = name;
        this.material = material;
        this.x = x;
        this.y = y;
        this.buy = buy;
        this.sell = sell;
    }

    public ItemConfig(Map<String, Object> map) {
        this(
                (map.get("name") instanceof String) ? (String) map.get("name") : "",
                (map.get("material") instanceof String) ? (String) map.get("material") : "",
                (map.get("x") instanceof Integer) ? (Integer) map.get("x") : -1,
                (map.get("y") instanceof Integer) ? (Integer) map.get("y") : -1,
                (map.get("buy") instanceof Double) ? ((Double)map.get("buy")).floatValue() : 0f,
                (map.get("sell") instanceof Double) ? ((Double)map.get("sell")).floatValue() : 0f
        );
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new LinkedHashMap<>();
        serialized.put("name", this.name);
        serialized.put("material", this.material);
        serialized.put("x", this.x);
        serialized.put("y", this.y);
        serialized.put("buy", this.buy);
        serialized.put("sell", this.sell);
        return serialized;
    }
}
