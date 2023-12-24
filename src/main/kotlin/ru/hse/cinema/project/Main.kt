package ru.hse.cinema.project

import ru.hse.cinema.project.api.ApiManager
import ru.hse.cinema.project.api.ConsoleApi


fun main() {
    val api = ConsoleApi()
    val apiManager = ApiManager(api)

    println("help")
    var input : String = ""

    while (api.isRunning) {
        input = readlnOrNull().toString()
        apiManager.parceCommand(input)
    }
}