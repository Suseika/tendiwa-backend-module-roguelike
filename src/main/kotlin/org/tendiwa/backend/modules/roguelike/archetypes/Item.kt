package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.space.archetypes.PositionCapable
import org.tendiwa.backend.space.aspects.Name

interface Item : PositionCapable {
    @DeclaredAspect
    fun name(): Name
}
