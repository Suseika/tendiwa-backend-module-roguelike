package org.tendiwa.backend.modules.roguelike.aspects

import org.junit.Test
import org.tendiwa.backend.existence.AbstractRealThing
import org.tendiwa.backend.modules.roguelike.archetypes.BundleItem
import org.tendiwa.backend.modules.roguelike.archetypes.UniqueItem
import org.tendiwa.backend.space.aspects.Name
import org.tendiwa.backend.testing.MockRealThing
import org.tendiwa.backend.testing.inMockReality
import kotlin.test.assertEquals

class InventoryTest {
    @Test
    fun `stores items`() {
        val inventory = Inventory()
        val axe = Axe()
        inMockReality (
            things = listOf(
                MockRealThing(inventory),
                axe
            )
        ) {
            inventory.apply {
                store(reality, axe)
                assert(items.contains(axe))
                assert(
                    trappedStimuli.any {
                        it is Inventory.Store && it.item == axe
                    }
                )
            }
        }
    }

    @Test
    fun `removes items`() {
        val inventory = Inventory()
        val axe = Axe()
        inMockReality (
            things = listOf(
                MockRealThing(inventory),
                axe
            )
        ) {
            inventory.apply {
                store(reality, axe)
                remove(reality, axe)
                assert(items.isEmpty())
                assert(
                    trappedStimuli.any {
                        it is Inventory.Lose && it.item == axe
                    }
                )
            }
        }
    }

    @Test
    fun `adds new bundle items`() {
        val inventory = Inventory()
        val coins = Coin(BunchSize(10))
        inMockReality (
            things = listOf(
                MockRealThing(inventory),
                coins
            )
        ) {
            inventory.apply {
                store(reality, coins)
                assert(items.isNotEmpty())
                assert(trappedStimuli.any {
                    it is Inventory.Store && it.item == coins
                })
            }
        }
    }

    @Test
    fun `adds bundle items to an existing bundle`() {
        val inventory = Inventory()
        val coins1 = Coin(BunchSize(10))
        val coins2 = Coin(BunchSize(10))
        inMockReality (
            things = listOf(
                MockRealThing(inventory),
                coins1,
                coins2
            )
        ) {
            inventory.apply {
                store(reality, coins1)
                store(reality, coins2)
                assertEquals(1, items.size)
                assert(trappedStimuli.any {
                    it is Inventory.Store && it.item == coins2
                })
                assert(trappedStimuli.any {
                    it is BunchSize.Change && it.new == 20
                })
            }
        }
    }

    @Test
    fun `removes existing complete bundles`() {
        val inventory = Inventory()
        val coins = Coin(BunchSize(10))
        inMockReality (
            things = listOf(
                MockRealThing(inventory),
                coins
            )
        ) {
            inventory.apply {
                store(reality, coins)
                remove(reality, coins)
                assert(items.isEmpty())
                assert(trappedStimuli.any {
                    it is Inventory.Lose && it.item == coins
                })
            }
        }
    }

    class Axe : AbstractRealThing(), UniqueItem {
        override fun volume() = Volume(1)

        override fun weight() = Weight(1)

        override fun name(): Name = Name("axe")
    }

    class Coin(
        bunchSize: BunchSize
    ) : AbstractRealThing(bunchSize), BundleItem {
        override fun name() = Name("coin")

        override fun volume() = Volume(1)

        override fun weight() = Weight(1)

        override fun unitVolume() = 1

        override fun unitWeight() = 1

        override fun bunchSize() = parameterAspect<BunchSize>()
    }

}
