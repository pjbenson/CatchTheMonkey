package com.spring.model;

import javax.persistence.Entity;

@Entity
public class MasterCard extends CreditCard {

	public MasterCard(String number, String month, String year) {
		super(number, month, year);
	}
	
	public MasterCard(){};
	
	public boolean checkNumberOfDigits() {
		int numberOfDigits = cardNumber.length();
		if (numberOfDigits == 16)
			return true;
		else
			return false;
		
	}
	
	public boolean checkValidPrefix(){
		String firstDigit = cardNumber.substring(0, 1);
		String nextDigit = cardNumber.substring(1, 2);
		String validDigits = "12345";

		if (firstDigit.equals("5") && (validDigits.indexOf(nextDigit) >= 0))
			return true;
		else
			return false;
	}

}
