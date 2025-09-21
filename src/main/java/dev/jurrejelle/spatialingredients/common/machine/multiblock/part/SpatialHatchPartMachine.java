package dev.jurrejelle.spatialingredients.common.machine.multiblock.part;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;

import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.TextFieldWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import dev.jurrejelle.spatialingredients.api.machine.trait.NotifiableSpatialHatch;
import lombok.Getter;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpatialHatchPartMachine extends TieredIOPartMachine {

    public static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(SpatialHatchPartMachine.class, TieredIOPartMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    @Getter
    private final NotifiableSpatialHatch spatialHatch;

    @Getter
    @Persisted
    private Vec3i offset;

    @Getter
    @Persisted
    private Vec3i size;

    public SpatialHatchPartMachine(IMachineBlockEntity holder, int tier, IO io) {
        super(holder, tier, io);
        spatialHatch = new NotifiableSpatialHatch(this, io);
    }

    public int getMaxOffset() {
        return (int) Math.pow(2, getTier()/2d);
    }

    public int getMaxSize() {
        return getTier();
    }

    public void setOffset(Vec3i newOffset) {
        offset = clamp(newOffset, -getMaxOffset(), getMaxOffset());
    }

    public void setSize(Vec3i newSize) {
        size = clamp(newSize, 1, getMaxSize());
    }

    private Vec3i clamp(Vec3i vec, int min, int max) {
        return new Vec3i(
                Mth.clamp(vec.getX(), min, max),
                Mth.clamp(vec.getY(), min, max),
                Mth.clamp(vec.getZ(), min, max)
        );
    }

    // GUI
    @Override
    public ModularUI createUI(Player entityPlayer) {
        var group = new WidgetGroup(0, 0, 176, 164);
        group.addWidget(new LabelWidget(5, 5, "spatialingredients.gui.spatial_hatch"));
        group.addWidget(vecInput(5, 20, this::setOffset, this::getOffset, Component.translatable("spatialingredients.gui.spatial_hatch.offset")));
        group.addWidget(vecInput(5, 60, this::setSize, this::getSize, Component.translatable("spatialingredients.gui.spatial_hatch.size")));
        return new ModularUI(176, 164, this, entityPlayer)
                .background(GuiTextures.BACKGROUND)
                .widget(group)
                .widget(UITemplate.bindPlayerInventory(entityPlayer.getInventory(), GuiTextures.SLOT, 7, 84, true));
    }

    private WidgetGroup vecInput(int posX, int posY, Consumer<Vec3i> setter, Supplier<Vec3i> getter, Component name) {
        WidgetGroup group = new WidgetGroup();
        group.setSelfPosition(posX, posY);
        group.addWidget(new LabelWidget(0, 0, name));
        group.addWidget(new TextFieldWidget(0, 20, 30, 20, () -> String.valueOf(getter.get().getX()), x -> {
            Vec3i vec = getter.get();
            try {
                setter.accept(new Vec3i(Integer.parseInt(x), vec.getY(), vec.getZ()));
            } catch (NumberFormatException ignored) {}
        }));
        group.addWidget(new TextFieldWidget(60, 20, 30, 20, () -> String.valueOf(getter.get().getX()), x -> {
            Vec3i vec = getter.get();
            try {
                setter.accept(new Vec3i(vec.getX(), Integer.parseInt(x), vec.getZ()));
            } catch (NumberFormatException ignored) {}
        }));
        group.addWidget(new TextFieldWidget(90, 20, 30, 20, () -> String.valueOf(getter.get().getX()), x -> {
            Vec3i vec = getter.get();
            try {
                setter.accept(new Vec3i(vec.getX(), Integer.parseInt(x), vec.getZ()));
            } catch (NumberFormatException ignored) {}
        }));
        return group;
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
