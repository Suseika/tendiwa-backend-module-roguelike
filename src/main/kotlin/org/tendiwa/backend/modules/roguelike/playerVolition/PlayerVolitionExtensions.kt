package org.tendiwa.backend.modules.roguelike.playerVolition

import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.aspects.inventory
import org.tendiwa.backend.space.Voxel
import org.tendiwa.frontend.generic.PlayerVolition

fun PlayerVolition.pickUp(item: Item) {
    item.removeFromSpace(reality)
    this.host.inventory.store(reality, item)
}

/**
 * Drops an item from player's [Inventory].
 */
fun PlayerVolition.drop(item: Item, voxel: Voxel) {
    this.host.inventory.remove(reality, item)
    item.addToSpace(reality, voxel)
}
