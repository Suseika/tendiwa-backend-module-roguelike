package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind

class Name(
    string: String
) : NoReactionAspect(kind) {
    companion object {
        val kind = NoStimuliAspectKind()
    }

    var string: String =
        string
        get() = field
        private set(value) {
            field = value
        }
}
