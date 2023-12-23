package ru.hse.cinema.project

import ru.hse.cinema.project.api.ConsoleApi


fun main() {
    val api = ConsoleApi()

    println("help")
    var input : String = ""


    while (api.isRunning) {
        input = readlnOrNull().toString()
        api.parceCommand(input)
    }
}