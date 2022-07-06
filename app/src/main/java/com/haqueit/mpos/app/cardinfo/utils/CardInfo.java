package com.haqueit.mpos.app.cardinfo.utils;

public class CardInfo {

	// ����
	private String cardNo = null;
	// ˢ�����
	private String amount = null;
	// ��Ƭ��Ч��
	private String validThru = null;
	// ˢ��ʱ��
	private String swipeCardTime = null;
	// ˢ������
	private String swipeCardDate = null;
	// IC��55��
	private String icData55 = null;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getValidThru() {
		return validThru;
	}

	public void setValidThru(String validThru) {
		this.validThru = validThru;
	}

	public String getSwipeCardTime() {
		return swipeCardTime;
	}

	public void setSwipeCardTime(String swipeCardTime) {
		this.swipeCardTime = swipeCardTime;
	}

	public String getSwipeCardDate() {
		return swipeCardDate;
	}

	public void setSwipeCardDate(String swipeCardDate) {
		this.swipeCardDate = swipeCardDate;
	}

	public String getIcData55() {
		return icData55;
	}

	public void setIcData55(String icData55) {
		this.icData55 = icData55;
	}

}
