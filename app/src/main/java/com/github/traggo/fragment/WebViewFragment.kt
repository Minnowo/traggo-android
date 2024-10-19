package com.github.traggo.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.traggo.CompileTime
import com.github.traggo.R
import com.github.traggo.utils.Constants.TRAGGO_TAG

class WebViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)

        val webView = view.findViewById<WebView>(R.id.webview)

        webView.loadUrl("http://localhost:3030")
        webView.settings.setJavaScriptEnabled(true)
        webView.settings.setSupportZoom(true)
        webView.settings.setDomStorageEnabled(true)

        webView.setWebViewClient(
            object : WebViewClient() {
                override fun onReceivedError(
                    webView: WebView,
                    errorCode: Int,
                    description: String,
                    failingUrl: String,
                ) {
                    Log.w(TRAGGO_TAG, "Webview error: $errorCode $description")

                    if (activity != null) {
                        Toast.makeText(activity!!.applicationContext, "Oh no! $description", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPageFinished(
                    webView: WebView,
                    url: String,
                ) {
                    Log.i(TRAGGO_TAG, "Flushing cookies for $url")
                    Log.d(TRAGGO_TAG, "Has Cookies ${CookieManager.getInstance().hasCookies()}")

                    if (CompileTime.IS_DEBUG) {
                        Log.d(TRAGGO_TAG, "Cookies: ${CookieManager.getInstance().getCookie(url)}")
                    }

                    CookieManager.getInstance().setAcceptCookie(true)
                    CookieManager.getInstance().flush()
                }
            },
        )
        return view
    }
}
