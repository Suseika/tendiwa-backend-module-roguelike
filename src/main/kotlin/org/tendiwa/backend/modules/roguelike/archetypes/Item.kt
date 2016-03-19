package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.space.aspects.Name

interface Item : RealThing {
    @DeclaredAspect
    fun name(): Name
}
