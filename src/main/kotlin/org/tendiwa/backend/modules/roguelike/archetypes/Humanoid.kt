package org.tendiwa.backend.modules.roguelike.archetypes

import aspects.HumanoidIntelligence
import aspects.Inventory
import org.tendiwa.backend.modules.roguelike.aspects.Equipment

interface Humanoid : Character {
    val equipment: Equipment
    val inventory: Inventory
    val intelligence: HumanoidIntelligence

    fun defaultEquipment() = Equipment(EquipmentSlot.values().toList())

    fun defaultInventory() = Inventory()

    fun defaultIntelligence() = HumanoidIntelligence()

    enum class EquipmentSlot : Equipment.Slot {
        HEAD, BODY, LEGS, FEET, MAIN_HAND, OFF_HAND, AMULET, RING1, RING2
    }
}

