package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.modules.roguelike.aspects.NPCVision

interface Character {
    @DeclaredAspect
    fun vision() = NPCVision()
}
