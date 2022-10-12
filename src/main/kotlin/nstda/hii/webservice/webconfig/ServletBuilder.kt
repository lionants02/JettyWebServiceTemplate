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

package nstda.hii.webservice.webconfig

import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.glassfish.jersey.servlet.ServletContainer

internal object ServletBuilder {

    private const val PATH_SPEC = "/v1/*"
    private const val ROOT_PATH = ""

    fun build(): ServletContextHandler {
        return ServletContextHandler(ServletContextHandler.SESSIONS).apply {
            contextPath = ROOT_PATH
            addServlet(thingServletHolder(), PATH_SPEC)
        }
    }

    private fun thingServletHolder(): ServletHolder {
        return ServletHolder(ServletContainer(ServletResourceConfig())).apply {
            initOrder = 0
        }
    }
}
