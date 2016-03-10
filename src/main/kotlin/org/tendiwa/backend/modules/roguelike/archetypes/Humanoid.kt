package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.existence.RealThing
import org.tendiwa.backend.modules.roguelike.aspects.Equipment
import org.tendiwa.backend.modules.roguelike.aspects.HumanoidIntelligence
import org.tendiwa.backend.modules.roguelike.aspects.Inventory

interface Humanoid : Character {
    fun initHumanoid(realThing: RealThing) {
        initCharacter(realThing)
        realThing.apply {
            addAspect(Equipment(EquipmentSlot.values().toList()))
            addAspect(Inventory())
            addAspect(HumanoidIntelligence())
        }
    }

    enum class EquipmentSlot : Equipment.Slot {
        HEAD, BODY, LEGS, FEET, MAIN_HAND, OFF_HAND, AMULET, RING1, RING2
    }
}

