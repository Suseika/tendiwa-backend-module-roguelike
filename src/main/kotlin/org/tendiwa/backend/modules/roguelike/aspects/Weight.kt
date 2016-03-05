package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind
import org.tendiwa.tools.argumentConstraint


class Weight(
    var units: Int = 0
) : NoReactionAspect(kind) {
    companion object {
        val kind = NoStimuliAspectKind()
    }
    init {
        argumentConstraint(
            units,
            { it >= 0 },
            { "weight must be >= 0" }
        )
    }
}
