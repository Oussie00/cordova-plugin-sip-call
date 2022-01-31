package com.oussama.sip;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.linphone.core.Account;
import org.linphone.core.AccountParams;
import org.linphone.core.Address;
import org.linphone.core.AuthInfo;
import org.linphone.core.Call;
import org.linphone.core.CallParams;
import org.linphone.core.Core;
import org.linphone.core.CoreListenerStub;
import org.linphone.core.Factory;
import org.linphone.core.MediaEncryption;
import org.linphone.core.RegistrationState;
import org.linphone.core.TransportType;
import org.linphone.core.tools.Log;
import org.linphone.mediastream.MediastreamerAndroidContext;


import androidx.annotation.NonNull;

import android.content.Context;

public class SipCallFacade {
  private static final String TAG = "SipCallFacade";
  private Core core;
  private AuthInfo authInfo;
  private CallbackContext callStateCallbackContext = null;
  private CallbackContext registrationStateCallbackContext = null;

  public SipCallFacade(Context context) {
    Factory factory = Factory.instance();
    core = factory.createCore(null, null, context);
  }

  private CoreListenerStub coreListener = new CoreListenerStub() {
    @Override
    public void onAccountRegistrationStateChanged(@NonNull Core core, @NonNull Account account, RegistrationState state,
        @NonNull String message) {
      super.onAccountRegistrationStateChanged(core, account, state, message);
      if (state == RegistrationState.None) {
        if(registrationStateCallbackContext != null){
            PluginResult result = new PluginResult(PluginResult.Status.OK, RegistrationState.None.name());
            result.setKeepCallback(true);
            registrationStateCallbackContext.sendPluginResult(result);
        }        
        Log.e("registration None");
      }
      if (state == RegistrationState.Progress) {
        if(registrationStateCallbackContext != null){
            PluginResult result = new PluginResult(PluginResult.Status.OK, RegistrationState.Progress.name());
            result.setKeepCallback(true);
            registrationStateCallbackContext.sendPluginResult(result);
        }
        Log.e("registration Progress");
      }              
      if (state == RegistrationState.Failed) {
        if(registrationStateCallbackContext != null){
            PluginResult result = new PluginResult(PluginResult.Status.OK, RegistrationState.Failed.name());
            result.setKeepCallback(true);
            registrationStateCallbackContext.sendPluginResult(result);
        }        
        Log.e("registration failed");
      } 
      if (state == RegistrationState.Ok) {
        if(registrationStateCallbackContext != null){
            PluginResult result = new PluginResult(PluginResult.Status.OK, RegistrationState.Ok.name());
            result.setKeepCallback(true);
            registrationStateCallbackContext.sendPluginResult(result);
        }
        Log.e("registration succeeded");
      }

      if (state == RegistrationState.Cleared) {
        if(registrationStateCallbackContext != null){
            PluginResult result = new PluginResult(PluginResult.Status.OK, RegistrationState.Cleared.name());
            result.setKeepCallback(true);
            registrationStateCallbackContext.sendPluginResult(result);
        }
        Log.e("registration Cleared");
      }      
    }

    @Override
    public void onCallStateChanged(@NonNull Core core, @NonNull Call call, Call.State state, @NonNull String message) {
      super.onCallStateChanged(core, call, state, message);
      if (state == Call.State.OutgoingInit) {
        if(callStateCallbackContext != null){
            PluginResult result = new PluginResult(PluginResult.Status.OK, Call.State.OutgoingInit.name());
            result.setKeepCallback(true);
            callStateCallbackContext.sendPluginResult(result);
        }
        Log.e("Call OutgoingInit");
      }
      if (state == Call.State.OutgoingRinging) {
        if(callStateCallbackContext != null){
          PluginResult result = new PluginResult(PluginResult.Status.OK, Call.State.OutgoingRinging.name());
          result.setKeepCallback(true);
          callStateCallbackContext.sendPluginResult(result);
        }
        Log.e("Call OutgoingRinging");
      }
      if (state == Call.State.OutgoingProgress) {
        if(callStateCallbackContext != null){
          PluginResult result = new PluginResult(PluginResult.Status.OK, Call.State.OutgoingProgress.name());
          result.setKeepCallback(true);
          callStateCallbackContext.sendPluginResult(result);
        }
        Log.e("Call OutgoingProgress");
      }
      if (state == Call.State.Connected) {
        if(callStateCallbackContext != null){
          PluginResult result = new PluginResult(PluginResult.Status.OK, Call.State.Connected.name());
          result.setKeepCallback(true);
          callStateCallbackContext.sendPluginResult(result);
        }
        Log.e("Call Connected");
      }
      if (state == Call.State.Resuming) {
        if(callStateCallbackContext != null){
          PluginResult result = new PluginResult(PluginResult.Status.OK, Call.State.Resuming.name());
          result.setKeepCallback(true);
          callStateCallbackContext.sendPluginResult(result);
        }
        Log.e("Call Resuming");
      }      
      if (state == Call.State.End) {
        if(callStateCallbackContext != null){
          PluginResult result = new PluginResult(PluginResult.Status.OK, Call.State.End.name());
          result.setKeepCallback(true);
          callStateCallbackContext.sendPluginResult(result);
        }
        Log.e("Call Ended");
      }
      if (state == Call.State.Error) {
        if(callStateCallbackContext != null){
          PluginResult result = new PluginResult(PluginResult.Status.OK, Call.State.Error.name());
          result.setKeepCallback(true);
          callStateCallbackContext.sendPluginResult(result);
        }
        Log.e("Call Errored");
      }
    }
  };

