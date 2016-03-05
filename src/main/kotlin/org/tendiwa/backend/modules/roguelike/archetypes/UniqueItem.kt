package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight

interface UniqueItem : Item {
    val weight: Weight
    val volume: Volume
}
