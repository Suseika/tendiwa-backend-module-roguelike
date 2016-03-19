package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.aspects.NPCVision

interface Character : RealThing {
    @DeclaredAspect
    fun vision() = NPCVision()
}
