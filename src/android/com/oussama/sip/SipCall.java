package com.oussama.sip;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;
import android.content.Context;
import android.Manifest;

import java.util.Date;

public class SipCall extends CordovaPlugin {
  private static final String TAG = "SipCall";
  private SipCallFacade sipCallFacade;
  private static final int RC_MIC_PERM = 2;

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);
    sipCallFacade = new SipCallFacade(cordova.getActivity().getApplicationContext());
  }

  @Override
  public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    if ("login".equals(action)) {
      try{
        Log.d(TAG, "login Called!!");
        sipCallFacade.login(args.getString(0), args.getString(1), args.getString(2), callbackContext);
        callbackContext.success();
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    if ("logout".equals(action)) {
      try{
        Log.d(TAG, "logout Called!!");
        sipCallFacade.logout();
        callbackContext.success();
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    if ("call".equals(action)) {
      try{
        Log.d(TAG, "call Called!!");
        requestPermissions();
        sipCallFacade.call(args.getString(0), args.getString(1));
        callbackContext.success();
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    if ("hangup".equals(action)) {
      try{
        Log.d(TAG, "hangup Called!!");
        sipCallFacade.hangup();
        callbackContext.success();
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    if ("setMicrophoneMuted".equals(action)) {
      try{
        Log.d(TAG, "setMicrophoneMuted Called!!");
        sipCallFacade.setMicrophoneMuted(args.getBoolean(0));
        callbackContext.success();
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    if ("setSpeakerEnabled".equals(action)) {
      try{
        Log.d(TAG, "setSpeakerEnabled Called!!");
        sipCallFacade.setSpeakerEnabled(args.getBoolean(0));
        callbackContext.success();
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }    
    if ("requestPermissions".equals(action)) {
      try{
        Log.d(TAG, "requestPermissions Called!!");
        requestPermissions();
        callbackContext.success();
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    if ("onCallStateChanged".equals(action)) {
      try{
        Log.d(TAG, "onCallStateChanged Called!!");
        sipCallFacade.onCallStateChanged(callbackContext);
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    if ("onRegistrationStateChanged".equals(action)) {
      try{
        Log.d(TAG, "onRegistrationStateChanged Called!!");
        sipCallFacade.onRegistrationStateChanged(callbackContext);
      }catch(Exception e){
        Log.d(TAG, e.getMessage());
        callbackContext.error(e.getMessage());
      }
      return true;
    }
    return false; // Returning false results in a "MethodNotFound" error.
  }

  private void requestPermissions() {
    // We will need the RECORD_AUDIO permission for call
    if (!cordova.hasPermission(Manifest.permission.RECORD_AUDIO)) {
      cordova.requestPermission(this, RC_MIC_PERM, Manifest.permission.RECORD_AUDIO);
    }
  }
}
