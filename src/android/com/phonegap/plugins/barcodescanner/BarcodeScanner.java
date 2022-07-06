/**
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) Matt Kane 2010
 * Copyright (c) 2011, IBM Corporation
 * Copyright (c) 2013, Maciej Nux Jaros
 */
package com.phonegap.plugins.barcodescanner;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blikoon.qrcodescanner.QrCodeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BarcodeScanner extends CordovaPlugin {
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private CallbackContext callbackContext;
    CordovaPlugin that;

    /**
     * Constructor.
     */
    public BarcodeScanner() {
    }

    /**
     * Executes the request.
     *
     * This method is called from the WebView thread. To do a non-trivial amount of work, use:
     *     cordova.getThreadPool().execute(runnable);
     *
     * To run on the UI thread, use:
     *     cordova.getActivity().runOnUiThread(runnable);
     *
     * @param action          The action to execute.
     * @param args            The exec() arguments.
     * @param callbackContext The callback context used when calling back into JavaScript.
     * @return                Whether the action was valid.
     *
     * @sa https://github.com/apache/cordova-android/blob/master/framework/src/org/apache/cordova/CordovaPlugin.java
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
       this.that = this;
        this.callbackContext = callbackContext;
        if(action.equals("scan")) {
            scan();
            return true;
        }
        return false;
    }

    /**
     * Starts an intent to scan and decode a barcode.
     */
    public void scan() {
        Intent intent = new Intent(that.cordova.getActivity().getBaseContext(), QrCodeActivity.class);
        intent.setPackage(that.cordova.getActivity().getApplicationContext().getPackageName());
        that.cordova.startActivityForResult(that,intent, REQUEST_CODE_QR_SCAN);
        
    }

    /**
     * Called when the barcode scanner intent completes.
     *
     * @param requestCode The request code originally supplied to startActivityForResult(),
     *                       allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     * @param intent      An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == cordova.getActivity().RESULT_OK) {
            try {
                if (requestCode == REQUEST_CODE_QR_SCAN) {
                    if (data != null) {
                        String lectura = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                        callbackContext.success(lectura);
                    }
                } else {
                    callbackContext.error("No se pudo escanear el código QR");
                }
            } catch (Exception e) {
                e.printStackTrace();
                callbackContext.error(e.getMessage());
            }

        } else {
            callbackContext.error("4003 - Callback fail");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Initiates a barcode encode.
     *
     * @param type Endoiding type.
     * @param data The data to encode in the bar code.
     */
    public void encode(String type, String data) {
         return;
    }

    /**
     * check application's permissions
     */
   public boolean hasPermisssion() {
        return;
   }

    /**
     * We override this so that we can access the permissions variable, which no longer exists in
     * the parent class, since we can't initialize it reliably in the constructor!
     *
     * @param requestCode The code to get request action
     */
   public void requestPermissions(int requestCode)
   {
        return;;
   }

   /**
   * processes the result of permission request
   *
   * @param requestCode The code to get request action
   * @param permissions The collection of permissions
   * @param grantResults The result of grant
   */
  public void onRequestPermissionResult(int requestCode, String[] permissions,
                                         int[] grantResults) throws JSONException
   {
       return;
   }

    /**
     * This plugin launches an external Activity when the camera is opened, so we
     * need to implement the save/restore API in case the Activity gets killed
     * by the OS while it's in the background.
     */
    public void onRestoreStateForActivityResult(Bundle state, CallbackContext callbackContext) {
        return;
    }

}
