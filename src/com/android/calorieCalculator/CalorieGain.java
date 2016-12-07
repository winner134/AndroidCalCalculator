package com.android.calorieCalculator;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CalorieGain extends Activity {
	
	private final static int FOOD_TABLE_SIZE = 257;
	public final static int ACTIVITY_FOOD = 1;
	public final static int ACTIVITY_SEARCH = 2;
	private static String[] FOODS = new String[FOOD_TABLE_SIZE];
	private DBAdapter db;
	private double totalCalorieGain = 0;
	public static final String CAL_TAG = "CalorieGain";
	private String displayCalorieValue;
	private TextView calorieGainDisplay;
	private EditText calValue;
	private AutoCompleteTextView textView;
	private Button addButton;
	private static InputStream fileToBeRead;
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.caloriegain);
        db = new DBAdapter(this);
        try {
			fileToBeRead = this.getResources().getAssets().open("calorie.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
	    
			Log.i(CAL_TAG, "About to call populateDatabase()");
	        populateDatabase(db);
	        Cursor temp = null;
			
	        for (int i = 0; i < FOOD_TABLE_SIZE; i++) {
	        	db.openDB();
				temp = db.getTitle(i + 1);
				//Log.i(CAL_TAG, "FOODS[ " + i + " ] = " +  db.getTitle(i + 1).getString(1));
				FOODS[i] = (temp != null) ? temp.getString(1) : "";
				temp.close();
				db.closeDB();
	        }
		
		}
		
		catch (Exception e) 
		{		
			e.printStackTrace();
		}
		
        
        textView = (AutoCompleteTextView) findViewById(R.id.widget33);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, FOODS);
        textView.setAdapter(adapter);
        
        calorieGainDisplay = (TextView) findViewById(R.id.widget35);
        displayCalorieValue = new String("0 Cal");
        calorieGainDisplay.setText(displayCalorieValue.toCharArray(), 0, displayCalorieValue.length());
        
        calValue = (EditText) findViewById(R.id.widget30);
        
        addButton = (Button) findViewById(R.id.widget32);
        
        addButton.setOnClickListener(new Button.OnClickListener() {
            
        	public void onClick(View v) {
            	
        		totalCalorieGain = totalCalorieGain + Integer.parseInt(calValue.getText().toString());
    			displayCalorieValue = totalCalorieGain + " Cal";
    			calorieGainDisplay.setText(displayCalorieValue.toCharArray(), 0, displayCalorieValue.length());
    			calValue.setText(new String("").toCharArray(), 0, 0);

            }
       
        });
       
       
        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    	
			//@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
			
				startItemActivity(textView.getAdapter().getItem(position).toString());
				
			}

        	
        });
        
        
        
        Button finish = (Button) findViewById(R.id.widget41);
        finish.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
            	
            	 Log.i(CAL_TAG, "database closed");
            }
       
        }); 
        
        textView.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
					
				String item = new String(textView.getText().toString());
				Boolean found = false;
					
				for (int i = 0; i < FOODS.length; i++) {
						
					if (item.equals(FOODS[i])) {
							
						startItemActivity(item);
						found = true;
						break;
					}
				}
					
				if (found == false) {
						
					Intent i = new Intent(CalorieGain.this, WebScreen.class);
					startSubActivityForResult(i, ACTIVITY_SEARCH);
						
				}

				
				
			}
        	
        
        });
        
        
        
       
    }
    
    private static void populateDatabase(DBAdapter db) {
    	
    	
    	/**db.insertTitle("Almonds, shelled", 1, "Item", 11);
    	db.insertTitle("Apple",	1,	"Item",	80);
    	db.insertTitle("Apple juice", 240, "ml", 115);
    	db.insertTitle("Applesauce", 240, "ml", 105);
    	db.insertTitle("Apricot", 3, "Item", 50);
    	db.insertTitle("Artichoke",	1,	"Item",	50);
    	db.insertTitle("Asparagus",	113.4,	"g", 20);
    	db.insertTitle("Avocado", 1, "Item", 305);
    	db.insertTitle("Bacon",	3,	"Strips", 100);
    	db.insertTitle("Bacon, Canadian" ,56.7,	"g", 155);
    	db.insertTitle("Bagel",	1,	"Item",	165);
    	db.insertTitle("Banana", 1,	"Item",	105);
    	db.insertTitle("Barley", 113.4,	"g", 390);
    	db.insertTitle("Beans, kidney",	240, "ml", 230);
    	db.insertTitle("Bologna", 56.7, "g", 165);
    	db.insertTitle("Bun, regular", 1, "Item", 210);
    	db.insertTitle("Bun, round", 1, "Item", 280);
    	db.insertTitle("Cereal, Whole Grain", 27, "g", 110);
    	db.insertTitle("Chicken meatball", 6, "Item", 160);
    	db.insertTitle("Cracker", 7, "Item", 90);
    	db.insertTitle("Egg", 1, "Item", 80);
    	db.insertTitle("Honey", 20, "g", 60);
     	db.insertTitle("Orange Drink, carbonated", 250, "ml", 130);
    	db.insertTitle("Potato Salad", 100, "g", 220);
     	db.insertTitle("Rigatoni", 1, "Item", 220);
    	db.insertTitle("Rice", 100, "g", 130);
    	db.insertTitle("Spaghetti, packaged", 1, "Item", 220);
    	db.insertTitle("Turkey Breast, strips", 1, "Strips", 60);
    	db.insertTitle("Tomato", 1,	"Item",	20);
    	db.insertTitle("Tomato juice",	170.1,	"g", 35);
    	db.insertTitle("Tuna salad",	53.87,	"g", 80);
    	db.insertTitle("Tuna, in oil",	85.05,	"g",165);
    	db.insertTitle("Tuna, in water",85.05,	"g", 135);
    	db.insertTitle("Turkey, white meat",113.4,	"g",200);
    	db.insertTitle("Turnip greens",	240, "ml", 45);
    	db.insertTitle("Turnips", 240,"ml",55);
    	db.insertTitle("Veal chop",	1,	"Item",	260);
    	db.insertTitle("Vegetable oil",	1,	"Tablespoon", 120);
    	db.insertTitle("Walnut pieces",	28.35,	"g",180);
    	db.insertTitle("Yogurt, small", 1, "Item", 60);
    	db.insertTitle("Yogurt, fruit-flavor", 240,"ml",230);
    	db.insertTitle("Yogurt, low-fat", 240,"ml", 127);
    	db.insertTitle("Yogurt, whole",	240,"ml",140);*/
    	


	   	 try {
	   	  
	   		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	   		  DocumentBuilder docBuild = dbf.newDocumentBuilder();
	   		  Document doc = docBuild.parse(fileToBeRead);
	   		  doc.getDocumentElement().normalize();
	   		  NodeList nodeLst = doc.getElementsByTagName("Row");
	   		  Log.i(CAL_TAG, "Information of all food items");
	   	
	   		  for (int i = 1; i < nodeLst.getLength(); i++) {
	   		
	   			  Node fstNode = nodeLst.item(i);
	   			  String foodName = null;
	   			  Double foodAmount = null;
	   			  String unit = null;
	   			  int calorieValue = 0;
	   		    
	   			  if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
	   		  
	   				  	Element fstElmnt = (Element) fstNode;
	   				  	NodeList valueList = fstElmnt.getElementsByTagName("Cell");
	   		      
	   				  	for (int j = 0; j < valueList.getLength(); j++) {
	   		    	  
	   				  		Node secondNode = valueList.item(j);
	   		    	  
	   		    	  
	   				  		if (secondNode.getNodeType() == Node.ELEMENT_NODE) {
	   		    		  
	   				  			Element secondElmnt = (Element) secondNode;
	   				  			NodeList foodList = secondElmnt.getChildNodes();
	   		    		  
	   				  			try {
	   				  				
	   				  				switch (j) 
	   				  				{
	   			    		  	
	   				  					case 0:
	   				  						foodName = foodList.item(0).getFirstChild().getNodeValue();
	   				  					break;
	   			    		  	
	   				  					case 1:
	   				  						foodAmount = Double.parseDouble(foodList.item(0).getFirstChild().getNodeValue());
	   				  					break;
	   			    		  	
	   				  					case 2:
	   				  						unit = foodList.item(0).getFirstChild().getNodeValue();
	   				  					break;
	   			    		  	
	   				  					case 3:
	   				  						calorieValue =  Integer.parseInt(foodList.item(0).getFirstChild().getNodeValue());
	   				  					break;
	   			    		  	
	   				  					default:
	   				  						System.out.println("Error in parsing food list");
	   				  					break;  					    		  	
	   			    		
	   				  				}
	
	   				  			}
	   		    		  
	   				  			catch (NullPointerException e)
	   				  			{
	   				  				e.printStackTrace();
	   				  			}
	   		    		  
	   				  		}
	   				  	}
	   		    	  
	   		    	  
	   		      	}
	   		    
	   			  	  Log.i(CAL_TAG, foodName);
	   			      db.insertTitle(foodName, foodAmount, unit, calorieValue);
	   		  	}
	   		    
	   	   
	   	 }
	   	 
	   	 catch (Exception e) 
	   	 {
	   		 e.printStackTrace();
	   	 }
	   	 
	   	 
    	
	
    }
    
    private void startItemActivity(String selectedFood) {
    	
    	Bundle foodData = new Bundle();
		String food = new String();
		food = selectedFood; 
		Log.i(CAL_TAG, "The value of food is " + food);
		
		try {
			
			db.openDB();
			Cursor temp = db.getTitle(food);
			
			if(temp != null) {
				foodData.putInt(DBAdapter.KEY_ROWID, temp.getInt(0));
				foodData.putString(DBAdapter.KEY_ITEM, food);
				foodData.putDouble(DBAdapter.KEY_AMOUNT, temp.getDouble(2));
				foodData.putString(DBAdapter.KEY_UNIT, temp.getString(3));
				foodData.putInt(DBAdapter.KEY_CALORIE_VALUE, temp.getInt(4));			
			}
			
			temp.close();
			db.closeDB();
			
			/*
			for (int i = 0; i < FOOD_TABLE_SIZE; i++) {
				Log.i(CAL_TAG, "food " + i + " = " + db.getTitle(i+1).getString(1));
				if (food.equals(db.getTitle(i+1).getString(1))) {
					 
					foodData.putInt(DBAdapter.KEY_ROWID, db.getTitle(i+1).getInt(0));
					foodData.putString(DBAdapter.KEY_ITEM, food);
					foodData.putDouble(DBAdapter.KEY_AMOUNT, db.getTitle(i+1).getDouble(2));
					foodData.putString(DBAdapter.KEY_UNIT, db.getTitle(i+1).getString(3));
					foodData.putInt(DBAdapter.KEY_CALORIE_VALUE, db.getTitle(i+1).getInt(4));
				}			 
			} 
			*/
			Log.i(CAL_TAG, "Food Data bundling completed");
			Intent i = new Intent(CalorieGain.this, FoodScreen.class);
			i.putExtras(foodData);
			startSubActivityForResult(i, ACTIVITY_FOOD);
		
		}
		
		catch (Exception e) 
		{
			Log.i(CAL_TAG, e.getMessage());
			Log.i(CAL_TAG, e.getLocalizedMessage());

			for (int i = 0; i < e.getStackTrace().length; i++) {
				
				Log.i(CAL_TAG, e.getStackTrace()[i].getMethodName());
				Log.i(CAL_TAG, Integer.toString(e.getStackTrace()[i].getLineNumber()));
			}
			

		}
    }
    
    private void startSubActivityForResult(Intent i, int activityCode) {
    	
    	Log.i(CAL_TAG, "We are here");
    	this.startActivityForResult(i, activityCode);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		
			case ACTIVITY_FOOD:
				
				totalCalorieGain = totalCalorieGain + data.getExtras().getDouble(FoodScreen.CAL_GAIN, 0);
				Log.i(CAL_TAG, "totalCalorieGain = " + totalCalorieGain);
				displayCalorieValue = totalCalorieGain + " Cal";
				calorieGainDisplay.setText(displayCalorieValue.toCharArray(), 0, displayCalorieValue.length());
				
			break;
			
			default:
				
			break;
		}
	}

	
	 /* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	//@Override
	
	/**
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		// TODO Auto-generated method stub
		
		Log.i(CAL_TAG, "Key Down");
		
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			
			String item = new String(textView.getText().toString());
			Boolean found = false;
			
			for (int i = 0; i < FOODS.length; i++) {
				
				if (item.equals(FOODS[i])) {
					
					startItemActivity(item);
					found = true;
					break;
				}
			}
			
			if (found == false) {
				
				Intent i = new Intent(CalorieGain.this, WebScreen.class);
				startSubActivityForResult(i, ACTIVITY_SEARCH);
				
			}
			
		}
		
		
		return super.onKeyDown(keyCode, event);
	} */
   
	
   

}
