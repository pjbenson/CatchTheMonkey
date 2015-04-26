package com.spring.model;

import javax.persistence.Entity;

@Entity
public class Visa extends CreditCard {

	public Visa(String number, String month, String year) {
		super(number, month, year);
	}
	
	public Visa(){};

	public boolean checkNumberOfDigits() {
		int numberOfDigits = cardNumber.length();
		if (numberOfDigits == 13 || numberOfDigits == 16)
			return true;
		else
			return false;	
	}
	
	public boolean checkValidPrefix(){
		String firstDigit = cardNumber.substring(0, 1);

		if (firstDigit.equals("4"))
			return true;
		else
			return false;
	}

}
