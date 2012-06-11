package com.breezy.helloWorldPrint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.breezy.android.sdk.BreezyIntent;

public class HelloWorldPrintActivity extends Activity {

	private final static String testPage = "testpage.pdf";
	private final static String testImage = "breezy_splash.png";
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try {
			loadResource(testPage);
			loadResource(testImage);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Button b = (Button) this.findViewById(R.id.buttonPrint);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					submit(v);
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public void submit(View view) throws NameNotFoundException {
		if (!BreezyIntent.isIntentAvailable(this)) {
			Intent intent = new Intent(this, DisplayMessageActivity.class);
			this.startActivity(intent);
			return;
		}

		BreezyIntent breezyIntent = null;
		
		RadioGroup rbg = (RadioGroup) this.findViewById(R.id.radioButtonGroup1);
		int i = rbg.getCheckedRadioButtonId();

		String dir = this.getExternalFilesDir(getPackageName()).getAbsolutePath();

		switch (i) {
		case R.id.radioButton1:
			breezyIntent = new BreezyIntent("This could be the body of an email.");
			break;
		case R.id.radioButton2:
			breezyIntent = new BreezyIntent("<html>This is what <b>HTML</b> looks like.</html>", true);
			break;
		case R.id.radioButton3:
			Uri uri = Uri.parse("file://" + dir + "/" + testPage);
			breezyIntent = new BreezyIntent("application/pdf", uri);			
			break;
		case R.id.radioButton4:
			// optionally pass a content Uri
			// uri = Uri.parse("content://media/external/images/media/2");
			uri = Uri.parse("file://" + dir + "/" + testImage);
			breezyIntent = new BreezyIntent("image/png", uri);						
			break;
		}

		startActivity(breezyIntent);
	}

	private void loadResource(String fileName) throws IOException {
		AssetManager mgr = this.getAssets();
		File tmp = null;

		File dir = this.getExternalFilesDir(getPackageName());

		for (File f : dir.listFiles()) {
			if (f.getName().equals(fileName))
				return;
		}

		InputStream is = null;
		OutputStream fos = null;
		try {
			tmp = new File(dir, fileName);

			is = mgr.open(fileName);
			fos = new FileOutputStream(tmp);

			byte[] buffer = new byte[4096];

			int len;
			while ((len = is.read(buffer)) != -1)
				fos.write(buffer, 0, len);

		} finally {
			if (fos != null)
				fos.close();
			if (is != null)
				is.close();
		}
	}

	// supported mime types
	// <data android:mimeType="text/html" />
	// <data android:mimeType="text/plain" />
	// <data android:mimeType="text/richtext" />
	// <data android:mimeType="text/rtf" />
	// <data android:mimeType="application/pdf" />
	// <data android:mimeType="application/msword" />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.wordprocessingml.document"
	// />
	// <data android:mimeType="application/vnd.ms-word.document.macroEnabled.12"
	// />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.wordprocessingml.template"
	// />
	// <data android:mimeType="application/vnd.ms-word.template.macroEnabled.12"
	// />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
	// />
	// <data android:mimeType="application/vnd.ms-excel.sheet.macroEnabled.12"
	// />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.spreadsheetml.template"
	// />
	// <data
	// android:mimeType="application/vnd.ms-excel.template.macroEnabled.12" />
	// <data
	// android:mimeType="application/vnd.ms-excel.sheet.binary.macroEnabled.12"
	// />
	// <data android:mimeType="application/vnd.ms-excel.addin.macroEnabled.12"
	// />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.presentationml.presentation"
	// />
	// <data
	// android:mimeType="application/vnd.ms-powerpoint.presentation.macroEnabled.12"
	// />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.presentationml.slideshow"
	// />
	// <data
	// android:mimeType="application/vnd.ms-powerpoint.slideshow.macroEnabled.12"
	// />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.presentationml.template"
	// />
	// <data
	// android:mimeType="application/vnd.ms-powerpoint.template.macroEnabled.12"
	// />
	// <data
	// android:mimeType="application/vnd.ms-powerpoint.addin.macroEnabled.12" />
	// <data
	// android:mimeType="application/vnd.openxmlformats-officedocument.presentationml.slide"
	// />
	// <data
	// android:mimeType="application/vnd.ms-powerpoint.slide.macroEnabled.12" />
	// <data android:mimeType="application/oda" />
	// <data android:mimeType="application/vnd.ms-powerpoint" />
	// <data android:mimeType="application/vnd.ms-project" />
	// <data android:mimeType="application/vnd.ms-excel" />
	// <data android:mimeType="application/vnd.ms-works" />
	// <data android:mimeType="application/rtf" />

}