package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.Aspect
import org.tendiwa.tools.argumentConstraint


class Weight(
    var units: Int = 0
) : AbstractAspect() {
    init {
        argumentConstraint(
            units,
            { it >= 0 },
            { "weight must be >= 0" }
        )
    }
}
