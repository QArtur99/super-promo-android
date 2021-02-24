package com.superpromo.superpromo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.android.Intents
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.superpromo.superpromo.R
import com.superpromo.superpromo.databinding.DialogCardNumberBinding
import com.superpromo.superpromo.databinding.ZxingCaptureBinding
import com.superpromo.superpromo.ui.util.ext.setStatusBarGradient
import com.superpromo.superpromo.ui.util.ext.setToolbar


class CustomCaptureActivity : CaptureActivity() {

    lateinit var enterManually: Button
    lateinit var binding: ZxingCaptureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarGradient(this)
    }

    override fun initializeContent(): DecoratedBarcodeView {
        binding = ZxingCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(binding.appBar.toolbar)
        binding.enterManually.setOnClickListener {
            createDialog()
        }
        return binding.zxingBarcodeScanner
    }

    private fun createDialog() {
        val bindingDialog = DialogCardNumberBinding.inflate(layoutInflater)
        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.card_barcode_dialog_number_title)
            .setView(bindingDialog.root)
            .setPositiveButton(R.string.common_btn_ok) { _, _ ->
                onSuccess(bindingDialog)
            }
            .setNegativeButton(R.string.common_btn_cancel) { _, _ ->
            }
            .create()
        dialog.show()
    }

    private fun onSuccess(bindingDialog: DialogCardNumberBinding) {
        val editTextInput = bindingDialog.editText.text.toString()
        val isDigitsOnly = editTextInput.isDigitsOnly()
        val format = when {
            editTextInput.length == 13 && isDigitsOnly -> BarcodeFormat.EAN_13
            else -> BarcodeFormat.CODE_128
        }
        val intent = Intent(Intents.Scan.ACTION)
        intent.putExtra(Intents.Scan.RESULT, editTextInput)
        intent.putExtra(Intents.Scan.RESULT_FORMAT, format.toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}