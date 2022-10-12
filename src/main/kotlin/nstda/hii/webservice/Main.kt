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

package nstda.hii.webservice

import nstda.hii.webservice.webconfig.server
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option

/**
 * Main application
 */
class Main(val args: Array<String>) {

    @Option(name = "-port", usage = "port destination to start nstda.hii.webservice.webconfig.server")
    private var port = 8080

    init {
        try {
            CmdLineParser(this).parseArgument(*args)
        } catch (cmd: CmdLineException) {
        }
    }

    fun start() {
        val server = server("0.0.0.0", port)
        logger.info { "running port $port" }
        server.setRequestLog { request, response ->
            logger.info {
                var message =
                    "HttpLog\t" + "Time:${System.currentTimeMillis()}\t" + "Status:${response.status}\t" + "Proto:${request.method}::" + request.originalURI
                request.headerNames.toList().forEach { key ->
                    if (key != "Authorization") message += "\t$key:${request.getHeader(key)}"
                }
                message += "\tInputIpAddress:${request.remoteAddr}"
                message
            }
        }
        server.start()
        server.join()
    }

    companion object {
        private val logger = getLogger()

        @JvmStatic
        fun main(args: Array<String>) {
            Main(args).start()
        }
    }
}
