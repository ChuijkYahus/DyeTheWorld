package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.index.DyedClayworks;
import com.possible_triangle.dye_the_world.index.DyedFurniture;
import com.starfish_studios.another_furniture.block.LampConnectorBlock;
import com.teamabnormals.clayworks.core.registry.ClayworksBlocks;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClayworksBlocks.class, remap = false)
public class ClayworksBlocksMixin {

    @Inject(
            method = "getPotFromDyeColor",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void getPotFromDyeColor(DyeColor dye, CallbackInfoReturnable<Block> cir) {
        var values = DyedClayworks.INSTANCE.getDECORATED_POTS();
        var instance = values.get(dye);
        if (instance != null) {
            cir.setReturnValue(instance.get());
        }
    }

    @Inject(
            method = "getDyeColorFromPot",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private static void getDyeColorFromPot(Block block, CallbackInfoReturnable<DyeColor> cir) {
        var dye = DyedClayworks.INSTANCE.dyeOf(block);
        if (dye != null) {
            cir.setReturnValue(dye);
        }
    }

}
