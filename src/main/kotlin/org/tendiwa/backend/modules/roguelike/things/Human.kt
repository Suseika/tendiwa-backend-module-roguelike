package org.tendiwa.backend.modules.roguelike.things

import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.archetypes.Humanoid
import org.tendiwa.backend.modules.roguelike.aspects.Health
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.space.aspects.Name
import org.tendiwa.backend.space.aspects.Position

class Human(
    position: Position,
    personalName: Name,
    weight: Weight,
    health: Health
) : RealThing(
    position,
    personalName,
    weight,
    health
), Humanoid
