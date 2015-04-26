package com.spring.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
public abstract class CreditCard implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	protected String cardNumber;
	protected String cardType;
	protected int expiryMonth, expiryYear;
	@OneToOne
    @PrimaryKeyJoinColumn
	private Account account;

	public CreditCard(String number, String month, String year) {
		cardNumber = number;
		try {
			expiryMonth = Integer.parseInt(month);
		}
		catch (NumberFormatException e1) {
			expiryMonth = 1;
		}
		try {
			expiryYear = Integer.parseInt(year);
		}
		catch (NumberFormatException e2) {
			expiryYear = 2014;
		}
	}
	
	public CreditCard(){};
	
	public boolean validate() {
		if (!checkExpiryDateValid()) {
			return false;
		}
		if (!checkAllCharsDigits()) {
			return false;
		}
		if (!checkNumberOfDigits()) {
			return false;
		}
		if (!checkValidPrefix()) {
			return false;
		}
		if (!checkCheckSumDigit()) {
			return false;			
		}
		return true;
	}

	/* This checks to see that the card's expiry date is after the current month and year */
	private boolean checkExpiryDateValid() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		int currentYear = cal.get(Calendar.YEAR);
		if (currentYear > expiryYear) {
			return false;
		}
		else if (currentYear == expiryYear && currentMonth > expiryMonth) {
			return false;
		}
		else {
			return true;
		}
	}

	/* This checks to see that all 'digits' in the credit card number are valid */
	private boolean checkAllCharsDigits() {
		String validDigits = "0123456789";	
		boolean result = true;

		for (int i = 0; i < cardNumber.length(); i++) {
			if (validDigits.indexOf(cardNumber.substring(i, i + 1)) < 0) {
				result = false;
				break;
			}
		}

		return result;
	}
	
	public abstract boolean checkNumberOfDigits();
	
	public abstract boolean checkValidPrefix();
	
	private boolean checkCheckSumDigit() {
		boolean result = true;
		int sum = 0;
		int multiplier = 1;
		int stringLength = cardNumber.length();

		for (int i = 0; i < stringLength; i++) {
			String currentDigit = cardNumber.substring(stringLength - i - 1, stringLength - i);
			int currentProduct = new Integer(currentDigit).intValue() * multiplier;
			if (currentProduct >= 10)
				sum += (currentProduct % 10) + 1;
			else
				sum += currentProduct;
			if (multiplier == 1)
				multiplier++;
			else
				multiplier--;
		}
		if ((sum % 10) != 0)
			result = false;

		return result;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

}
