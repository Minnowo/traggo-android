package com.github.traggo.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.traggo.R

class WebViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)

        val webView = view.findViewById<WebView>(R.id.webview)

        webView.loadUrl("http://localhost:3030")
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)

        webView.setWebViewClient(
            object : WebViewClient() {
                override fun onReceivedError(
                    webView: WebView,
                    errorCode: Int,
                    description: String,
                    failingUrl: String,
                ) {
                    if (activity != null) {
                        Toast.makeText(activity!!.applicationContext, "Oh no! $description", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onPageFinished(
                    webView: WebView,
                    url: String,
                ) {
                    CookieManager.getInstance().flush()
                }
            },
        )
        return view
    }
}
