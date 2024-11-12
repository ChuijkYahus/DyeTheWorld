package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.index.DyedChalk;
import io.github.mortuusars.chalk.Chalk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chalk.Blocks.class)
public class ChalkMixin {

    @Inject(
            method = "<clinit>",
            at = @At("HEAD"),
            require = 0
    )
    private static void getPotFromDyeColor(CallbackInfo ci) {
        DyedChalk.INSTANCE.registerColors();
    }

}
