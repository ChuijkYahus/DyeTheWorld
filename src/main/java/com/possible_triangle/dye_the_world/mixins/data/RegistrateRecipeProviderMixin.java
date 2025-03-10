package com.possible_triangle.dye_the_world.mixins.data;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.possible_triangle.dye_the_world.data.ConditionalFinishedRecipe;
import com.possible_triangle.dye_the_world.data.DyedRegistrateRecipeProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Stream;

@Mixin(value = RegistrateRecipeProvider.class, remap = false)
public abstract class RegistrateRecipeProviderMixin implements DyedRegistrateRecipeProvider {

    @Unique
    private final Queue<String> namespaces = Collections.asLifoQueue(new ArrayDeque<>());

    @Override
    public void pushNamespace(String namespace) {
        namespaces.add(namespace);
    }

    @Override
    public void popNamespace() {
        namespaces.poll();
    }

    @Override
    public Stream<String> getNamespaces() {
        return namespaces.stream().distinct();
    }

    @WrapMethod(
            method = "accept(Lnet/minecraft/data/recipes/FinishedRecipe;)V"
    )
    private void injectSave(FinishedRecipe recipe, Operation<Void> original) {
        var conditions = getNamespaces()
                .map(ModLoadedCondition::new)
                .toList();

        original.call(new ConditionalFinishedRecipe(recipe, conditions));
    }

}
