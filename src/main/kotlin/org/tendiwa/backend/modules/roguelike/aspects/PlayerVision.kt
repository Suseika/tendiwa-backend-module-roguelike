package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Space
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.chunks.chunkWithVoxel
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.backend.yawPlane
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

class PlayerVision : AbstractAspect() {

    lateinit var fieldOfView: FieldOfView

    override val stimuli: List<Class<out Stimulus>>
        get() = listOf(Position.Change::class.java)

    override fun reaction(reality: Reality, stimulus: Stimulus) {
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

    override fun init(reality: Reality) {
        fieldOfView = computeFieldOfView(host, reality)
    }

    private fun computeFieldOfView(
        host: RealThing,
        reality: Reality
    ): FieldOfView =
        FieldOfView(
            space = reality.space,
            center = host.aspect<Position>().tile,
            z = host.aspect<Position>().voxel.z
        )

    data class Change internal constructor(
        val host: RealThing,
        val new: FieldOfView,
        val old: FieldOfView
    ) : Stimulus

    class FieldOfView(
        space: Space,
        private val center: Tile,
        val z: Int
    ) {
        private val wallPlane = space.walls
        val hull =
            centeredGridRectangle(center, NPCVision.VISION_RANGE)
                .rectangleIntersection(space.hull.yawPlane)!!
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
                        .takeUntil { !isTileTransparent(it, z) }
                }

        private fun isTileTransparent(tile: Tile, z: Int): Boolean =
            wallPlane
                .chunkWithVoxel(tile.x, tile.y, z)
                .wallAt(tile) == WallType.void

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
            if (current.z == previous.z) {
                current.mask.difference(previous.mask)
            } else {
                current.mask
            }
                .boundedBy(current.hull)
                .tiles
                .toList()

        val unseen =
            if (current.z == previous.z) {
                previous.mask.difference(current.mask)
            } else {
                previous.mask
            }
                .boundedBy(previous.hull)
                .tiles
                .toList()
    }
}
