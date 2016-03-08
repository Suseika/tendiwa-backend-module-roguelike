package org.tendiwa.backend.modules.roguelike.things

import org.tendiwa.backend.modules.roguelike.archetypes.Humanoid
import org.tendiwa.backend.modules.roguelike.aspects.Health
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.space.aspects.Name
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.existence.RealThing

class Human(
    position: Position,
    personalName: Name,
    weight: Weight,
    health: Health
) : RealThing(), Humanoid {
    init {
        addAspect(position)
        addAspect(personalName)
        addAspect(weight)
        addAspect(health)
        initHumanoid(this)
    }
}
