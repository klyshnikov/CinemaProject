package ru.hse.cinema.project

import ru.hse.cinema.project.api.ApiManager
import ru.hse.cinema.project.api.ConsoleApi


fun main() {
    val api = ConsoleApi()
    val apiManager = ApiManager(api)

    println("commands - команды")
    println("help - расширенное описание команд")
    var input : String = ""

    while (api.isRunning) {
        input = readlnOrNull().toString()
        apiManager.parceCommand(input)
    }
}