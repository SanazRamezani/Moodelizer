package com.moodelizer.challenge.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.moodelizer.challenge.R
import kotlinx.android.synthetic.main.design_trackpad.view.*
import android.R.attr.y
import android.R.attr.x
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat


class TrackPadWidget : FrameLayout {

    lateinit var cursor: ImageView
    lateinit var xAxis: View
    lateinit var yAxis: View
    private lateinit var listener: TrackpadTouchListener

    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        init(context)
    }

    fun init(context: Context){
        val view = LayoutInflater.from(context).inflate(R.layout.design_trackpad, this)

        cursor = view.cursor
        xAxis = view.x_axis
        yAxis = view.y_axis

        setOnTouchListener(object: View.OnTouchListener {
            override fun onTouch(view: View?, motionEvent: MotionEvent): Boolean {
                if (motionEvent.action == MotionEvent.ACTION_UP) {
                    cursor.setVisibility(View.GONE)
                    xAxis.visibility = View.GONE
                    yAxis.visibility = View.GONE
                } else {
                    handleEvent(motionEvent)
                }
                return true
            }
        })

    }

    private fun handleEvent(motionEvent: MotionEvent) {

        var motionX = motionEvent.x
        var motionY = motionEvent.y

        if (cursor.getVisibility() == View.GONE) {
            cursor.setVisibility(View.VISIBLE)
            xAxis.visibility = View.VISIBLE
            yAxis.visibility = View.VISIBLE
        }

        val cursorSize = cursor.getHeight() / 2
        val maxSize = width - cursorSize

        var minX = false
        var maxX = false
        var minY = false
        var maxY = false

        if (motionX < cursorSize) {
            minX = true
            motionX = cursorSize.toFloat()
        } else if (motionX > maxSize) {
            maxX = true
            motionX = maxSize.toFloat()
        }

        if (motionY > maxSize) {
            maxY = true
            motionY = maxSize.toFloat()
        } else if (motionY < cursorSize) {
            minY = true
            motionY = cursorSize.toFloat()
        }

        cursor.setX(motionX - cursorSize)
        cursor.setY(motionY - cursorSize)

        xAxis.y = motionY
        yAxis.x = motionX

        if (listener != null) {
            listener.onTouchTrackpad(
                relativeTrackPad(motionX, cursorSize.toFloat(), minX, maxX),
                relativeTrackPad(height - motionY, cursorSize.toFloat(), maxY, minY)
            )
        }
    }

    fun setListener(listener: TrackpadTouchListener) {
        this.listener = listener
    }


    private fun relativeTrackPad(trackPad: Float, cursorSize: Float,
                                   min: Boolean, max: Boolean): Float {

        var fixedTrackpad = trackPad

        if (min) fixedTrackpad -= cursorSize
        if (max) fixedTrackpad += cursorSize

        val relativeValue = fixedTrackpad * 100 / width

        return relativeValue / 100
    }


}