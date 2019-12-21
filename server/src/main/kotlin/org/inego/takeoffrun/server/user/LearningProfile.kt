package org.inego.takeoffrun.server.user

import org.inego.takeoffrun.common.language.Language
import java.util.*


interface LearningProfile {
    val id: UUID
    val name: String
    val user: User
    val language: Language
}