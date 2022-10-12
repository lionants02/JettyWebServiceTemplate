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

package nstda.hii.webservice.app.helloworld

import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import nstda.hii.webservice.app.webcache.Cache
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.ZonedDateTime

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class HelloWorldResource {

    // http://localhost:8080/v1/hello
    @GET
    @Cache(maxAge = 5)
    fun hello(): HelloMessage {
        val zoneDateTime = ZonedDateTime.now()
        val offsetDateTime = zoneDateTime.toOffsetDateTime()
        val localDateTime = zoneDateTime.toLocalDateTime()
        val instant = zoneDateTime.toInstant()

        return HelloMessage(
            "Hello World",
            zoneDateTime,
            localDateTime.toLocalTime(),
            localDateTime,
            localDateTime.toLocalDate(),
            offsetDateTime,
            offsetDateTime.toOffsetTime(),
            instant
        )
    }

    data class HelloMessage(
        val hello: String,
        val javaZonedDateTime: ZonedDateTime,
        val javaLocalTime: LocalTime,
        val javaDateTime: LocalDateTime,
        val javaDate: LocalDate,
        val javaOffsetDateTime: OffsetDateTime,
        val javaOffsetTime: OffsetTime,
        val javaInstant: Instant
    )
}
