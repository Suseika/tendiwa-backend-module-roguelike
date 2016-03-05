package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.tile
import org.tendiwa.existence.AspectKind
import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.RealThing
import org.tendiwa.plane.grid.metrics.GridMetric
import org.tendiwa.plane.grid.tiles.Tile
import org.tendiwa.plane.grid.tiles.isNear
import org.tendiwa.stimuli.Stimulus
import org.tendiwa.stimuli.StimulusKind

class Position(
    voxel: Voxel
) : NoReactionAspect(kind) {
    var voxel: Voxel =
        voxel
        get() = field
        private set(value) {
            field = value
        }

    companion object {
        val kind = AspectKind(emptyList())
    }

    fun move(reality: Reality, voxel: Voxel) {
        val old = this.voxel
        this.voxel = voxel
        reality.sendStimulus(Change(reality.hostOf(this), old, voxel))
    }

    class Change internal constructor(
        val host: RealThing,
        val from: Voxel,
        val to: Voxel
    ) : Stimulus(kind) {
        companion object {
            val kind = StimulusKind("movement")
        }
    }

    fun isNear(other: Position): Boolean =
        this.voxel.z == other.voxel.z
            && this.voxel.tile.isNear(other.voxel.tile, GridMetric.KING)

    val tile: Tile
        get() = voxel.tile
}


var RealThing.position: Position
    get() = aspects[Position.Companion.kind] as Position
    set(value) {
        addAspect(value)
    }
