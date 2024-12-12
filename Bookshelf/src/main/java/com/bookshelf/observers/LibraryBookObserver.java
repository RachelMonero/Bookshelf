package com.bookshelf.observers;

import com.bookshelf.dao.LibraryBookDao;

/**
 * Observer that updates book availability when a reservation is canceled.
 */
public class LibraryBookObserver implements ReservationObserver {
    private final LibraryBookDao libraryBookDao;

    public LibraryBookObserver(LibraryBookDao libraryBookDao) {
        this.libraryBookDao = libraryBookDao;
    }

    @Override
    public void onReservationCanceled(String reservationId, String userId, String libraryBookId) {
        try {
            libraryBookDao.updateBookAvailability(libraryBookId, 1); // Set availability to 1
            System.out.println("Book " + libraryBookId + " is now available.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
