package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.modules.roguelike.aspects.BunchSize
import org.tendiwa.backend.modules.roguelike.aspects.Volume
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.backend.modules.roguelike.aspects.bunchSize
import org.tendiwa.backend.space.Reality

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

/**
 * Adds another [BundleItem] to this one, increasing the [BunchSize] aspect
 * of this [BundleItem] by the amount of items in the added bundle.
 * @param added The added bundle.
 * @throws IllegalArgumentException If the two bundles are of different classes.
 */
fun BundleItem.addBundle(reality: Reality, added: BundleItem) {
    if (this.javaClass != added.javaClass) {
        throw IllegalArgumentException(
            "Trying to add bundle of type ${added.javaClass} " +
                "to a bundle of type ${this.javaClass}"
        )
    }
    this.bunchSize
        .changeAmount(
            reality,
            added.bunchSize.amount
        )
}
