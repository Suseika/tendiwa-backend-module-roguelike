package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.space.Reality
import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind
import org.tendiwa.existence.RealThing
import org.tendiwa.stimuli.Stimulus

class BunchSize(
    amount: Int
) : NoReactionAspect(BunchSize.Companion.kind) {
    var amount: Int =
        amount
        get() = field
        private set(value) {
            field = value
        }

    companion object {
        val kind = NoStimuliAspectKind()
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
