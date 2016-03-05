package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.existence.AspectKind
import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.RealThing
import org.tendiwa.stimuli.Stimulus
import org.tendiwa.stimuli.StimulusKind
import org.tendiwa.tools.argumentsConstraint
import org.tendiwa.world.Reality
import java.util.*

class Equipment(
    slots: List<Equipment.Slot>
) : NoReactionAspect(Equipment.Companion.kind) {
    companion object {
        val kind = AspectKind(listOf())
    }

    private val worn =
        slots
            .map { Pair(it, Equipment.SlotContent()) }
            .toMap(LinkedHashMap(slots.size))

    /**
     * Wears an [item] in a particular [slot].
     * @throws IllegalArgumentException If [slot] is currently occupied or if
     * there is no such [slot] in this [Equipment].
     */
    fun equip(reality: Reality, slot: Equipment.Slot, item: UniqueItem) {
        validateAllowedSlot(slot)
        worn[slot]!!.occupyWith(item)
        reality.sendStimulus(Equipment.Equip(reality.hostOf(this), slot, item))
    }

    /**
     * Takes off whatever is currently worn in [slot].
     * @throws IllegalArgumentException If [slot] is currently free or if there
     * is no such [slot] in this [Equipment].
     */
    fun unequip(reality: Reality, slot: Equipment.Slot) {
        validateAllowedSlot(slot)
        val content = worn[slot]!!
        val item = content.item
        content.free()
        reality.sendStimulus(Equipment.Unequip(reality.hostOf(this), slot, item))
    }

    private fun validateAllowedSlot(slot: Equipment.Slot) {
        argumentsConstraint(
            worn.containsKey(slot),
            { "slot must be one of ${worn.keys}; it is $slot" }
        )
    }


    /**
     * Returns what is currently worn in [slot].
     * @throws
     */
    fun worn(slot: Equipment.Slot): UniqueItem? {
        validateAllowedSlot(slot)
        return worn[slot]!!.item
    }

    fun isOccupied(slot: Equipment.Slot): Boolean {
        validateAllowedSlot(slot)
        return worn[slot]!!.isOccupied()
    }

    interface Slot {
        val name: String
    }

    private class SlotContent {
        private var _item: UniqueItem? = null

        fun occupyWith(newContent: UniqueItem) {
            if (_item != null) {
                throw IllegalArgumentException(
                    "SlotContent must be unoccupied " +
                        "in order to set an item to it"
                )
            }
            _item = newContent
        }

        val item: UniqueItem
            get() {
                validateHasItem()
                return _item!!
            }

        fun free() {
            validateHasItem()
            _item = null
        }

        private fun validateHasItem() {
            if (_item == null) {
                throw IllegalArgumentException(
                    "Trying to free an already empty SlotContent"
                )
            }
        }

        fun isOccupied(): Boolean =
            _item != null
    }

    class Equip(
        val host: RealThing,
        val slot: Equipment.Slot,
        val item: UniqueItem
    ) : Stimulus(kind) {
        companion object {
            val kind = StimulusKind("equip")
        }
    }

    class Unequip(
        val host: RealThing,
        val slot: Equipment.Slot,
        val item: UniqueItem
    ) : Stimulus(kind) {
        companion object {
            val kind = StimulusKind("unequip")
        }
    }
}
