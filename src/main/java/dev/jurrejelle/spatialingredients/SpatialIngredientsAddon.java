package dev.jurrejelle.spatialingredients;

import com.gregtechceu.gtceu.api.recipe.lookup.ingredient.MapIngredientTypeManager;
import dev.jurrejelle.spatialingredients.api.capability.recipe.SpatialRecipeCapabilities;
import dev.jurrejelle.spatialingredients.api.recipe.lookup.MapSpatialIngredient;
import dev.jurrejelle.spatialingredients.gtbridge.SpatialRecipes;

import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;

@SuppressWarnings("unused")
@GTAddon
public class SpatialIngredientsAddon implements IGTAddon {

    @Override
    public GTRegistrate getRegistrate() {
        return SpatialIngredients.REGISTRATE;
    }

    @Override
    public void initializeAddon() {
        MapIngredientTypeManager.registerMapIngredient(BlockState.class, MapSpatialIngredient::convertToMapIngredient);
    }

    @Override
    public String addonModId() {
        return SpatialIngredients.MOD_ID;
    }

    @Override
    public void registerTagPrefixes() {
        // CustomTagPrefixes.init();
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        SpatialRecipes.init(provider);
    }

    @Override
    public void registerRecipeCapabilities() {
        SpatialRecipeCapabilities.init();
    }

    // If you have custom ingredient types, uncomment this & change to match your capability.
    // KubeJS WILL REMOVE YOUR RECIPES IF THESE ARE NOT REGISTERED.
    /*
     * public static final ContentJS<Double> PRESSURE_IN = new ContentJS<>(NumberComponent.ANY_DOUBLE,
     * CustomRecipeCapabilities.PRESSURE, false);
     * public static final ContentJS<Double> PRESSURE_OUT = new ContentJS<>(NumberComponent.ANY_DOUBLE,
     * CustomRecipeCapabilities.PRESSURE, true);
     * 
     * @Override
     * public void registerRecipeKeys(KJSRecipeKeyEvent event) {
     * event.registerKey(CustomRecipeCapabilities.PRESSURE, Pair.of(PRESSURE_IN, PRESSURE_OUT));
     * }
     */
}
