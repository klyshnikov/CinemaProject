package ru.hse.cinema.project.service.interfaces

import ru.hse.cinema.project.dao.interfaces.CinemaDao
import ru.hse.cinema.project.dao.interfaces.ClientDao
import ru.hse.cinema.project.dao.interfaces.SessionDao
import ru.hse.cinema.project.models.Cinema

interface BookService {
    val sessionDao : SessionDao
    val clientDao: ClientDao
    val cinemaDao : CinemaDao

    fun book(clientName: String, sessionName: String, seatNumber: Int) : Boolean

    fun canselBook(clientName: String, sessionName: String, seatNumber: Int) : Boolean
}