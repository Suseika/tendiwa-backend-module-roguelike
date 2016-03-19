package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.modules.roguelike.aspects.BunchSize
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight

interface BundleItem : Item {
    @DeclaredAspect
    fun bunchSize(): BunchSize

    @DeclaredAspect
    fun unitWeight(): Weight

    @DeclaredAspect
    fun unitVolume(): Volume

    @DeclaredAspect
    fun weight(): Weight

    @DeclaredAspect
    fun volume(): Volume
}

