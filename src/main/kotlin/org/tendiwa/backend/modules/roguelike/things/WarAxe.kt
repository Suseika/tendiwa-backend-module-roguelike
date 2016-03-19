package org.tendiwa.backend.modules.roguelike.things

import org.tendiwa.backend.existence.AbstractRealThing
import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.space.aspects.Name

class WarAxe() : AbstractRealThing(), UniqueItem {
    @DeclaredAspect
    override fun name() = Name("war_axe")

    @DeclaredAspect
    override fun weight() = Weight(10)

    @DeclaredAspect
    override fun volume() = Volume(10)
}
