package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspectKind
import org.tendiwa.existence.RealThing

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

val RealThing.name: Name
    get() = aspects[Name.kind] as Name
