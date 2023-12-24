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

class ConsoleApi : Api() {
    override fun help() : String {
        println(super.help())
        return ""
    }

    override fun setName(params : List<String>) {
        super.setName(params)
        println("Имя установлено")
    }

    override fun setStartTime(params : List<String>) {
        super.setStartTime(params)
        println("Успешно")
    }

    override fun setEndTime(params : List<String>) {
        super.setEndTime(params)
        println("Успешно")
    }

    override fun setSession(params: List<String>) {
        super.setSession(params)
        println("Сеанс установлен")
    }

    override fun book(params: List<String>) {
        super.book(params)
        println("Успешно забронировано место ${params[2]} на сеанс ${params[1]}")
    }

    override fun canselBook(params: List<String>) {
        super.canselBook(params)
        println("Отменена запись на ${params[1]} место номер ${params[2]}")
    }

    override fun addSession(params: List<String>) {
        super.addSession(params)
        println("Сеанс успешно добавлен в базу")
    }

    override fun removeSession(params: List<String>) {
        super.removeSession(params)
        println("Сеанс был удален из базы")
    }

    override fun setTime(params: List<String>) {
        super.setTime(params)
        println("Время для сеанса ${params[1]} установлено")
    }

    override fun getEmpty(params: List<String>) : List<Seat> {
        val empty = super.getEmpty(params)
        println("Номера пустых мест на сеансе ${params[1]}:\n")
        for (seat in empty) {
            print(seat.number)
            print(" ")
        }
        return empty
    }

    override fun getBooks(params : List<String>) : List<Pair<Session, Seat>> {
        val books = super.getBooks(params)
        println("Текущие брони для данного клиента: ")
        for (bookPair in books) {
            println("Сеанс ${bookPair.first}, номер места ${bookPair.second}")
        }
        return books
    }

    override fun callHelp(): String {
        println(super.callHelp())
        return ""
    }

    override fun callException(e: String): String {
        println(super.callException(e))
        return ""
    }

}