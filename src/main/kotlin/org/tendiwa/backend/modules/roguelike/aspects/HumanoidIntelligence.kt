package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.archetypes.Character
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.realThing.realThings
import org.tendiwa.backend.space.realThing.viewOfArea
import org.tendiwa.backend.time.*
import org.tendiwa.collections.randomElement
import org.tendiwa.plane.grid.constructors.centeredGridRectangle
import org.tendiwa.plane.grid.metrics.GridMetric
import org.tendiwa.plane.grid.segments.GridSegment
import org.tendiwa.plane.grid.tiles.distanceTo
import org.tendiwa.plane.grid.tiles.isNear
import org.tendiwa.plane.grid.tiles.neighbors

class HumanoidIntelligence : AbstractAspect(), Actor<Reality> {
    override fun act(context: Reality): Activity {
        fun attack(target: RealThing): Activity =
            Activity(
                listOf(
                    ActivityProcess(1, ActivityResult {
                        target
                            .aspect<Health>()
                            .change(context, -1)
                    }),
                    Cooldown(1)
                )
            )

        fun walkTowards(target: RealThing): Activity =
            Activity(
                listOf(
                    ActivityProcess(1, ActivityResult {
                        host.aspect<Position>().change(
                            context,
                            GridSegment(
                                host.aspect<Position>().tile,
                                target.aspect<Position>().tile
                            )
                                .tilesList[1]
                                .let { Voxel(it, host.aspect<Position>().voxel.z) }
                        )
                    }),
                    Cooldown(1)
                )
            )

        fun wander(): Activity =
            Activity(
                listOf(
                    ActivityProcess(1, ActivityResult {
                        host.aspect<Position>().tile
                            .neighbors(GridMetric.KING)
                            .tiles
                            .toList()
                            .randomElement(context.random)
                            .let { Voxel(it, host.aspect<Position>().voxel.z) }
                            .let { host.aspect<Position>().change(context, it) }
                    })
                )
            )

        fun closestEnemy(): RealThing? =
            context.space.realThings
                .viewOfArea(
                    centeredGridRectangle(
                        host.aspect<Position>().tile,
                        NPCVision.VISION_RANGE
                    )
                )
                .things
                .filter { it is Character }
                .minBy { it.aspect<Position>().tile.distanceTo(host.aspect<Position>().tile) }

        val closestEnemy = closestEnemy()
        return if (closestEnemy != null) {
            if (closestEnemy.aspect<Position>().tile.isNear(host.aspect<Position>().tile, GridMetric.KING)) {
                attack(closestEnemy)
            } else {
                walkTowards(closestEnemy)
            }
        } else {
            wander()
        }
    }

}
