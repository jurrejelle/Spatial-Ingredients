package dev.jurrejelle.spatialingredients.common.machine.multiblock.part;

import dev.jurrejelle.spatialingredients.api.capability.recipe.SpatialRecipeCapability;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.gui.UITemplate;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IUIMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;

import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpatialHatchPartMachine extends TieredIOPartMachine
                                     implements IRecipeHandler<BlockState>, IUIMachine {

    public SpatialHatchPartMachine(IMachineBlockEntity holder, int tier, IO io) {
        super(holder, tier, io);
    }

    private BlockState getBlock() {
        Level level = getHolder().level();
        BlockPos hatchPos = getHolder().getCurrentPos();
        BlockPos inFront = hatchPos.relative(this.getFrontFacing());
        return level.getBlockState(inFront);
    }

    private boolean consume() {
        Level level = getHolder().level();
        BlockPos hatchPos = getHolder().getCurrentPos();
        BlockPos inFront = hatchPos.relative(this.getFrontFacing());
        return level.setBlockAndUpdate(inFront, Blocks.AIR.defaultBlockState());
    }

    @Override
    public List<BlockState> handleRecipeInner(IO io, GTRecipe recipe, List<BlockState> left, boolean simulate) {
        BlockState block = getBlock();
        for (int i = 0; i < left.size(); i++) {
            BlockState recipeBlock = left.get(i);
            if (recipeBlock.equals(block)) {
                if (simulate || consume()) {
                    left.remove(i);
                    break;
                }
            }
        }
        return left;
    }

    @Override
    public @NotNull List<Object> getContents() {
        return List.of(getBlock());
    }

    @Override
    public double getTotalContentAmount() {
        return 1;
    }

    @Override
    public RecipeCapability<BlockState> getCapability() {
        return SpatialRecipeCapability.CAP;
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
