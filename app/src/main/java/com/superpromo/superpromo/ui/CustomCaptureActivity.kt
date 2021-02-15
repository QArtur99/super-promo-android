package com.superpromo.superpromo.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.superpromo.superpromo.R
import com.superpromo.superpromo.ui.util.ext.setStatusBarGradient
import com.superpromo.superpromo.ui.util.ext.setToolbar


class CustomCaptureActivity : CaptureActivity() {

    lateinit var timerQR: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarGradient(this)
    }

    override fun initializeContent(): DecoratedBarcodeView {
        setContentView(R.layout.zxing_capture)
        setToolbar(findViewById(R.id.toolbar))
        timerQR = findViewById(R.id.timerQR)
        return findViewById<View>(R.id.zxing_barcode_scanner) as DecoratedBarcodeView
    }
}