package ru.hse.cinema.project.service.interfaces

import ru.hse.cinema.project.dao.interfaces.CinemaDao
import ru.hse.cinema.project.dao.interfaces.SessionDao
import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.models.Session

interface SurveyService {
    val sessionDao : SessionDao
    val cinemaDao: CinemaDao

    fun getEmptySeats(sessionName: String) : ArrayList<Seat>
    fun getClientBooks(clientName : String) : ArrayList<Pair<Session, Seat>>
}