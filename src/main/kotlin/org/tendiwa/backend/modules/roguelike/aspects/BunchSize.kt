package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.Aspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.backend.space.Reality

class BunchSize(
    amount: Int
) : Aspect {
    var amount: Int =
        amount
        get() = field
        private set(value) {
            field = value
        }

    fun changeAmount(reality: Reality, delta: Int) {
        val old = amount
        amount += delta
        reality.sendStimulus(
            BunchSize.Change(
                reality.hostOf(this),
                old = old,
                new = amount
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
