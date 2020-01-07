package org.inego.takeoffrun.server.service.fileload.language

import org.inego.takeoffrun.common.language.inflection.InflectionTreeLeaf
import org.inego.takeoffrun.common.language.inflection.InflectionTreeNode
import org.inego.takeoffrun.server.utils.StringAny

fun parseInflectionTreeNode(yaml: Any): InflectionTreeNode {
    if (yaml is String) {
        return InflectionTreeLeaf(yaml)
    }
    @Suppress("UNCHECKED_CAST")
    for ((key, value) in yaml as StringAny) {
        
    }

}