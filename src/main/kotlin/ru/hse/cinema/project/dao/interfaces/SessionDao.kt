package ru.hse.cinema.project.dao.interfaces

import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.models.Session
import java.time.LocalDate

interface SessionDao {
    fun getEmptySeats(sessionName: String) : ArrayList<Seat>
    fun getBusySeats(sessionName: String) : ArrayList<Seat>

    fun isSeatEmpty(sessionName: String, seatNumber: Int) : Boolean
    fun setSeatClient(clientName: String, sessionName: String, seatNumber: Int)
    fun removeSeatClient(sessionName: String, seatNumber: Int)
    fun setTime(sessionName: String, startTime: LocalDate, endTime: LocalDate)

    fun getTimeStart(sessionName : String) : LocalDate
    fun getTimeEnd(sessionName : String) : LocalDate
    fun getClientBooks(clientName : String) : ArrayList<Pair<Session, Seat>>
}