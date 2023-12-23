package ru.hse.cinema.project.dao.exceptions

import java.io.FileNotFoundException

class SessionNotExistException(message : String) : FileNotFoundException(message) {}