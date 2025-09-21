package dev.jurrejelle.spatialingredients.api.recipe.ingredient;

import com.gregtechceu.gtceu.api.recipe.content.IContentSerializer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.jurrejelle.spatialingredients.SpatialIngredients;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
public class SpatialIngredient {
    public static final SpatialIngredient EMPTY = new SpatialIngredient(Blocks.AIR.defaultBlockState());

    public static final Codec<SpatialIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("blockstate").forGetter(SpatialIngredient::getBlockstate)
    ).apply(instance, SpatialIngredient::new));

    private BlockState getBlockstate() {
        if(blockstate == null){
            SpatialIngredients.LOGGER.error("Tried to get null blockstate!");
        }
        return blockstate;
    }

    private final BlockState blockstate;

    public SpatialIngredient(BlockState blockstate) {
        if(blockstate == null){
            SpatialIngredients.LOGGER.error("SpatialIngredient should never be initialized with null blockstate");
            this.blockstate = Blocks.AIR.defaultBlockState();
            return;
        }
        this.blockstate = blockstate;
    }

    public SpatialIngredient copy() {
        Tag tag = CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow(false, SpatialIngredients.LOGGER::error);
        return CODEC.decode(NbtOps.INSTANCE, tag).getOrThrow(false, SpatialIngredients.LOGGER::error).getFirst();
    }

    public static final class Serializer implements IContentSerializer<SpatialIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public SpatialIngredient of(Object o) {
            if (o instanceof BlockState state) {
                return new SpatialIngredient(state);
            } else if (o instanceof SpatialIngredient spatialIngredient) {
                return spatialIngredient;
            }
            return SpatialIngredient.EMPTY;
        }

        @Override
        public SpatialIngredient defaultValue() {
            return SpatialIngredient.EMPTY;
        }

        @Override
        public Class<SpatialIngredient> contentClass() {
            return SpatialIngredient.class;
        }

        @Override
        public Codec<SpatialIngredient> codec() {
            return CODEC;
        }
    }
}
