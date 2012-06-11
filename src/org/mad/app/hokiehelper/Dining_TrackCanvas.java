package org.mad.app.hokiehelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.View;

public class Dining_TrackCanvas extends View 
{
	private int mode;
	private int fat;
	private int carbs;
	private int protein;
	private int height;
	private int width;
	
	public Dining_TrackCanvas(Context mContext, int mode, int fat, int carbs, int protein, int width, int height)
	{
        super(mContext);
        this.mode = mode;
        this.fat = fat;
        this.carbs = carbs;
        this.protein = protein;
        this.height = height;
        this.width = width;
    }
	
    public void onDraw(Canvas canvas)
    {
    	height = canvas.getHeight();
    	width = canvas.getWidth();
    	switch (mode)
    	{
    		case 0: daily(canvas); break;
    		case 1: weekly(canvas); break;
    		case 2: monthly(canvas); break;
    	}
    }
    
    private void daily(Canvas canvas)
    {
        canvas.drawColor(0xF0EEE0);
    	Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);
        paint.setTextSize(13);
        paint.setColor(Color.BLACK);

        int total = fat + carbs + protein;
        if (total == 0)
        {
        	paint.setStrokeWidth(1);
        	paint.setTextAlign(Paint.Align.CENTER);
        	canvas.drawText("You have not eaten anything yet", width / 2, 50, paint);
        }
        else
        {
        	int carbsPercent = (carbs * 345) / total;
        	int proteinPercent = (protein * 345) / total;
        	
        	int percent = (int) (width * 0.30);
        	int current = 20 + 2 * percent;
        	
        	//Draw the arcs and colored boxes for legend
        	RectF oval = new RectF(width / 2 - percent, 20, width / 2 + percent, current);
        	paint.setColor(0xFF483D8B);
            canvas.drawArc(oval, 0, 360, true, paint);
            canvas.drawRect(10, (float) (height * 0.48), 70, (float) (height * 0.54), paint);
            
            paint.setColor(0xFF8B0000);
            canvas.drawArc(oval, 5, carbsPercent, true, paint);
            canvas.drawRect(10, (float) (height * 0.56), 70, (float) (height * 0.62), paint);
            
            paint.setColor(0xFF2F4F4F);
            canvas.drawArc(oval, carbsPercent + 10, proteinPercent, true, paint);
            canvas.drawRect(10, (float) (height * 0.64), 70, (float) (height * 0.70), paint);
            
            //Stroke the outer circle black
            paint.setStrokeWidth(4);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawArc(oval, 0, 360, false, paint);
            
            //Stroke the boxes
            paint.setStrokeWidth(2);
            canvas.drawRect(10, (float) (height * 0.48), 70, (float) (height * 0.54), paint);
            canvas.drawRect(10, (float) (height * 0.56), 70, (float) (height * 0.62), paint);
            canvas.drawRect(10, (float) (height * 0.64), 70, (float) (height * 0.70), paint);
            
            //Empty out the inner circle
            paint.setColor(0xFFF0EEE0);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            int temp = (int) (width * 0.15);
            oval = new RectF(width / 2 - temp, (20 + current) / 2 - temp, 
            		width / 2 + temp, (20 + current) / 2 + temp);
            canvas.drawArc(oval, 0, 360, true, paint);
            
            //Stroke the inner circle
            paint.setStrokeWidth(4);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
            canvas.drawArc(oval, 0, 360, true, paint);
            paint.setStrokeWidth(2);
            
            //Create partition
            paint.setColor(0xFFF0EEE0);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            oval = new RectF(width / 2 - percent - 2, 20 - 2, width / 2 + percent + 2, current + 2);
            canvas.drawArc(oval, 0, 5, true, paint);
            canvas.drawArc(oval, carbsPercent + 5, 5, true, paint);
            canvas.drawArc(oval, proteinPercent + carbsPercent + 10, 5, true, paint);
            
            //Text stuff
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(1);
            paint.setTextSize(20);
            carbsPercent = (carbs * 100) / total;
            proteinPercent = (protein * 100) / total;

            paint.setColor(Color.WHITE);
            canvas.drawText((100 - carbsPercent - proteinPercent) + "%", 23, (float) (height * 0.52), paint);
            canvas.drawText(carbsPercent + "%", 23, (float) (height * 0.60), paint);
            canvas.drawText(proteinPercent + "%", 23, (float) (height * 0.68), paint);
            paint.setColor(Color.BLACK);
            canvas.drawText("Fat", 75, (float) (height * 0.52), paint);
            canvas.drawText("Carbohydrate", 75, (float) (height * 0.60), paint);
            canvas.drawText("Protein", 75, (float) (height * 0.68), paint);
        }
    }
    
    private void weekly(Canvas canvas)
    {
    	int sum = 0;
    	for (int i = 0; i < 7; i++)
        {
        	if (Dining_HallInformationActivity.calValues[29 - i].getCal() > 0)
        		sum += Dining_HallInformationActivity.calValues[29 - i].getCal();
        }

		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(2);
		paint.setTextSize(17);
		canvas.drawColor(0xF0EEE0);
		
    	if (sum == 0)
    	{
    		paint.setTextSize(13);
    		paint.setStrokeWidth(1);
        	paint.setTextAlign(Paint.Align.CENTER);
        	canvas.drawText("You have not eaten anything yet", width / 2, 50, paint);
    	}
    	else
    	{
    		int tempHeight = (int) (height * 0.65);
    		//Paint for stroking
    		Paint p = new Paint();
    		p.setStyle(Paint.Style.STROKE);
    		p.setStrokeWidth(2);
    		p.setColor(Color.BLACK);

    		//Paint for filling the shapes
    		Paint p1 = new Paint();
    		p1.setStyle(Paint.Style.FILL_AND_STROKE);
    		p1.setTextAlign(Paint.Align.CENTER);
    		p1.setStrokeWidth(1);
    		p1.setTextSize(17);
    		p1.setColor(Color.BLACK);

    		//The axis - Change values
    		canvas.drawLine(20, tempHeight, 20, 20, paint);
    		canvas.drawLine(20, tempHeight, width - 30, tempHeight, paint);
    		
    		int sizeOfBar = (20 + width - 40) / 7;
    		int percent = (int) (sizeOfBar * 0.70);
    		int spacing = sizeOfBar - percent;

    		//Draw the seven bars - change values of bars and display days
    		for (int i = 0, j = 20 + spacing; i < 7; i++)
    		{
    			int cal = Dining_HallInformationActivity.calValues[29 - i].getCal();
    			int temp = cal;
    			if (temp != -1 && temp < 3000)
    			{
    				temp = ((tempHeight - 20) * temp) / 3000;
    				paint.setShader(new LinearGradient((2 * j + percent) / 2, tempHeight, 
    						(2 * j + percent) / 2, tempHeight - temp, Color.BLUE, 0xFF098EDF, 
    						Shader.TileMode.REPEAT));
    				canvas.drawRect(j, tempHeight - temp, j + percent, tempHeight, paint);
    				canvas.drawRect(j, tempHeight - temp, j + percent, tempHeight, p);
    				canvas.drawText(cal + "", (2 * j + percent) / 2, tempHeight - temp - 15, p1);
    				String text = Dining_HallInformationActivity.calValues[29 - i].getDate().toString().split("\\s+")[0];
    				text = getStringFromDay(text);
    				canvas.drawText(text, (2 * j + percent) / 2, tempHeight + 20, p1);
    			}
    			else if (temp >= 3000)
    			{
    				paint.setShader(new LinearGradient((2 * j + percent) / 2, tempHeight, 
    						(2 * j + percent) / 2, tempHeight - temp, Color.BLUE, 0xFF098EDF, 
    						Shader.TileMode.REPEAT));
    				canvas.drawRect(j, 20, j + percent, tempHeight, paint);
    				canvas.drawRect(j, 20, j + percent, tempHeight, p);
    				
    				Paint p2 = new Paint();
    				p2.setStyle(Paint.Style.FILL);
    				p2.setColor(0xFFF0EEE0);
    				canvas.drawRect(j - 3, 50, j + percent + 3, 80, p2);
    				
    				canvas.drawText(cal + "", (2 * j + percent) / 2, 70, p1);
    				String text = Dining_HallInformationActivity.calValues[29 - i].getDate().toString().split("\\s+")[0];
    				text = getStringFromDay(text);
    				canvas.drawText(text, (2 * j + percent) / 2, tempHeight + 20, p1);
    			}
    			j = j + sizeOfBar;
    		}
        }
    }
    
    private void monthly(Canvas canvas)
    {
    	int sum = 0;
    	for (int i = 0; i < 29; i++)
    	{
    		if (Dining_HallInformationActivity.calValues[29 - i].getCal() > 0)
    			sum += Dining_HallInformationActivity.calValues[29 - i].getCal();
    	}
    	
    	Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        paint.setTextSize(17);
    	canvas.drawColor(0xF0EEE0);
        
        if (sum == 0)
        {
        	paint.setTextSize(13);
    		paint.setStrokeWidth(1);
        	paint.setTextAlign(Paint.Align.CENTER);
        	canvas.drawText("You have not eaten anything yet", width / 2, 50, paint);
        }
        else
        {
        	int tempHeight = (int) (height * 0.65);
        	Paint p1 = new Paint();
        	p1.setStyle(Paint.Style.FILL);
        	p1.setTextSize(12);
        	p1.setColor(Color.BLACK);

    		//The axis - Change values
    		canvas.drawLine(20, tempHeight, 20, 20, paint);
    		canvas.drawLine(20, tempHeight, width - 30, tempHeight, paint);
    		
    		//Draw markers on axis
    		for (int i = 0, j = 0; i < 7; i++, j += 500)
    		{
    			int temp = ((tempHeight - 20) * j) / 3000;
    			canvas.drawLine(20, tempHeight - temp, width - 30, tempHeight - temp, paint);
    		}
    		
    		canvas.drawText("0", 4, tempHeight, p1);
    		canvas.drawText("3000", 2, 17, p1);
    		canvas.drawText("Most Recent", 15, tempHeight + 20, p1);
    		
    		int sizeOfBar = (20 + width - 40) / 30;
        	int[] array = new int[30];
        	for (int i = 0, j = 29; i < 30; i++)
        	{
        		int cal = Dining_HallInformationActivity.calValues[29 - i].getCal();
        		int temp = cal;
        		if (temp != -1)
        		{
        			temp = ((height - 220) * temp) / 3000;
        			canvas.drawCircle(j, tempHeight - temp, 2, paint);
        		}
        		else
        		{
        			temp = 0;
        			canvas.drawCircle(j, tempHeight - temp, 2, paint);
        		}
        		array[i] = temp;
        		j = j + sizeOfBar - 1;
        	}

        	paint.setColor(Color.BLUE);
        	paint.setStrokeWidth(3);
        	for (int i = 0, j = 29; i < array.length - 1; i++, j = j + sizeOfBar - 1)
        		canvas.drawLine(j, tempHeight - array[i], j + sizeOfBar, tempHeight - array[i + 1], paint);
        }
    }
    
    private String getStringFromDay(String text)
    {
    	text = text.toUpperCase();
    	if (text.equals("MON"))
    		return "M";
    	else if (text.equals("TUE"))
    		return "T";
    	else if (text.equals("WED"))
    		return "W";
    	else if (text.equals("THU"))
    		return "Th";
    	else if (text.equals("FRI"))
    		return "F";
    	else if (text.equals("SAT"))
    		return "S";
    	else
    		return "Su";
    }
}
