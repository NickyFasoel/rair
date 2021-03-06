package com.realdolmen.domain.beans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.realdolmen.domain.flight.Booking;
import com.realdolmen.repository.BookingRepository;

@Named("bookingBean")
@SessionScoped
public class BookingBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7742740715287006737L;
	
	@Inject
	private LoginBean loginBean;
	
	@Inject
	private BookingRepository bookingRepository;
	
	private Long urlCode;
	
	private Booking booking;
	
	private boolean bookingIsNull = true;
	
	private boolean afterBooking = false;

	public Long getUrlCode() {
		if(urlCode == null){
			return -55L;
		}
		return urlCode;
	}

	public void setUrlCode(int urlCode) {
		this.urlCode = new Long(urlCode);
	}
	
	public void setUrlCode(Long urlCode) {
		this.urlCode = urlCode;
	}
	
	public boolean isBookingIsNull() {
		if(getBooking() == null){
			return true;
		}
		return false;
	}

	public void setBookingIsNull(boolean bookingIsNull) {
		this.bookingIsNull = bookingIsNull;
	}
	
	public boolean getBookingIsNull(){
		return getBooking() == null;
	}

	public Booking getBooking() {
		if(urlCode == null){
			return null;
		}
		System.err.println("Trying to fetch booking");
//		if(true){
//			return new Booking();
//		}
		Booking bookingFound = bookingRepository.findById(urlCode);
		if(bookingFound == null){
			// TODO booking bestaat niet
			System.err.println("Booking doesn't exist");
			booking = null;
			return booking;
		}
		if(loginBean.getIsUserNull() || 
				! bookingFound.getCustomer().getUserName().equals(loginBean.getUserName())){
			// TODO user niet ingelogd of niet juiste user
			System.err.println("Booking user is not correct, bookingFound customer username: " 
			+ bookingFound.getCustomer().getUserName() + " and logged in user username: " + loginBean.getUserName());
			booking = null;
			return booking;
		}
		booking = bookingRepository.findById(urlCode);
		System.err.println("Found the booking with Id and customer: " + booking.getId() + ", " + booking.getCustomer());
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	public String print(){
		return "invoicePrintVersion";
	}

	public void setAfterBooking(boolean afterBooking) {
		this.afterBooking = afterBooking;
	}
	
	public boolean getAfterBooking(){
		return afterBooking;
	}
}
