package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind
import org.tendiwa.existence.RealThing
import org.tendiwa.stimuli.Stimulus
import org.tendiwa.stimuli.StimulusKind
import org.tendiwa.world.Reality

class Health(
    private var hitpoints: Int
) : NoReactionAspect(Health.Companion.kind) {
    companion object {
        val kind = NoStimuliAspectKind()
    }

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

    class Change(
        val host: RealThing,
        val old: Int,
        val new: Int
    ) : Stimulus(kind) {
        companion object {
            val kind = StimulusKind("health_change")
        }
    }
}

val RealThing.health: Health
    get() = aspects[Health.Companion.kind] as Health
