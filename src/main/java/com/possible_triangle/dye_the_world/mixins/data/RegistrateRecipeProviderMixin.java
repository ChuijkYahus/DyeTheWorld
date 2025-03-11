package com.possible_triangle.dye_the_world.mixins.data;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.possible_triangle.dye_the_world.data.ConditionalFinishedRecipe;
import com.possible_triangle.dye_the_world.data.DyedRegistrateRecipeProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;
import java.util.stream.Stream;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = RegistrateRecipeProvider.class, remap = false)
public abstract class RegistrateRecipeProviderMixin implements DyedRegistrateRecipeProvider {

    @Unique
    private final Queue<ICondition> conditions = Collections.asLifoQueue(new ArrayDeque<>());

    @Override
    public void pushCondition(ICondition condition) {
        conditions.add(condition);
    }

    @Override
    public void popCondition() {
        conditions.poll();
    }

    @Override
    public Stream<ICondition> getCondition() {
        return conditions.stream().distinct();
    }

    @WrapMethod(
            method = "accept(Lnet/minecraft/data/recipes/FinishedRecipe;)V"
    )
    private void injectSave(FinishedRecipe recipe, Operation<Void> original) {
        var conditions = getCondition().toList();
        original.call(new ConditionalFinishedRecipe(recipe, conditions));
    }

}
