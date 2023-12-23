package ru.hse.cinema.project.dao.interfaces

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.apache.commons.io.FileUtils
import ru.hse.cinema.project.dao.FileSystemCinemaDao
import ru.hse.cinema.project.models.Cinema
import ru.hse.cinema.project.models.Seat
import ru.hse.cinema.project.models.Session
import java.nio.charset.Charset
import java.time.LocalDate
import kotlin.io.path.Path

interface CinemaDao {
    fun addSession(session: Session)
    fun removeSession(sessionName : String)
    fun getCurrentTime() : LocalDate
    fun getSessions() : ArrayList<String>
}