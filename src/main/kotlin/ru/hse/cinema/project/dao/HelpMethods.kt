package ru.hse.cinema.project.dao

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import ru.hse.cinema.project.dao.exceptions.SeatLimitException
import ru.hse.cinema.project.dao.exceptions.SessionNotExistException
import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.models.Session
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import kotlin.io.path.Path

class HelpMethods {
    companion object {
        private val jsonMapper = ObjectMapper()
        init {
            jsonMapper.registerModule(JavaTimeModule())
            jsonMapper.setDateFormat(SimpleDateFormat("dd-MM-yyyy"));
        }

        fun getSessionByName(sessionName: String, sessionPath : String) : Session {
            val sessionFile = Path(sessionPath, sessionName).toFile()
            if (!sessionFile.exists()) {
                throw SessionNotExistException("Данного сеанса не существует")
            }
            val content = FileUtils.readFileToString(sessionFile, Charset.defaultCharset())
            val session = jsonMapper.readValue(content, Session::class.java)
            return session
        }

        fun setSessionByName(sessionPath: String, session : Session) {
            val sessionContent = jsonMapper.writeValueAsString(session)
            val sessionFile = Path(sessionPath, session.name).toFile()
            if (!sessionFile.exists()) {
                throw SessionNotExistException("Данного сеанса не существует")
            }
            sessionFile.writeText(sessionContent)
        }

        fun getSeatByNumber(session: Session, seatNumber: Int) : Seat {
            if (seatNumber >= session.seats.size) {
                throw SeatLimitException("Данного места не существует. Максимальный номер места ${session.seats.size-1}")
            }
            return session.seats[seatNumber]
        }
    }
}