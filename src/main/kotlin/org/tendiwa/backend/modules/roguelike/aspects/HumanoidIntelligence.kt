package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.modules.roguelike.archetypes.Character
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.backend.space.realThing.realThings
import org.tendiwa.backend.space.realThing.viewOfArea
import org.tendiwa.collections.randomElement
import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind
import org.tendiwa.existence.RealThing
import org.tendiwa.plane.grid.constructors.centeredGridRectangle
import org.tendiwa.plane.grid.metrics.GridMetric
import org.tendiwa.plane.grid.segments.GridSegment
import org.tendiwa.plane.grid.tiles.distanceTo
import org.tendiwa.plane.grid.tiles.neighbors
import org.tendiwa.time.*

class HumanoidIntelligence : NoReactionAspect(kind), Actor<Reality> {
    companion object {
        val kind = NoStimuliAspectKind()
    }

    override fun act(context: Reality): Activity {
        val host = context.hostOf(this)
        fun attack(target: RealThing): Activity =
            Activity(
                listOf(
                    ActivityProcess(1, ActivityResult {
                        target.health.change(context, -1)
                    }),
                    Cooldown(1)
                )
            )

        fun walkTowards(target: RealThing): Activity =
            Activity(
                listOf(
                    ActivityProcess(1, ActivityResult {
                        host.position.move(
                            context,
                            GridSegment(
                                host.position.tile,
                                target.position.tile
                            )
                                .tilesList[1]
                                .let { Voxel(it, host.position.voxel.z) }
                        )
                    }),
                    Cooldown(1)
                )
            )

        fun wander(): Activity =
            Activity(
                listOf(
                    ActivityProcess(1, ActivityResult {
                        host.position.tile
                            .neighbors(GridMetric.KING)
                            .tiles
                            .toList()
                            .randomElement(context.random)
                            .let { Voxel(it, host.position.voxel.z) }
                            .let { host.position.move(context, it) }
                    })
                )
            )

        fun closestEnemy(): RealThing? =
            context.space.realThings
                .viewOfArea(
                    centeredGridRectangle(
                        host.position.tile,
                        NPCVision.VISION_RANGE
                    )
                )
                .things
                .filter { it is Character }
                .minBy { it.position.tile.distanceTo(host.position.tile) }

        val closestEnemy = closestEnemy()
        return if (closestEnemy != null) {
            if (closestEnemy.position.isNear(host.position)) {
                attack(closestEnemy)
            } else {
                walkTowards(closestEnemy)
            }
        } else {
            wander()
        }
    }

}
