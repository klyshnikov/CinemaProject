package ru.hse.cinema.project.api

import ru.hse.cinema.project.dao.FileSystemCinemaDao
import ru.hse.cinema.project.dao.FileSystemClientDao
import ru.hse.cinema.project.dao.FileSystemSessionDao
import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.models.Session
import ru.hse.cinema.project.service.BookServiceImpl
import ru.hse.cinema.project.service.RedactorServiceImpl
import ru.hse.cinema.project.service.SurveyServiceImpl
import ru.hse.cinema.project.service.interfaces.BookService
import ru.hse.cinema.project.service.interfaces.RedactorService
import ru.hse.cinema.project.service.interfaces.SurveyService
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ConsoleApi {
    var isRunning : Boolean = true

    private val bookService : BookService = BookServiceImpl(FileSystemSessionDao(), FileSystemClientDao(), FileSystemCinemaDao())
    private val redactorService : RedactorService = RedactorServiceImpl(FileSystemSessionDao(), FileSystemCinemaDao())
    private val surveyService : SurveyService = SurveyServiceImpl(FileSystemSessionDao(), FileSystemCinemaDao())

    private var userName: String = "Steve"
    private var startTime = LocalDate.now()
    private var endTime: LocalDate = LocalDate.now()
    private var session: Session = Session(startTime, endTime, arrayListOf(Seat(0, true, null)), "Shreck", 1)

    fun help() : String {
        val response : String = "Commands:\n\n" +
                "Preparation:\n" +
                "set-name <name>\n" +
                "set-start-time <day>-<month>-<year> <hour>:<minute>\n" +
                "set-end-time <day>-<month>-<year> <hour>:<minute>\n" +
                "set-session <name> <number of seats>\n\n" +
                "Booking:\n" +
                "book <session name> <seat number>\n" +
                "cansel-book <session name> <seat number>\n\n" +
                "Redacting:\n" +
                "add-session\n" +
                "remove-session <session name>\n" +
                "set-time <session name>\n\n" +
                "Survey:\n" +
                "get-empty <session name>" +
                "get-books"
        return response
    }

    fun parceCommand(content : String) {
        val line = content.split(" ")
        val command = line[0]

        if (command == "set-name") {
            setName(line)
        } else if (command == "set-start-time") {
            setStartTime(line)
        } else if (command == "set-end-time") {
            setEndTime(line)
        } else if (command == "set-session") {
            setSession(line)
        } else if (command == "book") {
            book(line)
        } else if (command == "cansel-book") {
            canselBook(line)
        } else if (command == "add-session") {
            addSession(line)
        } else if (command == "remove-session") {
            removeSession(line)
        } else if (command == "set-time") {
            setTime(line)
        } else if (command == "get-empty") {
            getEmpty(line)
        } else if (command == "get-books") {
            getBooks(line)
        } else if (command == "help" || command == "-help" || command == "--help") {
            help()
        } else if (command == "exit") {
            isRunning = false
        } else {
            println("help")
        }
    }

    fun setName(params : List<String>) {
        userName = params[1]
    }

    fun setStartTime(params : List<String>) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        startTime = LocalDate.parse(params[1], dateFormatter)
    }

    fun setEndTime(params : List<String>) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        endTime = LocalDate.parse(params[1], dateFormatter)
    }

    fun setSession(params: List<String>) {
        var seats : ArrayList<Seat> = arrayListOf()
        val seatsNumber = params[2].toInt()
        for (i in 0..<seatsNumber) {
            seats.add(Seat(i, true, null))
        }
        session = Session(startTime, endTime, seats, params[1], seatsNumber)
    }

    fun book(params: List<String>) {
        val sessionName = params[1]
        val seatNumber = params[2].toInt()
        bookService.book(userName, sessionName, seatNumber)
    }

    fun canselBook(params: List<String>) {
        bookService.canselBook(userName, params[1], params[2].toInt())
    }

    fun addSession(params: List<String>) {
        redactorService.addSession(session)
    }

    fun removeSession(params: List<String>) {
        redactorService.removeSession(params[1])
    }

    fun setTime(params: List<String>) {
        redactorService.setTime(params[1], startTime, endTime)
    }

    fun getEmpty(params: List<String>) {
        val seats = surveyService.getEmptySeats(params[1])
        println("")
        for (seat in seats) {
            print(seat.number)
        }
    }

    fun getBooks(params : List<String>) {
        println(surveyService.getClientBooks(userName))
    }

}