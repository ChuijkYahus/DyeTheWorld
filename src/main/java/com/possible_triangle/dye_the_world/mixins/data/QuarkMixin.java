package com.possible_triangle.dye_the_world.mixins.data;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.violetmoon.quark.base.Quark;

@Mixin(value = Quark.class, remap = false)
public class QuarkMixin {

    @ModifyExpressionValue(
        method = "<init>",
        at = @At(value = "INVOKE", target = "Lorg/violetmoon/zeta/util/Utils;isDevEnv()Z")
    )
    public boolean noMixinAudit(boolean original) {
        return false;
    }

}
