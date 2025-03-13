package com.possible_triangle.dye_the_world.`object`.block

import com.possible_triangle.dye_the_world.index.DyedBaskets
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.DustParticleOptions
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.projectile.Projectile
import net.minecraft.world.item.DyeColor
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.HorizontalDirectionalBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.BlockHitResult
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.joml.Vector3f
import java.awt.Color
import kotlin.math.min

class DyeBasketBlock(properties: Properties, dye: DyeColor) : HorizontalDirectionalBlock(properties) {

    companion object {
        val SHAPE: VoxelShape = box(0.0, 0.0, 0.0, 16.0, 15.0, 16.0)
    }

    private val color = Color(dye.fireworkColor)
        .let { Vector3f(it.red / 255F, it.green / 255F, it.blue / 255F) }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block?, BlockState?>) {
        super.createBlockStateDefinition(builder.add(FACING))
    }

    override fun getStateForPlacement(blockPlaceContext: BlockPlaceContext): BlockState? {
        return this.defaultBlockState().setValue(FACING, blockPlaceContext.horizontalDirection.opposite)
    }

    override fun getCollisionShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext) =
        SHAPE

    override fun getBlockSupportShape(state: BlockState, level: BlockGetter, pos: BlockPos) = Shapes.block()

    override fun getVisualShape(state: BlockState, level: BlockGetter, pos: BlockPos, context: CollisionContext) =
        Shapes.block()

    override fun getShadeBrightness(state: BlockState, level: BlockGetter, pos: BlockPos) = 0.2F

    private fun spawnParticles(level: BlockGetter, pos: BlockPos, count: Int = 60) {
        if (level is ServerLevel && count > 0) {
            level.playSound(null, pos, DyedBaskets.POOF_SOUND.get(), SoundSource.BLOCKS, 1.5F, 1F)
            level.sendParticles(
                DustParticleOptions(color, 1F),
                pos.x + 0.5, pos.y + 1.2, pos.z + 0.5, count,
                0.5, 0.15, 0.5, 3.0
            )
        }
    }

    override fun fallOn(
        level: Level,
        state: BlockState,
        pos: BlockPos,
        entity: Entity,
        distance: Float
    ) {
        entity.causeFallDamage(distance, 0.5F, level.damageSources().fall())
        spawnParticles(level, pos, min(60, (distance - 2).toInt() * 10))
    }

    override fun onProjectileHit(
        level: Level,
        state: BlockState,
        hit: BlockHitResult,
        projectile: Projectile
    ) {
        super.onProjectileHit(level, state, hit, projectile)
        spawnParticles(level, hit.blockPos)
    }

}