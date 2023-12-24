package ru.hse.cinema.project.api

class ApiManager(val api : Api) {
    fun parceCommand(content : String) {
        try {
            val line = content.split(" ")
            val command = line[0]

            if (command == "set-name") {
                api.setName(line)
            } else if (command == "set-start-time") {
                api.setStartTime(line)
            } else if (command == "set-end-time") {
                api.setEndTime(line)
            } else if (command == "set-session") {
                api.setSession(line)
            } else if (command == "book") {
                api.book(line)
            } else if (command == "cansel-book") {
                api.canselBook(line)
            } else if (command == "add-session") {
                api.addSession(line)
            } else if (command == "remove-session") {
                api.removeSession(line)
            } else if (command == "set-time") {
                api.setTime(line)
            } else if (command == "get-empty") {
                api.getEmpty(line)
            } else if (command == "get-books") {
                api.getBooks(line)
            } else if (command == "check") {
                api.check(line)
            } else if (command == "commands") {
                api.commands()
            } else if (command == "help") {
                api.help()
            } else if (command == "exit") {
                api.isRunning = false
            } else {
                api.callHelp()
            }
        }
        catch (e : Exception) {
            e.message?.let { api.callException(it) }
        }

    }
}