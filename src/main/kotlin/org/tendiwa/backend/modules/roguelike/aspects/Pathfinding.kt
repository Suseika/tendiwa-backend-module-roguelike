package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.tile
import org.tendiwa.plane.grid.algorithms.floods.Flood
import org.tendiwa.plane.grid.algorithms.pathfinding.GridPath
import org.tendiwa.plane.grid.algorithms.pathfinding.astar.path
import org.tendiwa.plane.grid.constructors.centeredGridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.GridMask
import org.tendiwa.plane.grid.metrics.GridMetric

class Pathfinding : AbstractAspect() {

    private lateinit var passability: Passability

    companion object {
        private val AREA = 21 by 21
    }

    override fun init(reality: Reality) {
        this.passability = host.aspect<Passability>()
    }

    /**
     * Computes path table that fills [AREA] around current [Position] of
     * this thing.
     */
    fun flood(): Flood {
        val position = this.host.aspect<Position>()
        return Flood(
            start = position.tile,
            passable = GridMask { x, y -> passability.isTilePassable(x, y) },
            tableHull = centeredGridRectangle(position.tile, AREA),
            metric = GridMetric.KING
        )
    }

    fun astarPath(voxel: Voxel): GridPath? =
        GridMask { x, y -> passability.isTilePassable(x, y) }
            .path(host.aspect<Position>().tile, voxel.tile)
}
