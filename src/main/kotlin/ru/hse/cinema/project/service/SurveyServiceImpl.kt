package ru.hse.cinema.project.service

import ru.hse.cinema.project.dao.interfaces.CinemaDao
import ru.hse.cinema.project.dao.interfaces.SessionDao
import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.models.Session
import ru.hse.cinema.project.service.interfaces.SurveyService

class SurveyServiceImpl (
    override val sessionDao : SessionDao,
    override val cinemaDao: CinemaDao
) : SurveyService {
    override fun getEmptySeats(sessionName: String) : ArrayList<Seat> {
        return sessionDao.getEmptySeats(sessionName)
    }

    override fun getClientBooks(clientName: String): ArrayList<Pair<Session, Seat>> {
        return sessionDao.getClientBooks(clientName)
    }
}