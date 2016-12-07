package com.android.calorieCalculator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class UserLogin extends Activity {
	
	private Bundle personalInfo;
	private Button nextButton;
	private EditText usernameText;
	private String username;
	private EditText passwordField;
	private EditText confirmPasswordField;
	private String password;
	private String confirmPassword;
	private DBAdapter1 db1;
	private int userID;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userlogin);
		
		nextButton = (Button) this.findViewById(R.id.widget36);
		
		personalInfo =  this.getIntent().getExtras();
		
		db1 = new DBAdapter1(this);
		
		nextButton.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				username = usernameText.getText().toString();
				password = passwordField.getText().toString();
				confirmPassword = confirmPasswordField.getText().toString();
				
				if (username !=null && password !=null && confirmPassword !=null && password.equals(confirmPassword)) {
					
					db1.insertTitle(personalInfo.getString(NewUserInformationScreen.FULL_NAME), 
							personalInfo.getInt(NewUserInformationScreen.HEIGHT), 
							personalInfo.getInt(NewUserInformationScreen.HEIGHT), 
							personalInfo.getString(NewUserInformationScreen.SEX), username, confirmPassword);
					
					
					
				}
				
				else if (!password.equals(confirmPassword)) {
					
					
				}
				
				else {
					
					
				}
				
			}
			
			
		});
	}
	
	

}
