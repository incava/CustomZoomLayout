package com.incava.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.otaliastudios.zoom.ZoomLayout

class CustomZoomLayout(context: Context, attr: AttributeSet) : ZoomLayout(context,attr) {
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return false
    }
}