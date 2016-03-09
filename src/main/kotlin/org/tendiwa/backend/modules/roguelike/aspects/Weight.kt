package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.existence.NoInitAspect
import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspect
import org.tendiwa.tools.argumentConstraint


class Weight(
    var units: Int = 0
) : NoReactionAspect, NoInitAspect, NoStimuliAspect {
    init {
        argumentConstraint(
            units,
            { it >= 0 },
            { "weight must be >= 0" }
        )
    }
}
