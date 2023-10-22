package com.project.demiwatch.core.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.project.demiwatch.R
import com.project.demiwatch.core.utils.constants.PatientStatus

class CustomPatientStatus: AppCompatTextView {
    private var status: String = PatientStatus.NOT_ACTIVE.status

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun init() {
        text = PatientStatus.NOT_ACTIVE.status
        setBackground(context, R.color.dark_grey)
    }

    fun setStatus(status: String){
        this.status = status
        updateStatus()
    }

    private fun setBackground(context: Context, colorId: Int){

        setTextColor(ContextCompat.getColor(context, colorId))
        setBackgroundColor( ColorUtils.setAlphaComponent(
            ContextCompat.getColor(
                context,
                colorId,
            ), ALPHA_OPACITY
        ))

    }

    private fun updateStatus(){
        when(status){
            PatientStatus.NOT_ACTIVE.status ->{
                text = PatientStatus.NOT_ACTIVE.status
                setBackground(context, R.color.dark_grey)
            }
            PatientStatus.ACTIVE.status ->{
                text = PatientStatus.ACTIVE.status
                setBackground(context, R.color.blue)
            }
            PatientStatus.DANGER.status ->{
                text = PatientStatus.DANGER.status
                setBackground(context, R.color.red)
            }
            PatientStatus.SAFE.status ->{
                text = PatientStatus.SAFE.status
                setBackground(context, R.color.green)
            }
            PatientStatus.TROUBLE.status ->{
                text = PatientStatus.TROUBLE.status
                setBackground(context, R.color.red)
            }
        }
    }

    companion object{
        const val ALPHA_OPACITY = 64
    }
}