package org.tendiwa.backend.modules.roguelike.aspects

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.tendiwa.backend.by
import org.tendiwa.backend.existence.AbstractRealThing
import org.tendiwa.backend.existence.aspect
import org.tendiwa.backend.space.Voxel
import org.tendiwa.backend.space.aspects.Position
import org.tendiwa.backend.testing.AspectTestSuite
import org.tendiwa.backend.testing.MockRealThing
import org.tendiwa.plane.grid.constructors.GridRectangle
import org.tendiwa.plane.grid.dimensions.by
import org.tendiwa.plane.grid.tiles.Tile
import org.tendiwa.tools.expectIllegalArgument
import kotlin.test.assertEquals

class PathfindingTest {
    @JvmField @Rule val expectRule = ExpectedException.none()

    @Test
    fun `fails if host doesnt have position`() {
        expectRule.expectIllegalArgument("does not have Aspect")
        val thing = MockRealThing(Pathfinding())
        AspectTestSuite(
            things = listOf(thing)
        )
    }

    @Test
    fun `computes astar path`() {
        val pathfinder = Pathfinder(Voxel(0, 0, 0))
        AspectTestSuite(
            size = 10 by 10 by 1,
            things = listOf(pathfinder)
        )
            .apply {
                pathfinder.aspect<Pathfinding>()
                    .astarPath(Voxel(6, 6, 0))!!
                    .apply {
                        assertEquals(Tile(0, 0), start)
                        assertEquals(Tile(6, 6), end)
                    }
            }
    }

    @Test
    fun `computes flood`() {
        val pathfinder = Pathfinder(Voxel(5, 5, 0))
        AspectTestSuite(
            size = 10 by 10 by 1,
            things = listOf(pathfinder)
        ).apply {
            pathfinder
                .aspect<Pathfinding>()
                .flood()
                .apply {
                    assertEquals(
                        GridRectangle(10 by 10),
                        reachableMask.hull
                    )
                    assertEquals(
                        pathfinder.aspect<Position>().tile,
                        start
                    )
                }
        }
    }

    class Pathfinder(voxel: Voxel) : AbstractRealThing(
        Pathfinding(),
        Passability(),
        Position(voxel)
    )
}
