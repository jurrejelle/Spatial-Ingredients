package dev.jurrejelle.spatialingredients.api.recipe.ingredient;

import com.gregtechceu.gtceu.api.recipe.content.IContentSerializer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpatialIngredient {
    private static final Logger LOGGER = LogManager.getLogger();

    public static final SpatialIngredient EMPTY = new SpatialIngredient(Blocks.AIR.defaultBlockState());

    public static final Codec<SpatialIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("blockstate").forGetter(SpatialIngredient::getBlockstate)
    ).apply(instance, SpatialIngredient::new));

    @Getter
    private final BlockState blockstate;

    public SpatialIngredient(BlockState blockstate) {
        this.blockstate = blockstate;
    }

    public SpatialIngredient copy() {
        Tag tag = CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow(false, LOGGER::error);
        return CODEC.decode(NbtOps.INSTANCE, tag).getOrThrow(false, LOGGER::error).getFirst();
    }

    public static final class Serializer implements IContentSerializer<SpatialIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public SpatialIngredient of(Object o) {
            if (o instanceof BlockState state) {
                return new SpatialIngredient(state);
            }
            return null;
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
            return null;
        }
    }
}
