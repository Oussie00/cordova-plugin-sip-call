
var exec = require('cordova/exec');

var PLUGIN_NAME = 'SipCall';

var SipCall = {
  login: function(username, password, realm, successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'login', [username, password, realm]);
  },

  logout: function(successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'logout');
  },

  call: function(address, displayName, successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'call', [address, displayName]);
  },

  hangup: function(successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'hangup');
  },

  setMicrophoneMuted: function(muted, successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'setMicrophoneMuted', [muted]);
  },

  setSpeakerEnabled: function(enabled, successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'setSpeakerEnabled', [enabled]);
  },

  requestPermissions: function(successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'requestPermissions')
  },

  onCallStateChanged: function(successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'onCallStateChanged')
  },

  onRegistrationStateChanged: function(successCb, errorCb) {
    exec(successCb, errorCb, PLUGIN_NAME, 'onRegistrationStateChanged')
  },
};

module.exports = SipCall;
