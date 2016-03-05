package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.modules.roguelike.aspects.BunchSize
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight

interface BundleItem : Item {
    val bunchSize: BunchSize
    val unitWeight: Weight
    val unitVolume: Volume
    val weight: Weight
        get() =
        Weight(unitWeight.units * bunchSize.amount)
    val volume: Volume
        get() =
        Volume(unitVolume.units * bunchSize.amount)
}

