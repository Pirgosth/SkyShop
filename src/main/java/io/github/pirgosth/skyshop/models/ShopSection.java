package io.github.pirgosth.skyshop.models;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShopSection implements ConfigurationSerializable {

    public List<CategoryConfig> categories;

    public ShopSection(List<CategoryConfig> categories) {
        this.categories = categories;
    }

    public ShopSection(Map<String, Object> map) {
        this(
                List.class.isAssignableFrom(map.get("categories").getClass()) ? ShopSection.safetyCategoriesCast((List<?>) map.get("categories")) : new ArrayList<>()
        );
    }

    private static List<CategoryConfig> safetyCategoriesCast(List<?> rawCategories) {
        List<CategoryConfig> categories = new ArrayList<>();

        for (Object obj : rawCategories) {
            if (Map.class.isAssignableFrom(obj.getClass())) {
                Map<String, Object> values = new LinkedHashMap<>();
                for (Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                    if (entry.getKey() instanceof String) values.put((String) entry.getKey(), entry.getValue());
                }

                categories.add(new CategoryConfig(values));
            }
        }

        return categories;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> serialized = new LinkedHashMap<>();
        List<Map<String, Object>> serializedCategories = new ArrayList<>();
        for (CategoryConfig cat : this.categories) serializedCategories.add(cat.serialize());
        serialized.put("categories", serializedCategories);
        return serialized;
    }

    public InsertionResult addCategory(@NotNull CategoryConfig category) {
        if (this.getCategoryAt(category.x, category.y) != null) return InsertionResult.DUPLICATE_POSITION;
        this.categories.add(category);
        return InsertionResult.SUCCESS;
    }

    @Nullable
    public CategoryConfig getCategory(@NotNull String name) {
        for (CategoryConfig cat : this.categories) {
            if (cat.name.equals(name)) return cat;
        }

        return null;
    }

    @Nullable
    public CategoryConfig getCategoryAt(int x, int y) {
        for (CategoryConfig cat : this.categories) {
            if (cat.x == x && cat.y == y) return cat;
        }

        return null;
    }

    public boolean removeCategory(@NotNull CategoryConfig category) {
        return this.categories.remove(category);
    }
}
