package com.snorlon.sevestralkingdoms.android;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.snorlon.sevestralkingdoms.GameComponents.DeviceFeatures;

import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;

public class AndroidFeatures extends Activity implements DeviceFeatures
{	
	private NfcAdapter mNfcAdapter = null;//stays null if we don't support nfc
	AndroidApplication ourApp = null;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    
    /*
     * Insert to enable in manifest
     * 
     * 
            <intent-filter>
				<action android:name="android.nfc.action.TECH_DISCOVERED"/>
					<category android:name="android.intent.category.DEFAULT" />
			</intent-filter>
     * 
     */
	
	public AndroidFeatures(AndroidApplication app)
	{		
		/*ourApp = app;
		
		//get our nfc adaptor if we can
		mNfcAdapter = NfcAdapter.getDefaultAdapter(app);
		
		if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
			System.out.println("This device doesn't support NFC.");
 
        }
     
        if (!mNfcAdapter.isEnabled()) {
            System.out.println("NFC is disabled.");
        } else {
        	System.out.println("NFC enabled");
        }
        
        System.out.println("A");
        
        if(getClass() == null)
        {
        	System.out.println("WAOW??????\n\n\n\n\n\n");
        }
        else
        	System.out.println(getClass());
        
        System.out.println("B");
        
        final Intent intent = new Intent(this, getClass());
        System.out.println("C");
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        System.out.println("D");
        mPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        System.out.println("E");
        
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        mTechLists = new String[][] { new String[] { NfcA.class.getName() } };
        mFilters = new IntentFilter[] {
                ndef,
        };
        
       if(getIntent() != null)
    	   handleIntent(getIntent());*/
   }
    
   private void handleIntent(Intent intent) {
       // TODO: handle Intent
       //System.out.println(intent.getAction());
   }
	
	public String getNFCTag()
	{
		return "";
	}

    @Override
    public void onResume() {
        super.onResume();
        //mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
    }

    @Override
    public void onNewIntent(Intent intent) {
        //System.out.println("Foreground dispatch" + "Discovered tag with intent: " + intent);
        //System.out.println("Discovered tag " + " with intent: " + intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        //mNfcAdapter.disableForegroundDispatch(this);
    }
	
	public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        /*final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 
        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);
 
        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};
 
        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType("text/plain");
        } catch (MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
         
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);*/
    }
	
	public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        //adapter.disableForegroundDispatch(activity);
    }
	
	public NfcAdapter getAdapter()
	{
		return mNfcAdapter;
	}
	
	public String checkNFC()
	{
		/*
		if(mNfcAdapter!=null && getIntent()!=null)
		{
			if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
				System.out.println("FUCK");
				return "WAOW\n\n\n";
			}
		}*/
		
		return "";
	}
}
