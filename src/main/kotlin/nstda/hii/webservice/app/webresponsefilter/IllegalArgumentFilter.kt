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
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class IllegalArgumentFilter : ExceptionMapper<IllegalArgumentException> {
    override fun toResponse(exception: IllegalArgumentException): Response {
        val message = with(exception.message) {
            if (this != null && endsWith("Sex.NULL")) {
                "sex รองรับการใส่ข้อมูลด้วย MALE FEMALE UNKNOWN เป็นตัวใหญ่ทั้งหมดเท่านั้น"
            } else {
                exception.message
            }
        }
        return ErrorDetail.build(WebApplicationException(message, exception, 400))
    }
}
