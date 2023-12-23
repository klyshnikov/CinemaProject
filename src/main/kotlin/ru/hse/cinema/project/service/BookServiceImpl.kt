package ru.hse.cinema.project.service

import ru.hse.cinema.project.dao.exceptions.SeatIsAlreadyBookedException
import ru.hse.cinema.project.dao.exceptions.SeatIsNotBookedException
import ru.hse.cinema.project.dao.exceptions.TimeNoMatchException
import ru.hse.cinema.project.dao.interfaces.CinemaDao
import ru.hse.cinema.project.dao.interfaces.ClientDao
import ru.hse.cinema.project.dao.interfaces.SessionDao
import ru.hse.cinema.project.service.interfaces.BookService

class BookServiceImpl(
    override val sessionDao: SessionDao,
    override val clientDao: ClientDao,
    override val cinemaDao: CinemaDao
) : BookService {

    override fun book(clientName: String, sessionName: String, seatNumber: Int) : Boolean {
        if (cinemaDao.getCurrentTime().isAfter(sessionDao.getTimeEnd(sessionName))) {
            throw TimeNoMatchException("Нельзя забронировать - время сеанса уже закончено");
        }

        if (cinemaDao.getCurrentTime().isBefore(sessionDao.getTimeStart(sessionName))) {
            throw TimeNoMatchException("Нельзя забронировать - сеанс еще не начался");
        }

        if (clientDao.isClientBooked(clientName,sessionName, seatNumber) || !sessionDao.isSeatEmpty(sessionName, seatNumber)) {
            throw SeatIsAlreadyBookedException("Место уже занято");
        }

        sessionDao.setSeatClient(clientName, sessionName, seatNumber)
        return true
    }

    override fun canselBook(clientName: String, sessionName: String, seatNumber: Int) : Boolean {
        if (!clientDao.isClientBooked(clientName,sessionName, seatNumber)) {
            throw SeatIsNotBookedException("Место не было забронировано - нечего отменять");
        }

        sessionDao.removeSeatClient(sessionName, seatNumber)
        return true
    }

}