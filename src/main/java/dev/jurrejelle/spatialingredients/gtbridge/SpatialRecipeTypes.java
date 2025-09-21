package dev.jurrejelle.spatialingredients.gtbridge;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import dev.jurrejelle.spatialingredients.api.capability.recipe.SpatialRecipeCapability;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.MULTIBLOCK;

public class SpatialRecipeTypes {

    public static final GTRecipeType LARGE_SPATIAL_RECIPES = register("large_spatial_reactor", MULTIBLOCK)
            .setMaxIOSize(3, 3, 5, 4)
            // TODO: change this?
            .setMaxSize(IO.IN, SpatialRecipeCapability.CAP, 4)
            .setMaxSize(IO.OUT, SpatialRecipeCapability.CAP, 4)
            .setEUIO(IO.IN);

    public static void init() {}
    public static GTRecipeType register(String name, String group, RecipeType<?>... proxyRecipes) {
        var recipeType = new GTRecipeType(GTCEu.id(name), group, proxyRecipes);
        GTRegistries.register(BuiltInRegistries.RECIPE_TYPE, recipeType.registryName, recipeType);
        GTRegistries.register(BuiltInRegistries.RECIPE_SERIALIZER, recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }

}
