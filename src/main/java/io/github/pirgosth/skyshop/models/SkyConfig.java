package io.github.pirgosth.skyshop.models;

import io.github.pirgosth.skyshop.SkyShop;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SkyConfig implements ConfigurationSerializable {

    public List<String> enabledWorlds;
    public ShopSection shopSection;
    private static SkyConfig instance = null;

    public SkyConfig(List<String> enabledWorlds, ShopSection shopSection) {
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

    public SkyConfig(Map<String, Object> map) {
        this(
                (map.get("enabled-worlds") instanceof List<?>) ? SkyConfig.safetyWorldsCast((List<?>) map.get("enabled-worlds")) : new ArrayList<>(),
                new ShopSection(map.get("shop") instanceof MemorySection ? ((MemorySection) map.get("shop")).getValues(true) : new HashMap<>())
        );
    }

    private static List<String> safetyWorldsCast(List<?> rawWorlds) {
        List<String> worlds = new ArrayList<>();

        for (Object obj : rawWorlds) {
            if (obj instanceof String) worlds.add((String) obj);
        }

        return worlds;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new LinkedHashMap<>();
        serialized.put("enabled-worlds", this.enabledWorlds);
        serialized.put("shop", this.shopSection.serialize());
        return serialized;
    }
}
