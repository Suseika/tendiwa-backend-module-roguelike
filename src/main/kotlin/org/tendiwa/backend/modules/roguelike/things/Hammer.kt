package org.tendiwa.backend.modules.roguelike.things

import org.tendiwa.existence.RealThing
import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.backend.space.aspects.Name
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight

class Hammer : RealThing(), UniqueItem by Hammer.Prototype {
    private companion object Prototype : UniqueItem {
        override val weight = Weight(10)
        override val volume = Volume(10)
        override val name = Name("hammer")
    }
}
