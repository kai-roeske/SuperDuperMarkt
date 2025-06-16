package controller;

import java.util.Date;

import products.Clock;

public class DiaryClock implements Clock{

	@Override
	public void passDay() {
		Diary.get().passDay();
	}

	@Override
	public Date getCurrentDate() {
		return Diary.get().getCurrentDate() ;
	}

	@Override
	public Date getInitialDate() {
		return  Diary.get().getInitialDate() ;
	}
	
	@Override
	public String toString() {
		return DateTimeUtils.formatDate(getCurrentDate());
	}
}
