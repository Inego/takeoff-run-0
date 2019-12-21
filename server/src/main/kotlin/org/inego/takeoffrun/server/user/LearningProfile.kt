package org.inego.takeoffrun.server.user

import org.inego.takeoffrun.common.language.Language


interface LearningProfile {
    val user: User
    val language: Language
}