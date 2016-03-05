package org.tendiwa.backend.modules.roguelike.things

import org.tendiwa.backend.modules.roguelike.archetypes.Humanoid
import org.tendiwa.backend.modules.roguelike.aspects.Health
import org.tendiwa.backend.modules.roguelike.aspects.Name
import org.tendiwa.backend.modules.roguelike.aspects.Position
import org.tendiwa.backend.modules.roguelike.aspects.Weight
import org.tendiwa.existence.RealThing

class Human(
    override val position: Position,
    override val personalName: Name,
    override val weight: Weight,
    override val health: Health
) : RealThing(), Humanoid {
    override val equipment = defaultEquipment()
    override val inventory = defaultInventory()
    override val intelligence = defaultIntelligence()
    override val NPCVision = defaultVision()
}
