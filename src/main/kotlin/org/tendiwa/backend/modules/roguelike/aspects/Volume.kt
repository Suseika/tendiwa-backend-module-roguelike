package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind

class Volume(
    val units: Int
) : NoReactionAspect(kind) {
    companion object {
        val kind = NoStimuliAspectKind()
    }
}
