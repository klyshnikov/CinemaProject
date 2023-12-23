package ru.hse.cinema.project.service.interfaces

import ru.hse.cinema.project.dao.interfaces.CinemaDao
import ru.hse.cinema.project.dao.interfaces.SessionDao
import ru.hse.cinema.project.models.Session
import java.time.LocalDate

interface RedactorService {
    val sessionDao: SessionDao
    val cinemaDao: CinemaDao

    fun addSession(session : Session)

    fun removeSession(sessionName : String)

    fun setTime(sessionName: String, startTime: LocalDate, endTime : LocalDate)
}