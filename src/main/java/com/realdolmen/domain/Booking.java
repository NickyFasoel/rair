package com.realdolmen.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Booking implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String paymentStatus; //TODO: dit een enum mss
	
	@ManyToOne
	@JoinColumn(name="customer_id", nullable=false)
	private Customer customer;
	
	private Calendar dateOfReservation; //TODO: calendar of iets anders?
	
	@OneToMany(mappedBy="booking")
	private List<BookingOfFlight> bookingOfFlightList = new ArrayList<>();
	
	public Booking(){
		
	}
	
	public Booking(String paymentStatus, Customer customer, Calendar dateOfReservation) {
		super();
		this.paymentStatus = paymentStatus;
		this.customer = customer;
		this.dateOfReservation = dateOfReservation;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Calendar getDateOfReservation() {
		return dateOfReservation;
	}

	public void setDateOfReservation(Calendar dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

	public List<BookingOfFlight> getBookingOfFlightList() {
		return bookingOfFlightList;
	}

	public void setBookingOfFlightList(List<BookingOfFlight> bookingOfFlightList) {
		this.bookingOfFlightList = bookingOfFlightList;
	}

	public Long getId() {
		return id;
	}
	
	public void addBookingOfFlight(BookingOfFlight bf){
		bookingOfFlightList.add(bf);
	}
	
	
}
