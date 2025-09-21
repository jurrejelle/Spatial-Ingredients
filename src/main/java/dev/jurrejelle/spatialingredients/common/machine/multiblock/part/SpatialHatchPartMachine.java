package dev.jurrejelle.spatialingredients.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;

import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import dev.jurrejelle.spatialingredients.api.machine.trait.NotifiableSpatialHatch;
import net.minecraft.world.entity.player.Player;

public class SpatialHatchPartMachine extends TieredIOPartMachine {

    public NotifiableSpatialHatch spatialHatch;

    public SpatialHatchPartMachine(IMachineBlockEntity holder, int tier, IO io) {
        super(holder, tier, io);
        spatialHatch = new NotifiableSpatialHatch(this, io);
    }

    // GUI
    @Override
    public ModularUI createUI(Player entityPlayer) {
        var group = new WidgetGroup(0, 0, 176, 164);
        group.addWidget(new LabelWidget(5, 5, "spatialingredients.gui.spatial_hatch"));
        return new ModularUI(176, 164, this, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(group)
                .widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 84, true));
    }
}
