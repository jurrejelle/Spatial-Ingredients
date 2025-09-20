package dev.jurrejelle.spatialingredients.common.data;

import dev.jurrejelle.spatialingredients.SpatialIngredients;
import dev.jurrejelle.spatialingredients.common.data.lang.SpatialLangHandler;

import com.tterrag.registrate.providers.ProviderType;

public class SpatialDatagen {

    public static void init() {
        SpatialIngredients.REGISTRATE.addDataGenerator(ProviderType.LANG, SpatialLangHandler::init);
    }
}
