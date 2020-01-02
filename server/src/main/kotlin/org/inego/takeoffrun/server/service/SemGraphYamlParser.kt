package org.inego.takeoffrun.server.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.apache.logging.log4j.kotlin.logger
import org.inego.takeoffrun.common.sem.concrete.*
import org.inego.takeoffrun.common.sem.concrete.impl.SemanticGraphImpl
import org.inego.takeoffrun.common.sem.ontology.Ontology
import org.inego.takeoffrun.common.sem.ontology.Relation
import org.inego.takeoffrun.common.sem.ontology.RelationSlot
import org.inego.takeoffrun.common.sem.ontology.impl.MonoRelation
import org.inego.takeoffrun.common.sem.ontology.impl.SymmetricalRelation
import org.inego.takeoffrun.server.utils.StringAny
import org.inego.takeoffrun.server.utils.toMaybePairOfInts

private val log = logger("SemGraphYamlParser")

class SemGraphYamlParser(private val ontology: Ontology) {

    private val yamlFactory = YAMLFactory()
    private val objectMapper = ObjectMapper(yamlFactory)

    fun parseYaml(yaml: String): SemanticGraph {

        @Suppress("UNCHECKED_CAST")
        val readValue = objectMapper.readValue(yaml, Any::class.java) as StringAny

        val edges = arrayListOf<GraphEdge>()

        var maxNode = -1

        fun Int.check(): Int {
            if (this > maxNode) {
                maxNode = this
            }
            return this
        }

        for ((key, value) in readValue.entries) {
            val relation = ontology.findRelationByName(key)
            if (relation == null) {
                log.error("Cannot find relation $key")
                continue
            }

            when (relation) {
                is MonoRelation -> {
                    if (value is Int) {
                        edges.add(MonoEdge(relation, value.check()))
                    } else {
                        unsupported(relation, value)
                    }
                }
                is SymmetricalRelation -> {
                    val maybePairOfInts = toMaybePairOfInts(value)
                    if (maybePairOfInts == null) {
                        unsupported(relation, value)
                    } else {
                        edges.add(SymmetricalEdge(relation, maybePairOfInts.first.check(), maybePairOfInts.second.check()))
                    }
                }
                else -> {
                    // Some other relation

                    if (value is List<*>) {
                        var curIdx = 0
                        val slotTargets = hashMapOf<RelationSlot, SlotTarget>()
                        for (slot in relation.slots) {

                            if (slot.isMono) {
                                if (curIdx >= value.size) {
                                    unsupported(relation, value)
                                }
                                val target = value[curIdx]
                                if (target is Int) {
                                    slotTargets[slot] = SlotTarget.Mono(target.check())
                                } else {
                                    unsupported(relation, value)
                                }
                                curIdx++
                            } else {
                                unsupported(relation, value)
                            }
                        }
                        if (curIdx != value.size) {
                            unsupported(relation, value)
                        }
                        edges.add(GraphEdgeImpl(relation, slotTargets))
                    } else {
                        unsupported(relation, value)
                    }
                }
            }
        }

        return SemanticGraphImpl(maxNode + 1, edges)
    }

    private fun unsupported(relation: Relation, value: Any): Nothing {
        error("Unsupported value $value for relation $relation")
    }
}