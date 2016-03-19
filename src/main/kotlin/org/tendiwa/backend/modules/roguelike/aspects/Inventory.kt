package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.Aspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.backend.modules.roguelike.archetypes.BundleItem
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.name
import java.util.*

class Inventory() : Aspect {

    private val items: MutableMap<Class<out Item>, Item> =
        LinkedHashMap()


    fun store(reality: Reality, item: Item) {
        if (items.containsKey(item.javaClass)) {
            throw IllegalArgumentException(
                "Item $item is already present in Inventory"
            )
        }
        if (item is UniqueItem) {
            items.put(item.javaClass, item)
        } else if (item is BundleItem) {
            (items[item.javaClass]!! as RealThing)
                .bunchSize.changeAmount(
                reality,
                (item as RealThing).bunchSize.amount
            )
        }
    }

    fun remove(reality: Reality, item: Item) {
        if (item is RealThing) {
            if (!items.containsKey((item as Item).javaClass)) {
                throw IllegalArgumentException(
                    "Item $item is not present in Inventory"
                )
            }
            if (item is UniqueItem) {

                items.put(item.javaClass, item)
            } else if (item is BundleItem) {
                val inInventory = items[(item as Item).javaClass]!! as RealThing
                val currentAmount = inInventory.bunchSize.amount
                val removedAmount = item.bunchSize.amount
                if (currentAmount == removedAmount) {
                    items.remove((item as Item).javaClass)
                    reality.sendStimulus(
                        Inventory.Lose(
                            host = reality.hostOf(this),
                            item = inInventory as Item
                        )
                    )
                } else if (currentAmount > removedAmount) {
                    inInventory
                        .bunchSize.changeAmount(reality, -removedAmount)
                } else {
                    assert(currentAmount < removedAmount)
                    throw IllegalArgumentException(
                        "There are $currentAmount ${(item as RealThing).name.string}s" +
                            " in inventory, and you are trying to remove " +
                            "$removedAmount"
                    )
                }
            }
        }
    }

    data class Store
    internal constructor(
        val host: RealThing,
        val item: Item
    ) : Stimulus

    data class Lose
    internal constructor(
        val host: RealThing,
        val item: Item
    ) : Stimulus
}
