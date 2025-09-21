package dev.jurrejelle.spatialingredients.common.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class SpatialLangHandler {

    public static void init(RegistrateLangProvider provider) {
        provider.add("spatialingredients.gui.spatial_hatch", "Spatial Hatch");
        provider.add("spatialingredients.gui.spatial_hatch.offset", "Offset:");
        provider.add("spatialingredients.gui.spatial_hatch.size", "Size:");
    }
}
