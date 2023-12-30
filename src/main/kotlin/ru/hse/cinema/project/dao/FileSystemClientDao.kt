package ru.hse.cinema.project.dao

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import ru.hse.cinema.project.dao.interfaces.ClientDao
import ru.hse.cinema.project.dao.HelpMethods
import ru.hse.cinema.project.models.Cinema
import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.models.Session
import java.nio.charset.Charset
import kotlin.io.path.Path
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.nio.file.Paths
import java.text.SimpleDateFormat

class FileSystemClientDao : ClientDao {
    companion object {
        private val OSpath = Paths.get("").toAbsolutePath().toString()
        private val DIR_WITH_SESSIONS = OSpath + "/src/main/kotlin/ru/hse/cinema/project/resourses/cinema"
        private val jsonMapper = ObjectMapper()
    }

    init {
        jsonMapper.registerModule(JavaTimeModule())
        jsonMapper.setDateFormat(SimpleDateFormat("dd-MM-yyyy"))
    }

    override fun isClientBooked(clientName: String, sessionName: String, seatNumber: Int): Boolean {
        val session = HelpMethods.getSessionByName(sessionName, DIR_WITH_SESSIONS)
        val seat = HelpMethods.getSeatByNumber(session, seatNumber)

        return seat.client?.name == clientName
    }

}