  public void login(String username, String password, String realm, CallbackContext callbackContext) {
    TransportType transportType = TransportType.Udp;

    // To configure a SIP account, we need an Account object and an AuthInfo object
    // The first one is how to connect to the proxy server, the second one stores
    // the credentials

    // The auth info can be created from the Factory as it's only a data class
    // userID is set to null as it's the same as the username in our case
    // ha1 is set to null as we are using the clear text password. Upon first
    // register, the hash will be computed automatically.
    // The realm will be determined automatically from the first register, as well
    // as the algorithm
    authInfo = Factory.instance().createAuthInfo(username, null, password, null, realm, null, null);

    // Account object replaces deprecated ProxyConfig object
    // Account object is configured through an AccountParams object that we can
    // obtain from the Core
    AccountParams accountParams = core.createAccountParams();

    // A SIP account is identified by an identity address that we can construct from
    // the username and domain
    Address identity = Factory.instance().createAddress("sip:" + username + "@" + realm);
    accountParams.setIdentityAddress(identity);

    // We also need to configure where the proxy server is located
    Address address = Factory.instance().createAddress("sip:" + realm);
    // We use the Address object to easily set the transport protocol
    address.setTransport(transportType);
    accountParams.setServerAddress(address);
    // And we ensure the account will start the registration process
    accountParams.setRegisterEnabled(true);

    // Now that our AccountParams is configured, we can create the Account object
    Account account = core.createAccount(accountParams);

    // Now let's add our objects to the Core
    core.addAuthInfo(authInfo);
    core.addAccount(account);

    // Also set the newly added account as default
    core.setDefaultAccount(account);

    // To be notified of the connection status of our account, we need to add the
    // listener to the Core
    core.addListener(coreListener);
    // Finally we need the Core to be started for the registration to happen (it
    // could have been started before)
    core.start();
  }

  public void logout() {
    core.clearAccounts();
    core.clearAllAuthInfo();
  }

  public void call(String address, String displayName) {
    // As for everything we need to get the SIP URI of the remote and convert it to
    // an Address
    String realm = authInfo.getRealm();
    String remoteSipUri = "sip:" + address + "@" + realm;
    Address remoteAddress = Factory.instance().createAddress(remoteSipUri);
    if (remoteAddress == null) {
      // If address parsing fails, we can't continue with outgoing call process
      Log.e(" invalid remote address");
    }

    // We also need a CallParams object
    // Create call params expects a Call object for incoming calls, but for outgoing
    // we must use null safely
    CallParams params = core.createCallParams(null);
    // We can now configure it
    // Here we ask for no encryption but we could ask for ZRTP/SRTP/DTLS
    params.setMediaEncryption(MediaEncryption.None);

    // Finally we start the call
    core.inviteAddressWithParams(remoteAddress, params);
    // Call process can be followed in onCallStateChanged callback from core
    // listener
  }

  public void hangup() {
    if (core.getCallsNb() == 0) {
      return;
    }

    Call call = core.getCurrentCall();
    call = core.getCurrentCall() != null ? call : core.getCalls()[0];

    call.terminate();
  }

  public void onCallStateChanged(CallbackContext callbackContext) {
    callStateCallbackContext = callbackContext;
  }

  public void onRegistrationStateChanged(CallbackContext callbackContext) {
    registrationStateCallbackContext = callbackContext;
  }

  public void setMicrophoneMuted(Boolean muted) {
    if (core.getCallsNb() == 0) {
      return;
    }

    Call call = core.getCurrentCall();
    call = core.getCurrentCall() != null ? call : core.getCalls()[0];

    call.setMicrophoneMuted(muted);
  }

  public void setSpeakerEnabled(Boolean enabled) {
    if(enabled) {
        MediastreamerAndroidContext.enableSpeaker();
    } else {
        MediastreamerAndroidContext.enableEarpiece();
    }
  }
}
