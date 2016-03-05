package aspects

import org.tendiwa.backend.modules.roguelike.archetypes.BundleItem
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind
import org.tendiwa.existence.RealThing
import org.tendiwa.stimuli.Stimulus
import org.tendiwa.stimuli.StimulusKind
import org.tendiwa.world.Reality
import java.util.*

class Inventory() : NoReactionAspect(kind) {
    companion object {
        val kind = NoStimuliAspectKind()
    }

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
            (items[item.javaClass]!! as BundleItem)
                .bunchSize.changeAmount(reality, item.bunchSize.amount)
        }
    }

    fun remove(reality: Reality, item: Item) {
        if (!items.containsKey(item.javaClass)) {
            throw IllegalArgumentException(
                "Item $item is not present in Inventory"
            )
        }
        if (item is UniqueItem) {

            items.put(item.javaClass, item)
        } else if (item is BundleItem) {
            val inInventory = items[item.javaClass]!! as BundleItem
            val currentAmount = inInventory.bunchSize.amount
            val removedAmount = item.bunchSize.amount
            if (currentAmount == removedAmount) {
                items.remove(item.javaClass)
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
                    "There are $currentAmount ${item.name.string}s in " +
                        "inventory, and you are trying to remove " +
                        "$removedAmount"
                )
            }
        }
    }

    class Store(
        val host: RealThing,
        val item: Item
    ) : Stimulus(kind) {
        companion object {
            val kind = StimulusKind("inventory_store")
        }
    }

    class Lose(
        val host: RealThing,
        val item: Item
    ) : Stimulus(kind) {
        companion object {
            val kind = StimulusKind("inventory_lose")
        }
    }
}
