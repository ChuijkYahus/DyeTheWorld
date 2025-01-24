package com.possible_triangle.dye_the_world.mixins.data;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.possible_triangle.dye_the_world.data.ConditionalFinishedRecipe;
import com.possible_triangle.dye_the_world.data.DyedRegistrateRecipeProvider;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;

@Mixin(value = RegistrateRecipeProvider.class, remap = false)
public class RegistrateRecipeProviderMixin implements DyedRegistrateRecipeProvider {

    @Unique
    @Nullable
    private String namespace = null;

    @Override
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public void resetNamespace() {
        namespace = null;
    }

    @Override
    public @Nullable String getNamespace() {
        return namespace;
    }

    @WrapMethod(
            method = "accept(Lnet/minecraft/data/recipes/FinishedRecipe;)V"
    )
    private void injectSave(FinishedRecipe recipe, Operation<Void> original) {
        var conditions = Optional.ofNullable(namespace)
                .map(ModLoadedCondition::new)
                .map(List::of)
                .orElseGet(List::of);

        original.call(new ConditionalFinishedRecipe(recipe, conditions));
    }

}
