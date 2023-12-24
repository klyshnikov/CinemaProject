package ru.hse.cinema.project.dao

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import ru.hse.cinema.project.dao.exceptions.TimeNoMatchException
import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.dao.interfaces.SessionDao
import ru.hse.cinema.project.models.Cinema
import ru.hse.cinema.project.models.Client
import ru.hse.cinema.project.models.Session
import java.text.SimpleDateFormat
import java.time.LocalDate
import kotlin.io.path.Path

class FileSystemSessionDao : SessionDao {

    companion object {
        private const val DIR_WITH_SESSIONS = "/home/mihail/javaProjects/CinemaProject/src/main/kotlin/ru/hse/cinema/project/resourses/cinema"
        private val jsonMapper = ObjectMapper()
    }

    init {
        jsonMapper.registerModule(JavaTimeModule())
        jsonMapper.setDateFormat(SimpleDateFormat("dd-MM-yyyy"))
        jsonMapper.setDateFormat(SimpleDateFormat("dd-MM-yyyy"))
    }

    override fun getEmptySeats(sessionName: String) : ArrayList<Seat> {
        val emptySeats : ArrayList<Seat> = arrayListOf()

        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)

        for (i in 0..<session.seats.size) {
            if (session.seats[i].isEmpty) {
                session.seats[i].let { emptySeats.add(it) }
            }
        }

        return emptySeats
    }

    override fun getBusySeats(sessionName: String) : ArrayList<Seat> {
        val busySeats : ArrayList<Seat> = arrayListOf()

        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)

        for (i in 0..<session.seats.size) {
            if (!session.seats[i].isEmpty) {
                session.seats[i].let { busySeats.add(it) }
            }
        }

        return busySeats
    }

    override fun isSeatEmpty(sessionName: String, seatNumber: Int): Boolean {
        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
        val seat = HelpMethods.getSeatByNumber(session, seatNumber)
        return seat.isEmpty
    }

    override fun setSeatClient(clientName: String, sessionName: String, seatNumber: Int) {
        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
        val seat = HelpMethods.getSeatByNumber(session, seatNumber)
        seat.client = Client(clientName)
        seat.isEmpty = false
        session.seats[seatNumber] = seat
        HelpMethods.setSessionByName(DIR_WITH_SESSIONS, session)
    }

    override fun removeSeatClient(sessionName: String, seatNumber: Int) {
        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
        val seat = HelpMethods.getSeatByNumber(session, seatNumber)
        seat.isEmpty = true
        session.seats[seatNumber] = seat
        HelpMethods.setSessionByName(DIR_WITH_SESSIONS, session)
    }

    override fun setTime(sessionName: String, startTime: LocalDate, endTime: LocalDate) {
        if (startTime.isAfter(endTime)) {
            throw TimeNoMatchException("Start time should be less than end time")
        }
        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
        session.start = startTime
        session.end = endTime
        HelpMethods.setSessionByName(DIR_WITH_SESSIONS, session)
    }

    override fun getTimeStart(sessionName : String): LocalDate {
        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
        return session.start
    }

    override fun getTimeEnd(sessionName: String): LocalDate {
        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
        return session.end
    }

    override fun getClientBooks(clientName : String): ArrayList<Pair<Session, Seat>> {
        val result : ArrayList<Pair<Session, Seat>> = arrayListOf()
        var files : ArrayList<String> = arrayListOf()
        val dir = Path(DIR_WITH_SESSIONS).toFile()
        for (file in dir.listFiles()) {
            val splitPath = file.toString().split("/")
            files.add(splitPath.last())
        }

        for (sessionName in files) {
            val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
            for (seat in session.seats) {
                if (!seat.isEmpty && seat.client?.name == clientName) {
                    result.add(Pair(session, seat))
                }
            }
        }

        return result
    }
}