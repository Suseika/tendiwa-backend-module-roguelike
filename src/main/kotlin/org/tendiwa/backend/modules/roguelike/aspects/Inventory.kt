package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.existence.AbstractAspect
import org.tendiwa.backend.existence.Aspect
import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.existence.Stimulus
import org.tendiwa.backend.modules.roguelike.archetypes.BundleItem
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.backend.modules.roguelike.archetypes.addBundle
import org.tendiwa.backend.space.Reality
import org.tendiwa.backend.space.aspects.name
import java.util.*

class Inventory() : AbstractAspect() {

    private val bundleItems: MutableMap<Class<out BundleItem>, BundleItem> =
        LinkedHashMap()

    private val uniqueItems: MutableSet<UniqueItem> =
        LinkedHashSet()

    /**
     * [UniqueItems][UniqueItem] and [BundleItems][BundleItem] in this
     * inventory.
     */
    val items: Collection<Item>
        get() = bundleItems.values + uniqueItems

    fun store(reality: Reality, item: Item) {
        if (item is UniqueItem) {
            if (uniqueItems.contains(item)) {
                throw IllegalArgumentException(
                    "Item $item is already present in Inventory"
                )
            }
            uniqueItems.add(item)
        } else if (item is BundleItem) {
            if (bundleItems.containsKey(item.javaClass)) {
                bundleItems[item.javaClass]!!
                    .addBundle(reality, item)
            } else {
                bundleItems.put(item.javaClass, item)
            }
        } else {
            throw IllegalArgumentException(
                "Only UniqueItem or BundleItem can be stored in inventory"
            )
        }
        reality.sendStimulus(
            Store(
                host = reality.hostOf(this),
                item = item
            )
        )
    }

    fun remove(reality: Reality, item: Item) {
        if (item is UniqueItem) {
            if (!uniqueItems.contains(item)) {
                throw IllegalArgumentException(
                    "Item $item is not present in Inventory"
                )
            }
            uniqueItems.remove(item)
            reality.sendStimulus(
                Lose(
                    host = reality.hostOf(this),
                    item = item
                )
            )
        } else if (item is BundleItem) {
            val inInventory = bundleItems[item.javaClass]!!
            val currentAmount = inInventory.bunchSize.amount
            val removedAmount = item.bunchSize.amount
            if (currentAmount == removedAmount) {
                bundleItems.remove(item.javaClass)
                reality.sendStimulus(
                    Inventory.Lose(
                        host = reality.hostOf(this),
                        item = inInventory
                    )
                )
            } else if (currentAmount > removedAmount) {
                inInventory
                    .bunchSize.changeAmount(reality, -removedAmount)
            } else {
                assert(currentAmount < removedAmount)
                throw IllegalArgumentException(
                    "There are $currentAmount ${item.name.string}s" +
                        " in inventory, and you are trying to remove " +
                        "$removedAmount"
                )
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
