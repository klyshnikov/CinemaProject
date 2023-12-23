package ru.hse.cinema.project.dao

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import ru.hse.cinema.project.dao.interfaces.CinemaDao
import ru.hse.cinema.project.dao.HelpMethods
import ru.hse.cinema.project.models.Cinema
import ru.hse.cinema.project.models.Session
import java.nio.charset.Charset
import kotlin.io.path.Path
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import ru.hse.cinema.project.models.Seat
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.reflect.jvm.internal.impl.descriptors.Visibilities.Local

class FileSystemCinemaDao : CinemaDao {
    companion object {
        private const val DIR_WITH_SESSIONS = "/home/mihail/javaProjects/CinemaProject/src/main/kotlin/ru/hse/cinema/project/resourses/cinema"
        private val jsonMapper = ObjectMapper()
    }

    init {
        jsonMapper.registerModule(JavaTimeModule())
        jsonMapper.setDateFormat(SimpleDateFormat("dd-MM-yyyy"));
    }

    override fun addSession(session: Session) {
        val sessionFile = Path(DIR_WITH_SESSIONS, session.name).toFile()
        if (!sessionFile.exists()) {
            sessionFile.createNewFile()
        }
        val sessionContent = jsonMapper.writeValueAsString(session)
        sessionFile.writeText(sessionContent)
        Cinema.sessionsNames.add(session.name)
    }

    override fun removeSession(sessionName : String) {
        val sessionFile = Path(DIR_WITH_SESSIONS, sessionName).toFile()
        if (sessionFile.exists()) {
            sessionFile.delete()
            Cinema.sessionsNames.remove(sessionName)
        } else {
            throw FileNotFoundException("Нельзя удалить сеанс - его нет")
        }
    }

    override fun getCurrentTime() : LocalDate {
        return Cinema.currentTime
    }

    override fun getSessions(): ArrayList<String> {
        return Cinema.sessionsNames
    }

}