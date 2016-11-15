package com.realdolmen.domain.flight;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class BookingOfFlight implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9176140862127754720L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private double price;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="flight_id")
	private Flight flight;
	
	@NotNull
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="booking_id")
	private Booking booking;
	
	@NotNull
	private int numberInBooking;
	
	@NotNull
	@OneToOne
	private Seat seat;
	
	private double discountsPercentages;

	private double discountsRealvalues;

	private double extraDiscount;
	
	private double rairCharge;
	
	public BookingOfFlight(){
		
	}

	public BookingOfFlight(double price, Flight flight, Booking booking, Seat seat) {
		this.price = price;
		this.flight = flight;
		this.booking = booking;
		this.seat = seat;
	}

	public BookingOfFlight(double price, Flight flight, Booking booking, Seat seat,
			double discountsPercentages, double discountsRealvalues, double extraDiscount,
			double rairCharge) {
		this.price = price;
		this.flight = flight;
		this.booking = booking;
		this.seat = seat;
		this.rairCharge = rairCharge;
	}

	public double getPrice() {
		return price;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Long getId() {
		return id;
	}

	public int getNumberInBooking() {
		return numberInBooking;
	}

	public void setNumberInBooking(int numberInBooking) {
		this.numberInBooking = numberInBooking;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public double getDiscountsPercentages() {
		return discountsPercentages;
	}

	public void setDiscountsPercentages(double discountsPercentages) {
		this.discountsPercentages = discountsPercentages;
	}

	public double getDiscountsRealvalues() {
		return discountsRealvalues;
	}

	public void setDiscountsRealvalues(double discountsRealvalues) {
		this.discountsRealvalues = discountsRealvalues;
	}

	public double getExtraDiscount() {
		return extraDiscount;
	}

	public void setExtraDiscount(double extraDiscount) {
		this.extraDiscount = extraDiscount;
	}
	
	public double getRairCharge() {
		return rairCharge;
	}

	public void setRairCharge(double rairCharge) {
		this.rairCharge = rairCharge;
	}

	public String getDiscounts(){
		String result = "";
		if(discountsPercentages != 0.0 && discountsRealvalues != 0.0 && extraDiscount != 0.0){
//			ArrayList<Double> discs = new ArrayList<Double>();
			result += "with discount(s): ";
			if(discountsPercentages != 0.0){
//				discs.add(discountsPercentages);
				result += discountsPercentages + "% &";
			}
			if(discountsRealvalues != 0.0){
//				discs.add(discountsRealvalues);
				result += "�" + discountsRealvalues +" &";
			}
			if(extraDiscount != 0.0){
//				discs.add(extraDiscount);
				result += extraDiscount + "% ";
			}
			if(result.charAt(result.length()-1) == '&'){
				result = result.substring(0,result.length());
			}
		}
		return result;
	}
}
