package live.videosdk.rtc.prebuilt.videosdkprebuiltwebview

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {

    //Declare following variables
    private val token = ""

    private val prebuiltVersion = "0.3.21"

    private val meetingId = "wyea-c8vp-ivt6"

    private val name = "John Doe"
    private val micEnabled = "true"
    private val webcamEnabled = "true"

    private val PERMISSION_REQUEST_CODE = 1234
    private val requiredDangerousPermissions = arrayOf<String>(
        Manifest.permission.CAMERA,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO
    )

    private var myWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myWebView =  WebView(this)
        setContentView(myWebView)

        val webSettings = myWebView!!.settings
        webSettings.javaScriptEnabled = true

        myWebView!!.webViewClient = WebViewClient()
        myWebView!!.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.resources)
                }
            }
        }
    }

    private fun loadPrebuilt(){
        val builder: Uri.Builder = Uri.Builder()

        builder.scheme("https")
            .authority("embed.videosdk.live")
            .appendPath("rtc-js-prebuilt")
            .appendPath(prebuiltVersion)
            .appendQueryParameter("name", name) // Name Parameter
            .appendQueryParameter("micEnabled", micEnabled) // intial Mic Status Parameter
            .appendQueryParameter("webcamEnabled", webcamEnabled) // initial webcam status paramter
            .appendQueryParameter("meetingId", meetingId)
            .appendQueryParameter("redirectOnLeave", "https://videosdk.live")
            .appendQueryParameter("token", token)

        val url: String = builder.build().toString()
        myWebView!!.loadUrl(url)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResultsContainsDenials(grantResults)) {
                // Show some permissions required dialog.
            } else {
                // All necessary permissions granted, continue loading.
                loadPrebuilt()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onResume() {
        super.onResume()
        if (myWebView!!.url == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isPendingPermissions()) {
                // This explicitly requests the camera and audio permissions.
                // It's fine for a demo app but should probably be called earlier in the flow,
                // on a user interaction instead of onResume.
                requestCameraAndAudioPermissions()
            } else {
                loadPrebuilt()
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun requestCameraAndAudioPermissions() {
        requestPermissions(pendingPermissions, PERMISSION_REQUEST_CODE)
    }

    @get:RequiresApi(api = Build.VERSION_CODES.M)
    private val pendingPermissions: Array<String>
        private get() {
            val pendingPermissions: MutableList<String> = ArrayList<String>()
            for (permission in requiredDangerousPermissions) {
                if (checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    pendingPermissions.add(permission)
                }
            }
            return pendingPermissions.toTypedArray()
        }

    private fun isPendingPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            false
        } else pendingPermissions.isNotEmpty()
    }

    private fun grantResultsContainsDenials(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return true
            }
        }
        return false
    }

}
