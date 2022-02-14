package io.github.pirgosth.skyshop.models;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorldConfig implements ConfigurationSerializable {
    @Getter
    private final String worldName;
    @Getter
    private final boolean disableNPCTrades;

    public WorldConfig(String worldName, boolean disableNPCTrades) {
        this.worldName = worldName;
        this.disableNPCTrades = disableNPCTrades;
    }

    public WorldConfig(Map<String, Object> map) {
        this(
                map.get("name") instanceof String ? (String) map.get("name") : "",
                map.get("disable-npc-trades") instanceof Boolean ? (Boolean) map.get("disable-npc-trades") : false
        );
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", this.worldName);
        map.put("disable-npc-trades", this.disableNPCTrades);
        return map;
    }
}
