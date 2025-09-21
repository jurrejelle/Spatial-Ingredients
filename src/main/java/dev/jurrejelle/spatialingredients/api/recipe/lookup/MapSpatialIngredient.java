package dev.jurrejelle.spatialingredients.api.recipe.lookup;

import com.gregtechceu.gtceu.api.recipe.lookup.ingredient.AbstractMapIngredient;

import dev.jurrejelle.spatialingredients.api.recipe.ingredient.SpatialIngredient;

import java.util.Collections;
import java.util.List;

public class MapSpatialIngredient extends AbstractMapIngredient {

    public final SpatialIngredient ingredient;

    public MapSpatialIngredient(SpatialIngredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    protected int hash() {
        return MapSpatialIngredient.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapSpatialIngredient other)) return false;
        return other.ingredient.equals(this.ingredient);
    }

    @Override
    public String toString() {
        return "MapSpatialIngredient{" + "block=" + ingredient + '}';
    }

    public static List<AbstractMapIngredient> convertToMapIngredient(SpatialIngredient ingredient) {
        return Collections.singletonList(new MapSpatialIngredient(ingredient));
    }
}
