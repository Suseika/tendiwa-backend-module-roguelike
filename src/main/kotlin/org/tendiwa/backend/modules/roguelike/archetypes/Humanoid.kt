package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.DeclaredAspect
import org.tendiwa.backend.modules.roguelike.aspects.Equipment
import org.tendiwa.backend.modules.roguelike.aspects.HumanoidIntelligence
import org.tendiwa.backend.modules.roguelike.aspects.Inventory

interface Humanoid : Character {
    @DeclaredAspect
    fun equipment() = Equipment(EquipmentSlot.values().toList())

    @DeclaredAspect
    fun inventory() = Inventory()

    @DeclaredAspect
    fun intelligence() = HumanoidIntelligence()

    enum class EquipmentSlot : Equipment.Slot {
        HEAD, BODY, LEGS, FEET, MAIN_HAND, OFF_HAND, AMULET, RING1, RING2
    }
}

