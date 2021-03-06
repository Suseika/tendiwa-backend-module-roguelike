package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.backend.space.Reality

class Health(
    private var hitpoints: Int
) : AbstractAspect() {
    private val maxHitpoints: Int = hitpoints

    fun change(reality: Reality, delta: Int) {
        val old = hitpoints
        hitpoints += delta
        reality.sendStimulus(
            Health.Change(
                host,
                old = old,
                new = hitpoints
            )
        )
    }

    data class Change
    internal constructor(
        val host: RealThing,
        val old: Int,
        val new: Int
    ) : Stimulus
}
