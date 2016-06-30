/*package com.snorlon.sevestralkingdoms.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.snorlon.sevestralkingdoms.Sevestral;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Sevestral(), config);
	}
}*/

package com.snorlon.sevestralkingdoms.android;

import android.nfc.NfcAdapter;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.snorlon.sevestralkingdoms.Sevestral;

public class AndroidLauncher extends AndroidApplication {
	public AndroidFeatures ourFeatures;
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 0; // seems reasonable, you can turn it up of course, but bench!
		
		ourFeatures = new AndroidFeatures(this);
		
		initialize(new Sevestral(ourFeatures), config);
	}
	
	public void onResume()
	{
		super.onResume();
		
		ourFeatures.getAdapter();
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
			System.out.println("FUCK");
		}
	}
}

