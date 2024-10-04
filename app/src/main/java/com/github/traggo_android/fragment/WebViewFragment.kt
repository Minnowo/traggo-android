package com.github.traggo_android.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.github.traggo_android.R

class WebViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_webview, container, false)

val webView =        view.findViewById<WebView>(R.id.webview)

        webView.loadUrl("http://localhost:3030")
        webView.settings.javaScriptEnabled=true
        webView.settings.setSupportZoom(true)



        return view
    }
}
