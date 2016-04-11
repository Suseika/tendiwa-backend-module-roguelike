package org.tendiwa.backend.modules.roguelike.aspects

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.tendiwa.backend.by
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.space.walls.WallType
import org.tendiwa.backend.testing.MockRealThing
import org.tendiwa.backend.testing.inMockReality
import org.tendiwa.plane.grid.dimensions.by
import kotlin.test.assertFalse

class PassabilityTest {
    @JvmField @Rule val expectRule = ExpectedException.none()

    @Test
    fun `requires Position aspect`() {
        expectRule.apply {
            expectMessage("does not have Aspect")
            expectMessage("Position")
        }
        inMockReality (
            things = listOf(
                MockRealThing(
                    Passability()
                )
            )
        ) {}
    }

    @Test
    fun `tiles with nothing in them are passable`() {
        val thing = MockRealThing(
            Position(Voxel(0, 0, 0)),
            Passability()
        )
        inMockReality (
            size = 2 by 2 by 1,
            things = listOf(thing)
        ) {
            assert(thing.aspect<Passability>().isTilePassable(1, 1))
        }
    }

    @Test
    fun `treats walls as unpassable`() {
        val thing = MockRealThing(
            Position(Voxel(0, 0, 0)),
            Passability()
        )
        inMockReality (
            size = 2 by 2 by 1,
            things = listOf(thing)
        ) {
            reality.space.walls
                .planeAtZ(0)
                .chunkWithTile(1, 1)
                .setWall(1, 1, WallType("some wall"))
            assertFalse(thing.aspect<Passability>().isTilePassable(1, 1))
        }
    }

    @Test
    fun `treats tiles outside layer bounds as unpassable`() {
        val thing = MockRealThing(
            Position(Voxel(0, 0, 0)),
            Passability()
        )
        inMockReality(
            things = listOf(thing)
        ) {
            assertFalse(thing.aspect<Passability>().isTilePassable(-1, -1))
        }
    }

    @Test
    fun `current tile is always passable`() {
        val thing = MockRealThing(
            Position(Voxel(0, 0, 0)),
            Passability()
        )
        inMockReality (
            things = listOf(thing)
        ) {
            reality.space.walls
                .planeAtZ(0)
                .chunkWithTile(0, 0)
                .setWall(0, 0, WallType("some wall"))
            assert(thing.aspect<Passability>().isTilePassable(0, 0))
        }
    }
}
