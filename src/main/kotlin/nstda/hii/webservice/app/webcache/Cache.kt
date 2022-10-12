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

import jakarta.ws.rs.NameBinding
import jakarta.ws.rs.core.CacheControl
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE
)
annotation class Cache(
    val maxAge: Int = -1,
    val private: Boolean = false,
    val noStore: Boolean = false,
    val noCache: Boolean = false,
    val mustRevalidate: Boolean = false,
    val noTransform: Boolean = false
)

fun Cache.toCacheControl(): CacheControl {
    val age = maxAge
    return CacheControl().apply {
        if (noStore) {
            isNoStore = true
            return@apply
        }
        isPrivate = private
        isNoCache = noCache
        isMustRevalidate = mustRevalidate
        isNoTransform = noTransform
        this.maxAge = age
    }
}

fun CacheControl.combineWith(request: CacheControl): CacheControl {
    if (this.isNoStore || request.isNoStore) {
        return CacheControl().apply {
            isNoTransform = false
            isNoStore = true
        }
    }
    if (request.isNoCache) isNoCache = true
    if (request.isMustRevalidate) isMustRevalidate = true
    if (request.isNoTransform) isNoTransform = true
    if (request.isPrivate) isPrivate = true
    if (maxAge > -1) {
        maxAge = request.maxAge.takeIf { -1 < it && it < maxAge } ?: maxAge
    }
    return this
}
