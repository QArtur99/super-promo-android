package com.superpromo.superpromo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.superpromo.superpromo.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    companion object {
        val TAG: String = WebViewActivity::class.java.simpleName
        const val ACTION_GO_TO_URL = "ACTION_GO_TO_URL"
        const val ACTION_RESULT = 999
    }

    private lateinit var binding: ActivityWebViewBinding
    val link2 = "https://docs.google.com/gview?embedded=true&url="
    val link =
        "https://www.biedronka.pl/flexpaper/view?format=pdf&subfolder=_cache%2FMyaP4ETIKoUH0KoVkR9ya4E0g%2F&doc=MyaP4ETIKoUH0KoVkR9ya4E0g.pdf"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        intent.getStringExtra(ACTION_GO_TO_URL)
        link2.let {
            binding.webView.webViewClient = WebViewClient()
            onRedirect()
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(link2+ link)
        }
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
    }

    private fun onRedirect() {
        binding.webView.webViewClient = object : WebViewClient() {
        }
    }
}