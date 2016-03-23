package org.tendiwa.backend.modules.roguelike.playerVolition

import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.aspects.Inventory
import org.tendiwa.backend.space.Voxel
import org.tendiwa.frontend.generic.PlayerVolition

fun PlayerVolition.pickUp(item: Item) {
    item.removeFromSpace(reality)
    this.host.aspect<Inventory>().store(reality, item)
}

/**
 * Drops an item from player's [Inventory].
 */
fun PlayerVolition.drop(item: Item, voxel: Voxel) {
    this.host.aspect<Inventory>().remove(reality, item)
    item.addToSpace(reality, voxel)
}
