package br.com.wilson.mielke.baseapplication.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import br.com.wilson.mielke.baseapplication.databinding.DialogProgressBinding

class ProgressDialog (context: Context) : Dialog(context) {

    private val binding : DialogProgressBinding = DialogProgressBinding.inflate(LayoutInflater.from(context))
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.setContentView(binding.root)
        setCancelable(false)
    }
}