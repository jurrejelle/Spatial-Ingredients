package dev.jurrejelle.spatialingredients.api.machine.trait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.ICapabilityTrait;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import dev.jurrejelle.spatialingredients.api.capability.recipe.SpatialRecipeCapability;
import dev.jurrejelle.spatialingredients.api.recipe.ingredient.SpatialIngredient;
import dev.jurrejelle.spatialingredients.common.machine.multiblock.part.SpatialHatchPartMachine;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotifiableSpatialHatch extends NotifiableRecipeHandlerTrait<SpatialIngredient>
        implements ICapabilityTrait {

    @Getter
    public final IO handlerIO;
    @Getter
    public final IO capabilityIO;

    public NotifiableSpatialHatch(MetaMachine machine, IO io) {
        this(machine, io, io);
    }

    public NotifiableSpatialHatch(MetaMachine machine, IO handlerIO, IO capabilityIO) {
        super(machine);
        this.handlerIO = handlerIO;
        this.capabilityIO = capabilityIO;

    }

    @Override
    public SpatialHatchPartMachine getMachine() {
        return (SpatialHatchPartMachine) super.getMachine();
    }

    private SpatialIngredient getBlock() {
        Level level = getMachine().getHolder().level();
        if (level == null) {
            return SpatialIngredient.EMPTY;
        }
        BlockPos hatchPos = getMachine().getHolder().getCurrentPos();
        BlockPos inFront = hatchPos.relative(getMachine().getFrontFacing()).offset(getMachine().getOffset());
        return new SpatialIngredient(level.getBlockState(inFront));
    }

    private boolean consume() {
        Level level = getMachine().getHolder().level();
        BlockPos hatchPos = getMachine().getHolder().getCurrentPos();
        BlockPos inFront = hatchPos.relative(getMachine().getFrontFacing());
        return level.setBlockAndUpdate(inFront, Blocks.AIR.defaultBlockState());
    }

    @Override
    public List<SpatialIngredient> handleRecipeInner(IO io, GTRecipe recipe, List<SpatialIngredient> left, boolean simulate) {
        SpatialIngredient block = getBlock();
        for (int i = 0; i < left.size(); i++) {
            SpatialIngredient recipeBlock = left.get(i);
            if (recipeBlock.equals(block)) {
                if (simulate || consume()) {
                    left.remove(i);
                    break;
                }
            }
        }
        return left.isEmpty() ? null : left;
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
    public RecipeCapability<SpatialIngredient> getCapability() {
        return SpatialRecipeCapability.CAP;
    }
}
