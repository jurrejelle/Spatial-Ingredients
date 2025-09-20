package dev.jurrejelle.spatialingredients.gtbridge;

import com.gregtechceu.gtceu.api.capability.recipe.BlockStateRecipeCapability;
import dev.jurrejelle.spatialingredients.SpatialIngredients;
import dev.jurrejelle.spatialingredients.api.capability.recipe.SpatialRecipeCapability;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class SpatialRecipes {

    public static void init(Consumer<FinishedRecipe> provider) {
        GTRecipeTypes.LARGE_CHEMICAL_RECIPES.recipeBuilder(
                SpatialIngredients.id("test"))
                .inputItems(Items.STONE)
                .input(BlockStateRecipeCapability.CAP, Blocks.DIRT.defaultBlockState())
                .outputItems(Items.COBBLESTONE)
                .duration(100)
                .EUt(GTValues.VA[GTValues.LV])
                .save(provider);
    }
}
