package com.possible_triangle.dye_the_world.mixins;

import com.possible_triangle.dye_the_world.object.OptionalLootEntry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootItem.class)
public class LootItemMixin {

    @Inject(
            method = "lootTableItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void optionalLootTableItem(ItemLike item, CallbackInfoReturnable<LootPoolSingletonContainer.Builder<?>> cir) {
        cir.setReturnValue(OptionalLootEntry.Companion.create(item));
    }

}
