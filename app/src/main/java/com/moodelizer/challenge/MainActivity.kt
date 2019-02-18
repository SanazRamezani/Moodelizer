package com.moodelizer.challenge

import android.icu.math.BigDecimal
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.moodelizer.challenge.widget.TrackpadTouchListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), TrackpadTouchListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coordinates_text.text = String.format(getString(R.string.coordinates), 0.00, 0.00)
        trackPad.setListener(this)
    }

    override fun onTouchTrackpad(x: Float, y: Float) {
        coordinates_text.text = String.format(getString(R.string.coordinates),
            BigDecimal(x.toDouble()).setScale(2, BigDecimal.ROUND_DOWN).toString(),
            BigDecimal(y.toDouble()).setScale(2, BigDecimal.ROUND_DOWN).toString()
        )

        changeBackgroundColor(((x + y) * 50).roundToInt())

    }

    fun changeBackgroundColor(sum: Int){
        var colorRes = R.color.deep_orange_50
        when (sum) {
            in 0..10 -> colorRes = R.color.deep_orange_50
            in 11..20 -> colorRes = R.color.deep_orange_100
            in 21..30 -> colorRes = R.color.deep_orange_200
            in 31..40 -> colorRes = R.color.deep_orange_300
            in 41..50 -> colorRes = R.color.deep_orange_400
            in 51..60 -> colorRes = R.color.deep_orange_500
            in 61..70 -> colorRes = R.color.deep_orange_600
            in 71..80 -> colorRes = R.color.deep_orange_700
            in 81..90 -> colorRes = R.color.deep_orange_800
            in 91..100 -> colorRes = R.color.deep_orange_900
            else -> colorRes = R.color.deep_orange_50
        }
        mainLayout.setBackgroundColor(ContextCompat.getColor(this, colorRes))
    }

}
