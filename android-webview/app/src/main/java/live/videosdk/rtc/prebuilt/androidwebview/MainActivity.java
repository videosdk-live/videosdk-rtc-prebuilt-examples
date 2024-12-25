package live.videosdk.rtc.prebuilt.androidwebview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private String token = "REPLACE_YOUR_TOKEN_HERE";

  private String prebuiltVersion = "0.3.43";

  private String meetingId = "YOUR_MEETING_ID";

  private String name = "John Doe";
  private String micEnabled = "true";
  private String webcamEnabled = "true";

  private int PERMISSION_REQUEST_CODE = 1234;
  private String[] requiredDangerousPermissions = {
    Manifest.permission.CAMERA,
    Manifest.permission.MODIFY_AUDIO_SETTINGS,
    Manifest.permission.RECORD_AUDIO,
  };

  private WebView myWebView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    myWebView = new WebView(this);
    setContentView(myWebView);

    WebSettings webSettings = myWebView.getSettings();
    webSettings.setJavaScriptEnabled(true);

    myWebView.setWebViewClient(new WebViewClient());
    myWebView.setWebChromeClient(
      new WebChromeClient() {
        @Override
        public void onPermissionRequest(PermissionRequest request) {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            request.grant(request.getResources());
          }
        }
      }
    );
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (myWebView.getUrl() == null) {
      if (
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isPendingPermissions()
      ) {
        // This explicitly requests the camera and audio permissions.
        // It's fine for a demo app but should probably be called earlier in the flow,
        // on a user interaction instead of onResume.
        requestCameraAndAudioPermissions();
      } else {
        loadPrebuilt();
      }
    }
  }

  private void loadPrebuilt() {
    Uri.Builder builder = new Uri.Builder();

    builder
      .scheme("https")
      .authority("embed.videosdk.live")
      .appendPath("rtc-js-prebuilt")
      .appendPath(prebuiltVersion)
      .appendQueryParameter("name", name) // Name Parameter
      .appendQueryParameter("micEnabled", micEnabled) // intial Mic Status Parameter
      .appendQueryParameter("webcamEnabled", webcamEnabled) // initial webcam status paramter
      .appendQueryParameter("meetingId", meetingId)
      .appendQueryParameter("redirectOnLeave", "https://videosdk.live")
      .appendQueryParameter("token", token);

    String url = builder.build().toString();
    myWebView.loadUrl(url);
  }

  @Override
  public void onRequestPermissionsResult(
    int requestCode,
    @NonNull String[] permissions,
    @NonNull int[] grantResults
  ) {
    if (requestCode == PERMISSION_REQUEST_CODE) {
      if (grantResultsContainsDenials(grantResults)) {
        // Show some permissions required dialog.
      } else {
        // All necessary permissions granted, continue loading.
        loadPrebuilt();
      }
    } else {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private void requestCameraAndAudioPermissions() {
    requestPermissions(getPendingPermissions(), PERMISSION_REQUEST_CODE);
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  private String[] getPendingPermissions() {
    String[] permissions = {};
    ArrayList<String> pendingPermissions = new ArrayList<String>();
    for (String permission : requiredDangerousPermissions) {
      if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
        pendingPermissions.add(permission);
      }
    }
    return pendingPermissions.toArray(permissions);
  }

  private boolean isPendingPermissions() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return false;
    } else return getPendingPermissions().length > 0;
  }

  private boolean grantResultsContainsDenials(int[] grantResults) {
    for (int result : grantResults) {
      if (result == PackageManager.PERMISSION_DENIED) {
        return true;
      }
    }
    return false;
  }
}
