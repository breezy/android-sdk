package com.breezy.android.sdk;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

public class BreezyIntent extends Intent {

	public final static String APP = "com.breezy.android";
	public final static String ACTION = Intent.ACTION_SEND;

	private BreezyIntent() {
		super();
		this.setPackage(APP);
		this.setAction(ACTION);
	}

	public BreezyIntent(String text) {
		this(text, false);
	}

	public BreezyIntent(String text, boolean isHtml) {
		this();
		if (isHtml)
			this.setType("text/html");
		else
			this.setType("text/plain");
		this.putExtra(Intent.EXTRA_TEXT, text);
	}

	public BreezyIntent(String type, Uri uri) {
		this();
		this.setType(type);
		this.putExtra(Intent.EXTRA_STREAM, uri);
	}

	public static boolean isIntentAvailable(Context context)
			throws NameNotFoundException {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent();
		intent.setAction(ACTION);
		intent.setType("file/*");

		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);

		for (ResolveInfo resolveInfo : list) {
			String p = resolveInfo.activityInfo.packageName;
			if (p != null && p.startsWith(APP)) {
				PackageInfo pi = packageManager.getPackageInfo(p, 0);
				int versionCode = pi.versionCode;

				if (versionCode < 9)
					return false;
				else
					return true;
			}
		}
		return false;
	}

}
