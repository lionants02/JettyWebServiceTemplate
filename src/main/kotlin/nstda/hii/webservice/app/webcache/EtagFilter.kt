/*
 * MIT License
 *
 * Copyright (c) 2022 NSTDA
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nstda.hii.webservice.app.webcache

import jakarta.ws.rs.container.ContainerRequestContext
import jakarta.ws.rs.container.ContainerResponseContext
import jakarta.ws.rs.container.ContainerResponseFilter
import jakarta.ws.rs.core.EntityTag
import jakarta.ws.rs.ext.Provider
import nstda.hii.webservice.app.GsonJerseyProvider.Companion.hiiGson
import java.security.MessageDigest

@Provider
class EtagFilter : ContainerResponseFilter {

    override fun filter(request: ContainerRequestContext, response: ContainerResponseContext) {
        if (response.status != 200) return
        if (!response.hasEntity()) return

        val hash = Etag(response.entity).value
        val etag = EntityTag(hash)

        val responseBuilder = request.request.evaluatePreconditions(etag)
        if (responseBuilder != null) {
            response.status = 304
            response.entity = null
        }
        response.headers.add("ETag", "\"$hash\"")
    }
}

class Etag(content: Any) {
    val value: String = hiiGson.toJson(content).md5()
}

/**
 * From
 * https://github.com/kittinunf/Fuse/blob/master/fuse/src/main/kotlin/com/github/kittinunf/fuse/util/MD5.kt
 */
private fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val digested = md.digest(toByteArray())
    return digested.joinToString("") {
        String.format("%02x", it)
    }
}
