package com.bookshelf.observers;
/**
 * Observer interface for reservation-related events.
 */

public interface ReservationObserver {

	 void onReservationCanceled(String reservationId, String userId, String libraryBookId);
}
 