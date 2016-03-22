package org.tendiwa.backend.modules.roguelike.playerVolition

import org.tendiwa.backend.modules.roguelike.archetypes.Item
import org.tendiwa.backend.modules.roguelike.aspects.inventory
import org.tendiwa.frontend.generic.PlayerVolition

fun PlayerVolition.pickUp(item: Item) {
    item.removeFromSpace(reality)
    this.host.inventory.store(reality, item)
}
