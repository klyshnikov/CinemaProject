package ru.hse.cinema.project.service

import ru.hse.cinema.project.dao.interfaces.CinemaDao
import ru.hse.cinema.project.dao.interfaces.SessionDao
import ru.hse.cinema.project.models.Session
import ru.hse.cinema.project.service.interfaces.RedactorService
import java.time.LocalDate

class RedactorServiceImpl(
    override val sessionDao: SessionDao,
    override val cinemaDao: CinemaDao
) : RedactorService  {
    override fun addSession(session : Session) {
        cinemaDao.addSession(session)
    }

    override fun removeSession(sessionName : String) {
        cinemaDao.removeSession(sessionName)
    }

    override fun setTime(sessionName: String, startTime: LocalDate, endTime : LocalDate) {
        sessionDao.setTime(sessionName, startTime, endTime)
    }
}