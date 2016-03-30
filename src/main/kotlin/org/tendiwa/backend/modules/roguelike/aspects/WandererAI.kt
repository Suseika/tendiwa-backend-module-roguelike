package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.time.Activity
import org.tendiwa.backend.time.ActivityProcess
import org.tendiwa.backend.time.TemporalActor
import org.tendiwa.collections.randomElement
import org.tendiwa.plane.grid.constructors.centeredGridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.masks.GridMask
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.intersection
import org.tendiwa.plane.grid.metrics.GridMetric
import org.tendiwa.plane.grid.tiles.neighbors

class WandererAI : AbstractAspect(), TemporalActor<Reality> {

    override fun act(context: Reality): Activity {
        val newTile = wander(context)
        return Activity(
            listOf(
                ActivityProcess(
                    1,
                    { host.aspect<Position>().change(context, newTile) }
                )
            )
        )
    }

    private fun wander(reality: Reality): Voxel {
        val center = host.aspect<Position>().tile
        return center
            .neighbors(GridMetric.TAXICAB)
            .intersection(
                GridMask { x, y ->
                    host.aspect<Passability>().isTilePassable(x, y)
                }
                    .boundedBy(centeredGridRectangle(center, 3 by 3))
            )
            .tiles
            .toList()
            .let {
                if (it.isEmpty()) {
                    return host.aspect<Position>().voxel
                } else {
                    return it
                        .randomElement(reality.random)
                        .let {
                            Voxel(it, z = host.aspect<Position>().voxel.z)
                        }
                }
            }
    }

}
