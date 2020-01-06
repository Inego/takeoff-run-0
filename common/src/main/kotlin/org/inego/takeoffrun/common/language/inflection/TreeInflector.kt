package org.inego.takeoffrun.common.language.inflection

import org.inego.takeoffrun.common.language.GrammaticalFeatureValue

class TreeInflector(private val formProvider: FormProvider, private val root: InflectionTreeNode) : Inflector {

    override fun inflect(features: Collection<GrammaticalFeatureValue>): String {
        var currentNode = root
        var template = currentNode.getString()

        while (template == null) {
            currentNode = currentNode.down(features)
            template = currentNode.getString()
        }

        return formProvider.provide(template)
    }
}
