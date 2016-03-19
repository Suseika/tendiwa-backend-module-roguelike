package org.tendiwa.backend.modules.roguelike.things

import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.space.aspects.Name

class Hammer : RealThing(), UniqueItem by Hammer.Prototype {
    private companion object Prototype : UniqueItem {
        override fun weight() = Weight(10)
        override fun volume() = Volume(10)
        override fun name() = Name("hammer")
    }
}
