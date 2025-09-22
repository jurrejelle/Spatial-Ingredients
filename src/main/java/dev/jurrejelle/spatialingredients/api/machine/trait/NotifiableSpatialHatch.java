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
import net.minecraft.core.Vec3i;
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
        BlockPos inFront = hatchPos.relative(getMachine().getFrontFacing()).offset(getMachine().getOffset());
        return level.setBlockAndUpdate(inFront, Blocks.AIR.defaultBlockState());
    }

    private boolean place(SpatialIngredient ingredient) {
        Level level = getMachine().getHolder().level();
        BlockPos hatchPos = getMachine().getHolder().getCurrentPos();
        BlockPos inFront = hatchPos.relative(getMachine().getFrontFacing()).offset(getMachine().getOffset());
        return ingredient.place(level, inFront);
    }

    private boolean canPlace() {
        Level level = getMachine().getHolder().level();
        BlockPos hatchPos = getMachine().getHolder().getCurrentPos();
        BlockPos inFront = hatchPos.relative(getMachine().getFrontFacing()).offset(getMachine().getOffset());
        Vec3i size = getMachine().getSize();
        for (int x = 0; x < size.getX(); x++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int z = 0; z < size.getZ(); z++) {
                    if (!level.getBlockState(inFront.offset(x, y, z)).canBeReplaced()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public List<SpatialIngredient> handleRecipeInner(IO io, GTRecipe recipe, List<SpatialIngredient> left, boolean simulate) {
        if (io.support(IO.IN)) {
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
        }
        if (io.support(IO.OUT) && canPlace()) {
            for (int i = 0; i < left.size(); i++) {
                SpatialIngredient recipeBlock = left.get(i);
                if (supports(recipeBlock.getSize())) {
                    if (simulate || place(recipeBlock)) {
                        left.remove(i);
                        break;
                    }
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

    public boolean supports(Vec3i size) {
        Vec3i thisSize = this.getMachine().getSize();
        return thisSize.getX() >= size.getX() && thisSize.getY() >= size.getY() && thisSize.getZ() >= size.getZ();
    }
}
