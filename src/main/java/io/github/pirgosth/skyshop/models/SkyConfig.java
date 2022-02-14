package io.github.pirgosth.skyshop.models;

import io.github.pirgosth.liberty.core.api.utils.SerializationUtils;
import io.github.pirgosth.skyshop.SkyShop;
import lombok.Getter;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SkyConfig implements ConfigurationSerializable {

    @Getter
    private final List<WorldConfig> enabledWorlds;
    @Getter
    private final ShopSection shopSection;
    private static SkyConfig instance = null;

    public SkyConfig(List<WorldConfig> enabledWorlds, ShopSection shopSection) {
        this.enabledWorlds = enabledWorlds;
        this.shopSection = shopSection;
    }

    public static SkyConfig getConfiguration() {
        if (instance == null) SkyConfig.reloadConfiguration();
        return instance;
    }

    public static void reloadConfiguration() {
        instance = new SkyConfig(SkyShop.getInstance().getConfig().getValues(true));
    }

    public void save() {
        for (Map.Entry<String, Object> entry : this.serialize().entrySet()) {
            SkyShop.getInstance().getConfig().set(entry.getKey(), entry.getValue());
        }
        SkyShop.getInstance().saveConfig();
    }

    public boolean isWorldEnabled(String worldName) {
        for (WorldConfig worldConfig : this.enabledWorlds) {
            if (worldConfig.getWorldName().equals(worldName)) return true;
        }
        return false;
    }

    public boolean areNPCTradesDisabled(String worldName) {
        for (WorldConfig worldConfig : this.enabledWorlds) {
            if (worldConfig.getWorldName().equals(worldName) && worldConfig.isDisableNPCTrades()) return true;
        }
        return false;
    }

    public SkyConfig(Map<String, Object> map) {
        this(
                (map.get("enabled-worlds") instanceof List<?>) ? SerializationUtils.safeListCast(WorldConfig.class, (List<?>) map.get("enabled-worlds")) : new ArrayList<>(),
                new ShopSection(map.get("shop") instanceof MemorySection ? ((MemorySection) map.get("shop")).getValues(true) : new HashMap<>())
        );
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new LinkedHashMap<>();
        List<Map<String, Object>> serializedWorlds = new ArrayList<>();
        for(WorldConfig worldConfig : this.enabledWorlds) serializedWorlds.add(worldConfig.serialize());
        serialized.put("enabled-worlds", serializedWorlds);
        serialized.put("shop", this.shopSection.serialize());
        return serialized;
    }
}
