package ru.hse.cinema.project

import ru.hse.cinema.project.api.ApiManager
import ru.hse.cinema.project.api.ConsoleApi
import ru.hse.cinema.project.verefication.VereficatorImpl


fun main() {
    val vereficarot = VereficatorImpl()
    val api = ConsoleApi(vereficarot)
    val apiManager = ApiManager(api)

    println("commands - команды")
    println("help - расширенное описание команд")

    while (api.isRunning) {
        apiManager.run()
    }
}