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

open class Api {
    var isRunning : Boolean = true

    protected val bookService : BookService = BookServiceImpl(FileSystemSessionDao(), FileSystemClientDao(), FileSystemCinemaDao())
    protected val redactorService : RedactorService = RedactorServiceImpl(FileSystemSessionDao(), FileSystemCinemaDao())
    protected val surveyService : SurveyService = SurveyServiceImpl(FileSystemSessionDao(), FileSystemCinemaDao())

    protected var userName: String = "Steve"
    protected var startTime = LocalDate.now()
    protected var endTime: LocalDate = LocalDate.now()
    protected var session: Session = Session(startTime, endTime, arrayListOf(Seat(0, true, null)), "Shreck", 1)

    open fun help() : String {
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


    open fun setName(params : List<String>) {
        userName = params[1]
    }

    open fun setStartTime(params : List<String>) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        startTime = LocalDate.parse(params[1], dateFormatter)
    }

    open fun setEndTime(params : List<String>) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        endTime = LocalDate.parse(params[1], dateFormatter)
    }

    open fun setSession(params: List<String>) {
        var seats : ArrayList<Seat> = arrayListOf()
        val seatsNumber = params[2].toInt()
        for (i in 0..<seatsNumber) {
            seats.add(Seat(i, true, null))
        }
        session = Session(startTime, endTime, seats, params[1], seatsNumber)
    }

    open fun book(params: List<String>) {
        val sessionName = params[1]
        val seatNumber = params[2].toInt()
        bookService.book(userName, sessionName, seatNumber)
    }

    open fun canselBook(params: List<String>) {
        bookService.canselBook(userName, params[1], params[2].toInt())
    }

    open fun addSession(params: List<String>) {
        redactorService.addSession(session)
    }

    open fun removeSession(params: List<String>) {
        redactorService.removeSession(params[1])
    }

    open fun setTime(params: List<String>) {
        redactorService.setTime(params[1], startTime, endTime)
    }

    open fun getEmpty(params: List<String>) : List<Seat> {
        return surveyService.getEmptySeats(params[1])
    }

    open fun getBooks(params : List<String>) : List<Pair<Session, Seat>> {
        return surveyService.getClientBooks(userName)
    }

    open fun callHelp() : String {
        return "help"
    }

    open fun callException(e : String) : String {
        return e
    }
}