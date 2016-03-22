package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractDataAspect
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.tools.argumentConstraint


class Weight(
    units: Int
) : AbstractDataAspect<Int>(units) {

    init {
        argumentConstraint(
            units,
            { it >= 0 },
            { "weight must be >= 0" }
        )
    }

    override fun createChangeStimulus(oldValue: Int, newValue: Int) =
        Change(oldValue, newValue)

    class Change(val old: Int, val new: Int) : Stimulus
}
