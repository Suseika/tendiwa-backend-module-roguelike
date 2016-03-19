package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight

interface UniqueItem : Item {
    @DeclaredAspect
    fun weight(): Weight

    @DeclaredAspect
    fun volume(): Volume
}
