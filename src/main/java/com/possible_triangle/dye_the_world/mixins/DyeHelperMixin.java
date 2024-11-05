package com.possible_triangle.dye_the_world.mixins;

import com.google.common.collect.ImmutableMap;
import com.possible_triangle.dye_the_world.compat.CreateCompat;
import com.possible_triangle.dye_the_world.index.DyedFurniture;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.DyeHelper;
import net.minecraft.world.item.DyeColor;
import org.checkerframework.checker.units.qual.K;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = DyeHelper.class, remap = false)
public class DyeHelperMixin {

    @Redirect(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"),
            require = 0
    )
    private static ImmutableMap<DyeColor, Couple<Integer>> getLampConnectorByColor(ImmutableMap.Builder<DyeColor, Couple<Integer>> builder) {
        builder.putAll(CreateCompat.INSTANCE.getADDITIONAL_DYE_COLORS());
        return builder.build();
    }

}
