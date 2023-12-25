package ru.hse.cinema.project.verefication

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.commons.io.FileUtils
import ru.hse.cinema.project.dao.HelpMethods
import ru.hse.cinema.project.dao.exceptions.VereficationException
import ru.hse.cinema.project.models.User
import java.nio.charset.Charset
import java.nio.file.Paths
import kotlin.io.path.Path

class VereficatorImpl : Vereficator {
    companion object {
        private val OSpath = Paths.get("").toAbsolutePath().toString()
        private val DIR_WITH_ACCOUNTS = OSpath + "/src/main/kotlin/ru/hse/cinema/project/resourses/accounts"
    }

    private val jsonMapper = ObjectMapper()

    override fun run() {
        println("r - зарегистрироваться")
        println("e - войти")
        val choice = readln()
        if (choice == "r") {
            println("Введите имя пользователя: ")
            val name = readln()
            println("Введите пароль: ")
            val password = readln()
            if (isUserExist(name)) {
                throw VereficationException("Пользователь уже существует")
            }
            addUser(name, password)
            println("Регистрация успешна")
        } else if (choice == "e") {
            println("Введите имя пользователя: ")
            val name = readln()
            println("Введите пароль: ")
            val password = readln()
            if (isUserExist(name) && isPasswordCorrect(name, password)) {
                println("Успешно вошли")
            } else {
                throw VereficationException("Неверынй пароль!")
            }
        } else {
            throw VereficationException("Неверный ввод!")
        }
    }

    fun addUser(name : String, password : String) {
        val userPath = Path(DIR_WITH_ACCOUNTS, name).toFile()
        if (!userPath.exists()) {
            userPath.createNewFile()
        }
        val user = User(password)
        val userContent = jsonMapper.writeValueAsString(user)
        userPath.writeText(userContent)
    }

    fun isUserExist(name : String) : Boolean {
        val userPath = Path(DIR_WITH_ACCOUNTS, name).toFile()
        if (!userPath.exists()) {
            return false
        }
        return true
    }

    fun isPasswordCorrect(name : String, password : String) : Boolean {
        return getUserPassword(name) == password
    }

    fun getUserPassword(name : String) : String {
        val userPath = Path(DIR_WITH_ACCOUNTS, name).toFile()
        val content = FileUtils.readFileToString(userPath, Charset.defaultCharset())
        val user = jsonMapper.readValue(content, User::class.java)
        return user.password
    }
}