package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.modules.roguelike.aspects.NPCVision
import org.tendiwa.backend.existence.RealThing

interface Character {
    fun initCharacter(realThing: RealThing) {
        realThing.addAspect(NPCVision())
    }
}
