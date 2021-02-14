package com.superpromo.superpromo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra(ACTION_GO_TO_URL)?.let {
            onRedirect()
            binding.webView.settings.javaScriptEnabled = true
            binding.webView.loadUrl(it)
        }
        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true
    }

    private fun onRedirect() {
        binding.webView.webViewClient = object : WebViewClient() {
        }
    }
}