package org.mad.app.hokiehelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class Dining_NutritionInfoActivity extends SherlockActivity
{
    private TextView calcium;
    private TextView calories;
    private TextView caloriesFromFat;
    private TextView carbs;
    private TextView carbs_DV;
    private TextView cholesterol;
    private TextView cholesterol_DV;
    private TextView dietary_fiber;
    private TextView dietary_fiber_DV;
    private TextView iron;
    private TextView protein;
    private TextView sat_fat;
    private TextView sat_fat_DV;
    private TextView serving_size;
    private TextView sodium;
    private TextView sodium_DV;
    private TextView sugars;
    private TextView total_fat;
    private TextView total_fat_DV ;
    private TextView trans_fat;
    private TextView vitamin_a;
    private TextView vitamin_c;
    private boolean firsttime = true;
    private boolean allergensShowing = false;
    private boolean ingredientsShowing = false;
    private ImageView toggleAllergensButton;
    private TextView allergensList;
    private ImageView toggleIngredientsButton;
    private TextView ingredientsList;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutri_facts_portrait);
        
		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Nutritional Facts");

        toggleAllergensButton = (ImageView) findViewById(R.id.toggleAllergensButton);
        allergensList = (TextView) findViewById(R.id.allergensTextView);
        allergensList.setVisibility(TextView.GONE);
        allergensShowing = false;
        toggleAllergensButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (allergensShowing) {
                    allergensList.setVisibility(TextView.GONE);
                    toggleAllergensButton.setImageResource(R.drawable.toggle_plus);
                    allergensShowing = false;
                } else {
                    allergensList.setVisibility(TextView.VISIBLE);
                    toggleAllergensButton.setImageResource(R.drawable.toggle_minus);
                    allergensShowing = true;
                }
            }
        });

        toggleIngredientsButton = (ImageView) findViewById(R.id.toggleIngredientsButton);
        ingredientsList = (TextView) findViewById(R.id.ingredientsTextView);
        ingredientsList.setVisibility(TextView.GONE);
        ingredientsShowing = false;
        toggleIngredientsButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ingredientsShowing) {
                    ingredientsList.setVisibility(TextView.GONE);
                    toggleIngredientsButton.setImageResource(R.drawable.toggle_plus);
                    ingredientsShowing = false;

                } else {
                    ingredientsList.setVisibility(TextView.VISIBLE);
                    toggleIngredientsButton.setImageResource(R.drawable.toggle_minus);
                    ingredientsShowing = true;
                }
            }
        });

        final String[] nutriInfo =
                getIntent().getExtras().getString("org.mad.app.dining.nutriInfo").split("\\|");
        final String name = getIntent().getExtras().getString("foodName");

        Button hadIt = (Button) findViewById(R.id.ate_button);
        hadIt.setText("I had " + name);

        setFacts(1);

        final Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                    int arg2, long arg3)
            {
                int number = s.getSelectedItemPosition() + 1;
                setFacts(number);
                if (!firsttime) {
                    Toast.makeText(getApplicationContext(), "Modified Nutritional Facts", Toast.LENGTH_SHORT).show();
                }
                firsttime = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {
                //Do Nothing    
            }

        });

        final String foodName = nutriInfo[0];
        TextView tView = (TextView) findViewById(R.id.servSize);
        tView.setText(tView.getText() + foodName);

        hadIt.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                Date d = new Date(System.currentTimeMillis());
                Dining_CalWrapper[] array = Dining_HallDietChooserActivity.calValues;
                String[] temp = array[array.length - 1].getDate().toString().split("\\s+");
                String[] dArray = d.toString().split("\\s+");
                int cal, fat, carbs, protein;
                if (nutriInfo[1].equals("---")) {
                    cal = 0;
                } else {
                    cal = (int) (Double.parseDouble(nutriInfo[1]) * (s.getSelectedItemPosition() + 1)); 
                }
                if (nutriInfo[3].equals("---")) {
                    fat = 0;
                } else {
                    fat = (int) (Double.parseDouble(nutriInfo[3].replace("g", "")) * (s.getSelectedItemPosition() + 1) * 9); 
                }
                if (nutriInfo[5].equals("---")) {
                    carbs = 0;
                } else {
                    carbs = (int) (Double.parseDouble(nutriInfo[5].replace("g", "")) * (s.getSelectedItemPosition() + 1) * 4); 
                }
                if (nutriInfo[15].equals("---")) {
                    protein = 0;
                } else {
                    protein = (int) (Double.parseDouble(nutriInfo[15].replace("g", "")) * (s.getSelectedItemPosition() + 1) * 4); 
                }
                if (temp[1].equals(dArray[1]) && temp[2].equals(dArray[2]) && temp[5].equals(dArray[5]))
                {
                    array[array.length - 1].setCal(array[array.length - 1].getCal() + cal);
                    array[array.length - 1].setFat(array[array.length - 1].getFat() + fat);
                    array[array.length - 1].setCarbs(array[array.length - 1].getCarbs() + carbs);
                    array[array.length - 1].setProtein(array[array.length - 1].getProtein() + protein);
                }
                else
                {
                    Dining_CalWrapper cWrapper = new Dining_CalWrapper(d, cal, fat, carbs, protein);
                    int i;
                    for (i = 1; i < array.length; i++)
                        array[i - 1] = array[i];
                    array[i - 1] = cWrapper;
                }

                try
                {
                    FileOutputStream fos = openFileOutput("track.txt", MODE_PRIVATE);
                    for (int i = 0; i < array.length; i++)
                        fos.write(array[i].toString().getBytes());
                    fos.close();

                    FileOutputStream fos2 = openFileOutput("listFood.txt", MODE_APPEND);
                    fos2.close();
                    //Read from the file
                    FileInputStream fis = openFileInput("listFood.txt");
                    String s = "";
                    while (true)
                    {
                        byte[] b = new byte[100];
                        int i = fis.read(b, 0, 100);
                        if (i < 0)
                            break;
                        s  += new String(b);
                        if (i != 100)
                            break;
                    }
                    fis.close();

                    //Make an array of food items
                    if (s.equals(""))
                        writeToFile(MODE_PRIVATE, name, d, nutriInfo[1]);
                    else
                    {
                        String[] str = s.split("\\|");
                        Date date = new Date(Long.parseLong(str[1]));
                        String[] thisTime = date.toString().split("\\s+");
                        if (thisTime[1].equals(dArray[1]) && thisTime[2].equals(dArray[2]) && 
                                thisTime[5].equals(dArray[5]))
                            writeToFile(MODE_APPEND, name, d, nutriInfo[1]);
                        else
                            writeToFile(MODE_PRIVATE, name, d, nutriInfo[1]);
                    }
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Food item added", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
	/**
	 * Handles the clicking of action bar icons.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

    private void writeToFile(int mode, String name, Date d, String cal)
    {
        try
        {
            FileOutputStream fos3 = openFileOutput("listFood.txt", mode);
            String str = name + "|" + d.getTime() + "|" + cal + "|";
            fos3.write(str.getBytes());
            fos3.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setFacts(int multiplier)
    {
        final String[] nutriInfo =
                getIntent().getExtras().getString("org.mad.app.dining.nutriInfo").split("\\|");

        serving_size = (TextView)findViewById(R.id.serving_size);
        serving_size.setText("Serving Size  " + nutriInfo[0]);

        calories = (TextView)findViewById(R.id.calories);
        String cal = getNumber(nutriInfo[1], multiplier, 0);
        calories.setText("Calories  " + cal + " cal");

        caloriesFromFat = (TextView)findViewById(R.id.calories_from_fat);
        String calFat = getNumber(nutriInfo[2], multiplier, 0);
        caloriesFromFat.setText("Calories from Fat  " + calFat);

        total_fat = (TextView)findViewById(R.id.total_fat);
        String totFat = getNumber(nutriInfo[3], multiplier, 1);
        total_fat.setText("Total Fat  " + totFat + "g");

        total_fat_DV = (TextView)findViewById(R.id.total_fat_DV);
        String totFatDV = getNumber(nutriInfo[4], multiplier, 1);
        total_fat_DV.setText(totFatDV + "%");

        carbs = (TextView)findViewById(R.id.carbs);
        String carb = getNumber(nutriInfo[5], multiplier, 1);
        carbs.setText("Total Carbohydrate  " + carb + "g");

        carbs_DV = (TextView)findViewById(R.id.carbs_DV);
        String carbsDV = getNumber(nutriInfo[6], multiplier, 1);
        carbs_DV.setText(carbsDV + "%");

        sat_fat = (TextView)findViewById(R.id.sat_fat);
        String satfat = getNumber(nutriInfo[7], multiplier, 1);
        sat_fat.setText("Saturated Fat  " + satfat + "g");

        sat_fat_DV = (TextView)findViewById(R.id.sat_fat_DV);
        String satfatDV = getNumber(nutriInfo[8], multiplier, 1);
        sat_fat_DV.setText(satfatDV + "%");

        dietary_fiber = (TextView)findViewById(R.id.dietary_fiber);
        String fiber = getNumber(nutriInfo[9], multiplier, 1);
        dietary_fiber.setText("Dietary Fiber  " + fiber + "g");

        dietary_fiber_DV = (TextView)findViewById(R.id.dietary_fiber_DV);
        String fiberDV = getNumber(nutriInfo[10], multiplier, 1);
        dietary_fiber_DV.setText(fiberDV + "%");

        trans_fat = (TextView)findViewById(R.id.trans_fat);
        String transFat = getNumber(nutriInfo[11], multiplier, 1);
        trans_fat.setText("Trans Fat  " + transFat + "g");

        sugars = (TextView)findViewById(R.id.sugars);
        String sug = getNumber(nutriInfo[12], multiplier, 1);
        sugars.setText("Sugars  " + sug + "g");

        cholesterol = (TextView)findViewById(R.id.cholesterol);
        String chol = getNumber(nutriInfo[13], multiplier, 2);
        cholesterol.setText("Cholesterol  " + chol + "mg");

        cholesterol_DV = (TextView)findViewById(R.id.cholesterol_DV);
        String cholDV = getNumber(nutriInfo[14], multiplier, 1);
        cholesterol_DV.setText(cholDV + "%");

        protein = (TextView)findViewById(R.id.protein);
        String prot = getNumber(nutriInfo[15], multiplier, 1);
        protein.setText("Protein  " + prot + "g");

        sodium = (TextView)findViewById(R.id.sodium);
        String sod = getNumber(nutriInfo[16], multiplier, 2);
        sodium.setText("Sodium  " + sod + "mg");

        sodium_DV = (TextView)findViewById(R.id.sodium_DV);
        String sodDV = getNumber(nutriInfo[17], multiplier, 1);
        sodium_DV.setText(sodDV + "%");

        calcium = (TextView)findViewById(R.id.calcium);
        String calc = getNumber(nutriInfo[18], multiplier, 1);
        calcium.setText("Calcium  " + calc + "%");

        iron = (TextView)findViewById(R.id.iron);
        String ir = getNumber(nutriInfo[19], multiplier, 1);
        iron.setText("Iron  " + ir + "%");

        vitamin_a = (TextView)findViewById(R.id.vitamin_a);
        String a = getNumber(nutriInfo[20], multiplier, 1);
        vitamin_a.setText("Vitamin A  " + a + "%");

        vitamin_c = (TextView)findViewById(R.id.vitamin_c);
        String c = getNumber(nutriInfo[21], multiplier, 1);
        vitamin_c.setText("Vitamin C  " + c + "%");
        
        ingredientsList = (TextView)findViewById(R.id.ingredientsTextView);
        ingredientsList.setText(nutriInfo[22]);

        allergensList = (TextView)findViewById(R.id.allergensTextView);
        allergensList.setText(nutriInfo[23]);

    }
    private String getNumber(String num, int multiplier, int digits) {
        if (num.trim().equals(""))
            return "---";
        
        num = num.substring(0, num.length() - digits);
        try {
            int i = Integer.parseInt(num) * multiplier;
            return Integer.toString(i);
        }
        catch (NumberFormatException e) {
            try {
                double d = Double.parseDouble(num) * multiplier;
                DecimalFormat dec = new DecimalFormat("#.##");
                return dec.format(d);
            }
            catch (NumberFormatException e1) {
                return "---";
            }
        }
//      if (num.indexOf("---") == 0 || num.indexOf("-") == 0 
//              || num.equals("") || num.indexOf("%") == 0){
//          return "---";
//      }
//      num = num.substring(0, num.length() - digits);
//      try {
//          int i = Integer.parseInt(num) * multiplier;
//          return Integer.toString(i);
//      }
//      catch (NumberFormatException e) {
//          double d = Double.parseDouble(num) * multiplier;
//          DecimalFormat dec = new DecimalFormat("#.##");
//          return dec.format(d);
//      }
    }
}