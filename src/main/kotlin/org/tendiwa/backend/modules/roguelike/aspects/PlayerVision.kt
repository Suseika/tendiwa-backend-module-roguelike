package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.Aspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Space
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.aspects.position
import org.tendiwa.backend.space.chunks.chunkWithTile
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.backend.space.walls.walls
import org.tendiwa.collections.takeUntil
import org.tendiwa.plane.grid.constructors.centeredGridRectangle
import org.tendiwa.plane.grid.masks.GridMask
import org.tendiwa.plane.grid.masks.borders.border
import org.tendiwa.plane.grid.masks.boundedBy
import org.tendiwa.plane.grid.masks.difference
import org.tendiwa.plane.grid.masks.mutable.MutableArrayGridMask
import org.tendiwa.plane.grid.masks.mutable.add
import org.tendiwa.plane.grid.rectangles.GridRectangle
import org.tendiwa.plane.grid.rectangles.rectangleIntersection
import org.tendiwa.plane.grid.segments.GridSegment
import org.tendiwa.plane.grid.tiles.Tile

class PlayerVision : Aspect {

    lateinit var fieldOfView: FieldOfView

    override val stimuli: List<Class<out Stimulus>>
        get() = listOf(Position.Change::class.java)

    override fun reaction(reality: Reality, stimulus: Stimulus) {
        val host = reality.hostOf(this)
        if (
        stimulus is Position.Change
            && stimulus.host == host
        ) {
            val oldFieldOfView = fieldOfView
            fieldOfView = computeFieldOfView(host, reality)
            reality.sendStimulus(
                Change(
                    host = host,
                    old = oldFieldOfView,
                    new = fieldOfView
                )
            )
        }
    }

    override fun init(reality: Reality, host: RealThing) {
        fieldOfView = computeFieldOfView(host, reality)
    }

    private fun computeFieldOfView(
        host: RealThing,
        reality: Reality
    ): FieldOfView =
        FieldOfView(
            space = reality.space,
            center = host.position.tile
        )

    data class Change internal constructor(
        val host: RealThing,
        val new: FieldOfView,
        val old: FieldOfView
    ) : Stimulus

    class FieldOfView(
        space: Space,
        private val center: Tile
    ) {
        private val wallPlane = space.walls
        val hull =
            centeredGridRectangle(center, NPCVision.VISION_RANGE)
                .rectangleIntersection(space.hull)!!
        val mask: GridMask = computeVisionMask()

        private fun computeVisionMask(): GridMask =
            MutableArrayGridMask(hull).apply {
                visibleTiles(hull).forEach { add(it) }
            }

        private fun visibleTiles(hull: GridRectangle): List<Tile> =
            hull.border
                .tiles
                .flatMap {
                    GridSegment(center, it)
                        .tilesList
                        .takeUntil { !isTileTransparent(it) }
                }

        private fun isTileTransparent(tile: Tile): Boolean =
            wallPlane.chunkWithTile(tile).wallAt(tile) == WallType.void

        fun difference(new: FieldOfView): VisionDifference =
            VisionDifference(this, new)
    }

    /**
     * Difference between two fields of view.
     */
    class VisionDifference(
        previous: FieldOfView,
        current: FieldOfView
    ) {
        val seen =
            current.mask.difference(previous.mask)
                .boundedBy(current.hull)
                .tiles
                .toList()
        val unseen =
            previous.mask.difference(current.mask)
                .boundedBy(previous.hull)
                .tiles
                .toList()
    }
}

val RealThing.playerVision: PlayerVision
    get() = aspects[PlayerVision::class.java]!! as PlayerVision
