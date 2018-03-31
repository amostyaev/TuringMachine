package com.raistlin.turingmachine.ui

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.raistlin.turingmachine.R

class TapeWidget(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private val elements: ArrayList<TextView> = arrayListOf()
    private var initialized = false

    fun showLine(lineItems: List<Char>, state: Int) {
        for (i in 0 until lineItems.size - elements.size) {
            addElement()
        }
        elements.forEachIndexed { index, textView ->
            textView.text = if (index < lineItems.size) lineItems[index].toString() else ""
            textView.setBackgroundResource(if (index == state) R.drawable.tape_element_border_active else R.drawable.tape_element_border)
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (!initialized) {
            addDefaultViews(r - l)
            initialized = true
        }
    }

    private fun addDefaultViews(width: Int) {
        elements.clear()
        val count = width / context.resources.getDimensionPixelSize(R.dimen.tape_element_size)
        for (i in 0..count) {
            addElement()
        }
    }

    @Suppress("DEPRECATION")
    private fun addElement() {
        val elem = TextView(context)
        val elemSize = context.resources.getDimensionPixelSize(R.dimen.tape_element_size)
        val elemMargin = context.resources.getDimensionPixelSize(R.dimen.tape_element_margin)
        val elemLayoutParams = LinearLayout.LayoutParams(elemSize, elemSize)
        elemLayoutParams.setMargins(elemMargin, elemMargin, elemMargin, elemMargin)
        elem.layoutParams = elemLayoutParams
        elem.gravity = Gravity.CENTER
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            elem.setTextAppearance(R.style.TapeElement)
        } else {
            elem.setTextAppearance(context, R.style.TapeElement)
        }
        elem.setBackgroundResource(R.drawable.tape_element_border)
        elements.add(elem)
        addView(elem)
    }

}