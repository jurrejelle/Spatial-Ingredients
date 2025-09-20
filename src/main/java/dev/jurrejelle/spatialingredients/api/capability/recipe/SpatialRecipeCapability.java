package dev.jurrejelle.spatialingredients.api.capability.recipe;

import dev.jurrejelle.spatialingredients.api.recipe.lookup.MapSpatialIngredient;

import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;
import com.gregtechceu.gtceu.api.recipe.content.SerializerBlockState;
import com.gregtechceu.gtceu.api.recipe.lookup.ingredient.AbstractMapIngredient;

import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.world.level.block.state.BlockState;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class SpatialRecipeCapability extends RecipeCapability<BlockState> {

    public final static SpatialRecipeCapability CAP = new SpatialRecipeCapability();

    protected SpatialRecipeCapability() {
        super("spatial", 0x5E2129FF, false, 5, SerializerBlockState.INSTANCE);
    }

    @Override
    public BlockState copyInner(BlockState content) {
        return content;
    }

    @Override
    public @Nullable List<AbstractMapIngredient> getDefaultMapIngredient(Object ingredient) {
        List<AbstractMapIngredient> ingredients = new ObjectArrayList<>(1);
        if (ingredient instanceof BlockState block) ingredients.add(new MapSpatialIngredient(block));
        return ingredients;
    }

    @Override
    public List<Object> compressIngredients(Collection<Object> ingredients) {
        // TODO: This doesn't make sense, max 1 hatch per machine
        return super.compressIngredients(ingredients);
    }

    @Override
    public boolean isRecipeSearchFilter() {
        return true;
    }

    @Override
    public void addXEIInfo(WidgetGroup group, int xOffset, GTRecipe recipe, List<Content> contents, boolean perTick,
                           boolean isInput, MutableInt yOffset) {
        for (var stack : contents) {
            // TODO: fix this when proper implemented
            // spotless: off
            /*
             * var sterileIngredient = BlockStateRecipeCapability.CAP.of(stack.getContent());
             * if (isInput) {
             * group.addWidget(new LabelWidget(3 - xOffset, yOffset.addAndGet(10),
             * LocalizationUtils.format("cosmiccore.recipe.sterile_in",
             * sterileIngredient.getStacks()[0].getDisplayName().getString(),
             * sterileIngredient.getStacks()[0].getAmount() + (perTick ? "/t" : ""))));
             * } else {
             * group.addWidget(new LabelWidget(3 - xOffset, yOffset.addAndGet(10),
             * LocalizationUtils.format("cosmiccore.recipe.sterile_out",
             * sterileIngredient.getStacks()[0].getDisplayName().getString(),
             * sterileIngredient.getStacks()[0].getAmount() + (perTick ? "/t" : ""))));
             * }
             */
            // spotless: on
        }
    }
}
