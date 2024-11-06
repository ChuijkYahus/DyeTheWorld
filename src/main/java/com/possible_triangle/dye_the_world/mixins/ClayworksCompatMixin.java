package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.index.DyedClayworks;
import com.teamabnormals.clayworks.core.other.ClayworksCompat;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashSet;

@Mixin(value = ClayworksCompat.class, remap = false)
public class ClayworksCompatMixin {

    @ModifyVariable(
            method = "addDecoratedPotBlockEntityTypes",
            at = @At("STORE"),
            require = 0
    )
    private static HashSet<Block> addDecoratedPotBlockEntityTypes(HashSet<Block> original) {
        DyedClayworks.INSTANCE.getDECORATED_POTS().forEach((dye, pot) -> {
            original.add(pot.get());
        });
        return original;
    }

}
