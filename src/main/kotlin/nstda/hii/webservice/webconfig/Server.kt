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

import org.eclipse.jetty.server.Connector
import org.eclipse.jetty.server.LowResourceMonitor
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.util.thread.QueuedThreadPool

/**
 * ตั้งค่าการทำงานของ jetty http nstda.hii.webservice.webconfig.server
 */
fun server(host: String, port: Int): Server {
    return Server(threadPool).apply {
        handler = ServletBuilder.build()
        connectors = connectorFor(this, host, port)
        addBean(lowResourceMonitorFor(this))
    }
}

private const val MAX_THREADS = 500
private const val MIN_THREADS = 50
private const val IDLE_TIMEOUT = 6000

private val threadPool: QueuedThreadPool
    get() {
        val threadPool = QueuedThreadPool(
            MAX_THREADS,
            MIN_THREADS,
            IDLE_TIMEOUT
        )
        threadPool.isDaemon = true
        threadPool.isDetailedDump = false
        return threadPool
    }

private fun lowResourceMonitorFor(server: Server): LowResourceMonitor {
    val monitor = LowResourceMonitor(server)
    monitor.period = 1000
    monitor.lowResourcesIdleTimeout = 1000
    monitor.monitorThreads = true
    monitor.maxMemory = 0
    monitor.maxLowResourcesTime = 5000
    return monitor
}

private fun connectorFor(server: Server, host: String, port: Int): Array<Connector> {
    val connector = ServerConnector(server)
    connector.host = host
    connector.port = port
    connector.idleTimeout = 30000
    connector.acceptQueueSize = 3000
    return arrayOf(connector)
}
