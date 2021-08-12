package com.pepper.racecondition

import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.net.InetAddress

@SpringBootApplication
class Application

private val logger = LogManager.getLogger(Application::class.java)

fun main(args: Array<String>) {
    val app = runApplication<Application>(*args)

    val applicationName = app.environment.getProperty("spring.application.name")
    val contextPath = app.environment.getProperty("server.servlet.context-path")
    val port = app.environment.getProperty("server.port")
    val hostAddress = InetAddress.getLocalHost().hostAddress

    logger.info("""|
                   |------------------------------------------------------------
                   |Application '$applicationName' is running! Access URLs:
                   |   Local:      http://127.0.0.1:$port$contextPath
                   |   External:   http://$hostAddress:$port$contextPath
                   |------------------------------------------------------------""".trimMargin())

}
