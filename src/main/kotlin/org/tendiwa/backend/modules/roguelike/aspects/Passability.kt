package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.walls.hasWallAt

class Passability : AbstractAspect() {
    private lateinit var reality: Reality
    private lateinit var position: Position

    override fun init(reality: Reality) {
        this.reality = reality
        this.position = host.aspect<Position>()
    }

    fun isTilePassable(x: Int, y: Int): Boolean =
        isCurrentTile(x, y)
            || tileIsWithinPlaneBounds(x, y) && noWallAtTile(x, y)

    private fun isCurrentTile(x: Int, y: Int): Boolean =
        position.voxel.x == x && position.voxel.y == y

    private fun tileIsWithinPlaneBounds(x: Int, y: Int) =
        reality.space.levelHull.contains(x, y)

    private fun noWallAtTile(x: Int, y: Int): Boolean =
        reality.space.walls
            .planeAtZ(position.voxel.z)
            .hasWallAt(x, y)
            .not()
}
