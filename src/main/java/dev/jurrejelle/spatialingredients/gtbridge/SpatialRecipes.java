package dev.jurrejelle.spatialingredients.gtbridge;

import dev.jurrejelle.spatialingredients.SpatialIngredients;
import dev.jurrejelle.spatialingredients.api.capability.recipe.SpatialRecipeCapability;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import dev.jurrejelle.spatialingredients.api.recipe.ingredient.SpatialIngredient;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static dev.jurrejelle.spatialingredients.gtbridge.SpatialRecipeTypes.LARGE_SPATIAL_RECIPES;

public class SpatialRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        LARGE_SPATIAL_RECIPES.recipeBuilder(
                SpatialIngredients.id("test"))
                .inputItems(Items.STONE)
                .input(SpatialRecipeCapability.CAP, new SpatialIngredient(Blocks.DIRT.defaultBlockState()))
                .outputItems(Items.COBBLESTONE)
                .duration(100)
                .EUt(GTValues.VA[GTValues.LV])
                .save(provider);
    }
}
