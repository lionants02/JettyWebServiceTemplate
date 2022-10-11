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
