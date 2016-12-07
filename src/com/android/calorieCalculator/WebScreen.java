package com.android.calorieCalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebScreen extends Activity {
	
	private WebView webview;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.webview);
		
		Log.i(CalorieGain.CAL_TAG, "Ch1");
		webview = (WebView) findViewById(R.id.webview);
		webview.setWebViewClient(new WebScreenClient());;
		webview.getSettings().setJavaScriptEnabled(true);
		webview.loadUrl("http://www.yahoo.com");
		Log.i(CalorieGain.CAL_TAG, "Ch2");
		
	}
	
	private class WebScreenClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	    
	    
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
	        webview.goBack();
	        return true;
	    }

		return super.onKeyDown(keyCode, event);
	}
	
	
	

}
