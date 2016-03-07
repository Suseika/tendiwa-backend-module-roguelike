package org.tendiwa.backend.modules.roguelike.archetypes

import org.tendiwa.backend.modules.roguelike.aspects.*
import org.tendiwa.backend.space.aspects.Position

interface Character {
    val position: Position
    val personalName: Name
    val weight: Weight
    val health: Health
    val NPCVision: NPCVision
    fun defaultVision() = NPCVision()
}
