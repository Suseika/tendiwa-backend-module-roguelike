package org.tendiwa.backend.modules.roguelike.aspects

import org.tendiwa.existence.NoInitAspect
import org.tendiwa.existence.NoReactionAspect
import org.tendiwa.existence.NoStimuliAspect

class Volume(
    val units: Int
) : NoReactionAspect, NoInitAspect, NoStimuliAspect
