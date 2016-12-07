package com.android.calorieCalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class FoodScreen extends Activity {
	
	private TextView amount;
	private ImageView foodImage;
	private Button finishButton;
	private double calorieGain;
	public final static String CAL_GAIN = "calorieGain";
	private Bundle foodInfo;
	private EditText foodAmount;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foodscreen);
		
		amount = (TextView) findViewById(R.id.widget34);
		finishButton = (Button) findViewById(R.id.widget33);
		foodImage = (ImageView) findViewById(R.id.widget30);
		foodAmount = (EditText) findViewById(R.id.widget32);
		
		foodInfo = this.getIntent().getExtras();
		
		Log.i(CalorieGain.CAL_TAG, "Display components assigned correctly to variables");
		Log.i(CalorieGain.CAL_TAG, "FoodInfo - Row ID = " + foodInfo.getInt(DBAdapter.KEY_ROWID));
		
		switch (foodInfo.getInt(DBAdapter.KEY_ROWID)) {
		
			case 1:
				
				foodImage.setImageDrawable(this.getResources().getDrawable(R.drawable.almonds));		
			
			break;
			
			default:
				
				
			break;
				
		}
		
		setAmountText();
		
		
		finishButton.setOnClickListener(new View.OnClickListener() {
			
			 public void onClick(View v) {
				 
				 calculateCalorieGain();
				 returnResult();
				 
             }

		
		});
		
	}
	
	private void setAmountText() {
		
		Log.i(CalorieGain.CAL_TAG, "Unit = " + foodInfo.getString(DBAdapter.KEY_UNIT));
		
		if (foodInfo.getString(DBAdapter.KEY_UNIT).equals(new String("Item"))) {
			
			String items = new String("Enter how many " + foodInfo.getString(DBAdapter.KEY_ITEM) +  " you ate:");
			amount.setText(items.toCharArray(), 0, items.length());
			
		}
		
		else if (foodInfo.getString(DBAdapter.KEY_UNIT).equals(new String("ml"))) {
			
			String volume = new String ("Enter how many ml of " +  foodInfo.getString(DBAdapter.KEY_ITEM) + " you had:");
			amount.setText(volume.toCharArray(), 0, volume.length());
		}
		
		else if (foodInfo.getString(DBAdapter.KEY_UNIT).equals(new String("g"))) {
			
			String mass = new String("Enter how many grams of " + foodInfo.getString(DBAdapter.KEY_ITEM) + " you had:");
			amount.setText(mass.toCharArray(), 0, mass.length());
		}
		
		else if (foodInfo.getString(DBAdapter.KEY_UNIT).equals(new String("Strips"))) {
			

			String strips = new String("Enter how many strips of " + foodInfo.getString(DBAdapter.KEY_ITEM) + " you had:");
			amount.setText(strips.toCharArray(), 0, strips.length());
		}
	}
	
	private void calculateCalorieGain() {
		
		calorieGain = (Double.parseDouble(foodAmount.getText().toString()) / foodInfo.getDouble(DBAdapter.KEY_AMOUNT)) * foodInfo.getInt(DBAdapter.KEY_CALORIE_VALUE);
		
		Log.i(CalorieGain.CAL_TAG, "calorieGain = " + calorieGain);
	}
	
	
	private void returnResult() {
		
		Intent result = new Intent();
		Bundle returnValue = new Bundle();
		returnValue.putDouble(CAL_GAIN, calorieGain);
		result.putExtras(returnValue);
		setResult(RESULT_OK, result);
		finish();
	}
	
	
	

}
