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

package nstda.hii.webservice.app.webresponsefilter

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import nstda.hii.webservice.getLogger

/**
 * เวลาเกิด Error จะส่ง Object นี้ response ไปยัง client
 */
class ErrorDetail(
    val code: Int,
    val message: String?,
    t: Throwable
) {
    val tType = t::class.java.simpleName

    init {
        val logger = getLogger()
        logger.debug("${t.message}", t)
    }

    companion object {
        fun build(ex: WebApplicationException): Response {
            val err = ErrorDetail(ex.response.status, ex.message, ex)
            return Response.status(err.code).entity(err).type(MediaType.APPLICATION_JSON_TYPE).build()
        }

        fun build(ex: WebApplicationException, t: Throwable): Response {
            val err = ErrorDetail(ex.response.status, ex.message, t)
            return Response.status(err.code).entity(err).type(MediaType.APPLICATION_JSON_TYPE).build()
        }
    }
}
