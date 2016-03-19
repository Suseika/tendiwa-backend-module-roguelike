package org.tendiwa.backend.modules.roguelike.things

import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.space.aspects.Name

class WarAxe() : UniqueItem by WarAxe.Prototype {
    companion object Prototype : UniqueItem {
        override val name: Name = Name("war_axe")
        override val weight: Weight = Weight(10)
        override val volume: Volume = Volume(10)
    }
}
