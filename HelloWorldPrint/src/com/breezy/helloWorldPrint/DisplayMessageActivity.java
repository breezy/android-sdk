package com.breezy.helloWorldPrint;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.downloadbreezy);
		
		Button b = (Button)this.findViewById(R.id.buttonDownloadBreezy);
		b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	submit(v);
            }
        });
		
    }
    
    public void submit(View view) {
    	Intent goToMarket = new Intent(Intent.ACTION_VIEW)
        .setData(Uri.parse("market://details?id=com.breezy.android"));
    	startActivity(goToMarket);    
    }
}