package ru.hse.cinema.project.dao.interfaces

interface ClientDao {
    fun isClientBooked(clientName: String, sessionName: String, seatNumber: Int) : Boolean
}