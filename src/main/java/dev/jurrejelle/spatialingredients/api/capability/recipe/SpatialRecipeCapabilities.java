package dev.jurrejelle.spatialingredients.api.capability.recipe;

import com.gregtechceu.gtceu.api.registry.GTRegistries;

public class SpatialRecipeCapabilities {

    public static final SpatialRecipeCapability SPATIAL = SpatialRecipeCapability.CAP;

    public static void init() {
        GTRegistries.RECIPE_CAPABILITIES.register(SPATIAL.name, SPATIAL);
        // Potentially update this to let KubeJS understand our inputs/outputs when adding to recipe
        // VALID_CAPS.put(SPATIAL, Pair.of(FLUID_IN, FLUID_OUT));
    }
}
