package dev.jurrejelle.spatialingredients.api.recipe.lookup;

import com.gregtechceu.gtceu.api.recipe.lookup.ingredient.AbstractMapIngredient;

import net.minecraft.world.level.block.state.BlockState;

import java.util.Collections;
import java.util.List;

public class MapSpatialIngredient extends AbstractMapIngredient {

    public final BlockState block;

    public MapSpatialIngredient(BlockState block) {
        this.block = block;
    }

    @Override
    protected int hash() {
        return MapSpatialIngredient.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MapSpatialIngredient other)) return false;
        return other.block.equals(this.block);
    }

    @Override
    public String toString() {
        return "MapSpatialIngredient{" + "block=" + block + '}';
    }

    public static List<AbstractMapIngredient> convertToMapIngredient(BlockState block) {
        return Collections.singletonList(new MapSpatialIngredient(block));
    }
}
