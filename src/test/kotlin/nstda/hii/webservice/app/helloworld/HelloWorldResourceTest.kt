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

import nstda.hii.webservice.webconfig.server
import org.amshove.kluent.`should be equal to`
import org.eclipse.jetty.http.HttpStatus
import org.eclipse.jetty.server.Server
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection
import java.net.URL

internal class HelloWorldResourceTest {

    private lateinit var server: Server

    @BeforeEach
    fun setUp() {
        server = server("0.0.0.0", 8954)
        server.start()
    }

    @AfterEach
    fun tearDown() {
        try {
            server.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun hello() {
        val http: HttpURLConnection = URL("http://localhost:8954/v1/hello").openConnection() as HttpURLConnection
        http.connect()
        println("body response ${http.inputStream.reader().readText()}")
        http.responseCode `should be equal to` HttpStatus.OK_200
    }
}
