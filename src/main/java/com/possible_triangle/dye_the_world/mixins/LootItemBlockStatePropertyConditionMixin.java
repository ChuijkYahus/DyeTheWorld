package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.object.BlockLessStatePropertyCondition;
import com.possible_triangle.dye_the_world.object.OptionalLootEntry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootItemBlockStatePropertyCondition.class)
public class LootItemBlockStatePropertyConditionMixin {

    @Inject(
            method = "hasBlockStateProperties",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void optionalLootTableItem(Block block, CallbackInfoReturnable<LootItemBlockStatePropertyCondition.Builder> cir) {
        cir.setReturnValue(BlockLessStatePropertyCondition.Companion.wrapped(block));
    }

}
