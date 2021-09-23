package br.com.wilson.mielke.baseapplication.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import br.com.wilson.mielke.baseapplication.R
import br.com.wilson.mielke.baseapplication.databinding.CustomDialogBinding

class CustomDialog(context: Context) : AppCompatDialog(context) {

    private var binding: CustomDialogBinding = CustomDialogBinding.inflate(LayoutInflater.from(context))

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setCancelable(false)
        setContentView(binding.root)
        this.window?.attributes?.windowAnimations = R.style.FadeInOutDialogAnimation
        this.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    class Builder(context: Context) {

        var customDialog: CustomDialog = CustomDialog(context)

        private fun getVisibility(stringRes: Int = 0): Int {
            return if (stringRes != 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        private fun getVisibility(stringValue: String = ""): Int {
            return if ("" != stringValue) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        fun image(@DrawableRes image: Int): Builder {
            customDialog.binding.imageDialog.apply {
                setImageResource(image)
                visibility = getVisibility(image)
            }
            return   this
        }

        fun title(@StringRes title: Int): Builder {
            customDialog.binding.tvTitleDialog.apply {
                setText(title)
                visibility = getVisibility(title)
            }
            return this
        }

        fun title(title: String): Builder {
            customDialog.binding.tvTitleDialog.apply {
                text = title
                visibility = getVisibility(title)
            }
            return this

        }

        fun message(@StringRes message: Int): Builder {
            customDialog.binding.tvMessageDialog.apply {
                setText(message)
                visibility = getVisibility(message)
            }
            return this

        }

        fun message(message: String): Builder {
            customDialog.binding.tvMessageDialog.apply {
                text = message
                visibility = getVisibility(message)
            }
            return this
        }

        fun spannedMessage(message: Spanned): Builder {
            customDialog.binding.tvMessageDialog.apply {
                text = message
                visibility = getVisibility(message.toString())
            }
            return this
        }

        fun checkBox(message: String): Builder {
            customDialog.binding.apply {
                textViewCheckBox.text = message
                textViewCheckBox.setOnClickListener {
                    checkboxDialog.isChecked = !checkboxDialog.isChecked
                }
                rlCheckBox.visibility = getVisibility(message)
                rlCheckBox.setOnClickListener {
                    checkboxDialog.isChecked = !checkboxDialog.isChecked
                }
            }
            return this
        }

        fun primaryButton(@StringRes textButton: Int, textColor: Int? = null, listener: ((Boolean) -> Unit)? = null): Builder {
            customDialog.binding.btnPrimary.apply {
                setText(textButton)
                textColor?.let {
                    customDialog.binding.btnPrimary.setTextColor(it)
                }
                visibility = getVisibility(textButton)
                setOnClickListener {
                    listener?.invoke(customDialog.binding.checkboxDialog.isChecked)
                    customDialog.dismiss()
                }
            }
            return this
        }

        fun primaryButton(textButton: String, textColor: Int? = null, listener: ((Boolean) -> Unit)? = null): Builder {
            customDialog.binding.btnPrimary.apply {
                setText(textButton)
                textColor?.let {
                    customDialog.binding.btnPrimary.setTextColor(it)
                }
                visibility = getVisibility(textButton)
                setOnClickListener {
                    listener?.invoke(customDialog.binding.checkboxDialog.isChecked)
                    customDialog.dismiss()
                }
            }
            return this
        }

        fun secondaryButton(textButton: String, textColor: Int? = null, listener: ((Boolean) -> Unit)? = null): Builder {
            customDialog.binding.btnSecondary.apply {
                setText(textButton)
                textColor?.let { customDialog.binding.btnSecondary.setTextColor(it) }
                visibility = getVisibility(textButton)
                setOnClickListener {
                    listener?.invoke(customDialog.binding.checkboxDialog.isChecked)
                    customDialog.dismiss()
                }
            }
            return this
        }

        fun secondaryButton(@StringRes textButton: Int, textColor: Int? = null, listener: ((Boolean) -> Unit)? = null): Builder {
            customDialog.binding.btnSecondary.apply {
                setText(textButton)
                textColor?.let { customDialog.binding.btnSecondary.setTextColor(it) }
                visibility = getVisibility(textButton)
                setOnClickListener {
                    listener?.invoke(customDialog.binding.checkboxDialog.isChecked)
                    customDialog.dismiss()
                }
            }
            return this
        }

        fun build(): CustomDialog {
            return customDialog
        }
    }
}
