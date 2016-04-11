package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.transparency.transparency
import org.tendiwa.collections.withoutLast
import org.tendiwa.plane.grid.constructors.centeredGridRectangle
import org.tendiwa.plane.grid.constructors.segmentTo
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.contains

class NPCVision : AbstractAspect() {
    companion object {
        val VISION_RANGE = 21 by 21
    }

    fun canSee(reality: Reality, target: Position): Boolean {
        host.run {
            val position = aspect<Position>()
            val closeEnough =
                position
                    .tile
                    .let { centeredGridRectangle(it, VISION_RANGE) }
                    .contains(target.tile)
            val isVisible =
                position
                    .tile
                    .segmentTo(target.tile)
                    .tilesList
                    .withoutLast()
                    .all { reality.space.transparency.isTransparent(it) }
            val isLit = isTileLit(position, reality)
            return closeEnough && isVisible && isLit
        }
    }

    private fun isTileLit(position: Position, reality: Reality): Boolean {
        val bubble = reality.simulation.bubbleAt(position.chunkCoordinate)
        return if (bubble != null) {
            bubble
                .lighting
                .luminosityAt(
                    position.voxel.x,
                    position.voxel.y,
                    position.voxel.z
                )
                .level > 0
        } else {
            false
        }
    }
}
