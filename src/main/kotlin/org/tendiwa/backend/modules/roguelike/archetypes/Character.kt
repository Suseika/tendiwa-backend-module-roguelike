package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.aspects.NPCVision
import org.tendiwa.backend.modules.roguelike.aspects.Passability
import org.tendiwa.backend.modules.roguelike.aspects.Pathfinding

interface Character : RealThing {
    @DeclaredAspect
    fun vision() = NPCVision()

    @DeclaredAspect
    fun passability() = Passability()

    @DeclaredAspect
    fun pathfinding() = Pathfinding()
}
