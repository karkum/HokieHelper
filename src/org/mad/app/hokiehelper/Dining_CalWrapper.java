package org.mad.app.hokiehelper;

import java.util.Date;

public class Dining_CalWrapper
{
	private Date d;
	private int cal;
	private int fat;
	private int carbs;
	private int protein;
	
	public Dining_CalWrapper(Date d, int cal, int fat, int carbs, int protein)
	{
		this.d = d;
		this.cal = cal;
		this.fat = fat;
		this.carbs = carbs;
		this.protein = protein;
	}
	
	public Date getDate()
	{
		return d;
	}
	
	public void setDate(Date d)
	{
		this.d = d;
	}
	
	public int getCal()
	{
		return cal;
	}
		
	public void setCal(int cal)
	{
		this.cal = cal;
	}
	
	public int getFat()
	{
		return fat;
	}
		
	public void setFat(int fat)
	{
		this.fat = fat;
	}
	
	public int getCarbs()
	{
		return carbs;
	}
		
	public void setCarbs(int carbs)
	{
		this.carbs = carbs;
	}
	
	public int getProtein()
	{
		return protein;
	}
		
	public void setProtein(int protein)
	{
		this.protein = protein;
	}
	
	@Override
	public String toString()
	{
		return d.getTime() + " " + cal + " " + fat + " " + carbs + " " + protein + " ";
	}
}
