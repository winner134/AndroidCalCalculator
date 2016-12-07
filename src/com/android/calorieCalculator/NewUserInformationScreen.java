package com.android.calorieCalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class NewUserInformationScreen extends Activity {
	
	private EditText fullNameField;
	private String fullName;
	private EditText weightField;
	private int weight = 0;
	private EditText heightField;
	private int height = 0;
	private RadioButton femaleButton;
	private RadioButton maleButton;
	private Button nextButton;
	public static final String  FULL_NAME = "full name";
	public  static final String  WEIGHT = "weight";
	public  static final String  HEIGHT = "height";
	public  static final String  SEX = "sex";
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuserinfo);
		
		fullNameField = (EditText) findViewById(R.id.widget31);
		weightField = (EditText) findViewById(R.id.widget35);
		heightField = (EditText) findViewById(R.id.widget33);
		femaleButton =  (RadioButton) findViewById(R.id.widget43);
		maleButton =  (RadioButton) findViewById(R.id.widget42);
		nextButton =  (Button) findViewById(R.id.widget46);
		
		nextButton.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				fullName = fullNameField.getText().toString();
				weight = Integer.parseInt(weightField.getText().toString());
				height = Integer.parseInt(heightField.getText().toString());
				
				if (fullName != null && weight > 0 && height > 0) {
				
					Intent i = new Intent(NewUserInformationScreen.this, UserLogin.class);
					Bundle personalInfo = new Bundle();
					personalInfo.putString(FULL_NAME, fullName);
					personalInfo.putInt(WEIGHT, weight);
					personalInfo.putInt(HEIGHT, height);
					
					if (maleButton.isSelected())
						personalInfo.putString(SEX, "male");
					else if (femaleButton.isSelected())
						personalInfo.putString(SEX, "female");
					
					i.putExtras(personalInfo);
					startActivityForResult(i, 1);
				
				}
				
				else {
					
					
				}
				
			}
			
			
			
		});
		
	}


	/* (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	

}
