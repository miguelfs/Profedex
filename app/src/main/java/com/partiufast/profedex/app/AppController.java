package com.partiufast.profedex.app;

import android.app.Application;
import android.text.TextUtils;

import com.partiufast.profedex.data.User;

public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();

	public User user;

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		user = new User();
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}
}