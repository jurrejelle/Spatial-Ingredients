package dev.jurrejelle.spatialingredients.common.data;

import dev.jurrejelle.spatialingredients.api.machine.part.SpatialPartAbility;
import dev.jurrejelle.spatialingredients.common.machine.multiblock.part.SpatialHatchPartMachine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.registry.GTRegistration;
import dev.jurrejelle.spatialingredients.gtbridge.SpatialRecipeTypes;

import static com.gregtechceu.gtceu.api.GTValues.ZPM;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_PTFE_INERT;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.BATCH_MODE;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.OC_PERFECT_SUBTICK;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.OVERLAY_ITEM_HATCH;
import static dev.jurrejelle.spatialingredients.SpatialIngredients.REGISTRATE;

public class SpatialMachines {

    public static final MachineDefinition SPATIAL_HATCH = REGISTRATE
            .machine("spatial_hatch", (holder) -> new SpatialHatchPartMachine(holder, ZPM, IO.IN))
            .langValue("Spatial Hatch")
            .rotationState(RotationState.ALL)
            .tier(ZPM)
            .modelProperty(GTMachineModelProperties.IS_FORMED, false)
            .colorOverlayTieredHullModel(GTCEu.id("block/overlay/machine/overlay_pipe_in_emissive"), null,
                    GTCEu.id("block/overlay/machine/" + OVERLAY_ITEM_HATCH))
            .abilities(SpatialPartAbility.SPATIAL_HATCH)
            .register();

    public static final MultiblockMachineDefinition LARGE_SPATIAL_REACTOR = GTRegistration.REGISTRATE
            .multiblock("large_spatial_reactor", WorkableElectricMultiblockMachine::new)
            .rotationState(RotationState.ALL)
            .recipeType(SpatialRecipeTypes.LARGE_SPATIAL_RECIPES)
            .recipeModifiers(OC_PERFECT_SUBTICK, BATCH_MODE)
            .appearanceBlock(CASING_PTFE_INERT)
            .pattern(definition -> {
                var casing = blocks(CASING_PTFE_INERT.get()).setMinGlobalLimited(10);
                var abilities = Predicates.autoAbilities(definition.getRecipeTypes())
                        .or(Predicates.autoAbilities(true, false, false))
                        .or(Predicates.abilities(SpatialPartAbility.SPATIAL_HATCH));
                return FactoryBlockPattern.start()
                        .aisle("XXX", "XCX", "XXX")
                        .aisle("XCX", "CPC", "XCX")
                        .aisle("XXX", "XSX", "XXX")
                        .where('S', Predicates.controller(blocks(definition.getBlock())))
                        .where('X', casing.or(abilities))
                        .where('P', blocks(CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                        .where('C', Predicates.heatingCoils().setExactLimit(1)
                                .or(abilities)
                                .or(casing))
                        .build();
            })
            .workableCasingModel(GTCEu.id("block/casings/solid/machine_casing_inert_ptfe"),
                    GTCEu.id("block/multiblock/large_chemical_reactor"))
            .register();

    public static void init() {}
}
