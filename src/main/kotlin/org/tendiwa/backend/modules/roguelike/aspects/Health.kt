package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.Aspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.backend.space.Reality

class Health(
    private var hitpoints: Int
) : Aspect {
    private val maxHitpoints: Int = hitpoints

    fun change(reality: Reality, delta: Int) {
        val old = hitpoints
        hitpoints += delta
        reality.sendStimulus(
            Health.Change(
                reality.hostOf(this),
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

val RealThing.health: Health
    get() = aspects[Health::class.java] as Health
