package org.inego.takeoffrun.server.service.fileload

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

private val yamlFactory = YAMLFactory()

val objectMapper = ObjectMapper(yamlFactory)
