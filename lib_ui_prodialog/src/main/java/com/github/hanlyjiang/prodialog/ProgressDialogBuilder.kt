package com.github.hanlyjiang.prodialog

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.github.hanlyjiang.prodialog.databinding.DialogProgressBinding


/**
 * 简要的进度对话框
 */
class ProgressDialogBuilder {

    val realBuilder: AlertDialog.Builder

    constructor(context: Context) : this(context, R.style.Dialog_Custom)

    constructor(context: Context, styleRes: Int) {
        realBuilder = AlertDialog.Builder(context, styleRes)
    }

    private var isDarkMode: Boolean = false
    private var msg: String? = null

    private var binding: DialogProgressBinding? = null

    fun setIsDarkMode(isDark: Boolean): ProgressDialogBuilder {
        isDarkMode = isDark
        return this
    }

    fun setMessage(msg: String): ProgressDialogBuilder {
        this.msg = msg
        return this
    }

    fun setCancelable(cancelable: Boolean): ProgressDialogBuilder {
        realBuilder.setCancelable(cancelable)
        return this
    }

    fun show(): AlertDialog {
        val dialog = create()
        dialog.show()
        return dialog
    }

    fun create(): AlertDialog {
        binding = DialogProgressBinding.inflate(LayoutInflater.from(realBuilder.context))
        msg?.let {
            binding?.setVariable(BR.progressDialogMsg, it)
        }

        binding?.setVariable(BR.progressDialogIsDarkMode, isDarkMode)
        realBuilder.setView(binding?.root)
        val dialog = realBuilder.create()
        val degree = 20
        val step = 360 / degree
        dialog.setOnShowListener {
            val values = FloatArray(step + 1) { it * degree.toFloat() }
            val rotationAnimator = ObjectAnimator.ofFloat(binding?.ivProgress, "rotation", *values)
            rotationAnimator.setInterpolator { interpolatorValue ->
                (interpolatorValue * step).toInt() / step.toFloat()
            }
            rotationAnimator.duration = 1000L
            rotationAnimator.repeatCount = -1
            rotationAnimator.repeatMode = ValueAnimator.RESTART
            rotationAnimator.start()
        }
        dialog.setOnDismissListener {
            binding?.ivProgress?.clearAnimation()
        }
        return dialog
    }

}