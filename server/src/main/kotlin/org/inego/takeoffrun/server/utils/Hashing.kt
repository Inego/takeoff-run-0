

package org.inego.takeoffrun.server.utils

import com.google.common.hash.Hashing
import java.nio.ByteBuffer
import java.util.*


@Suppress("UnstableApiUsage")
fun hash(value: String): UUID {
    val hasher = Hashing.murmur3_128().newHasher()
    val hash = hasher.putUnencodedChars(value).hash()
    val hashBytes = hash.asBytes()
    val wrap = ByteBuffer.wrap(hashBytes)
    val high = wrap.long
    val low = wrap.long
    return UUID(high, low)
}