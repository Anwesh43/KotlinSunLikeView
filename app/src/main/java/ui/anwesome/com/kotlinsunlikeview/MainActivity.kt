package ui.anwesome.com.kotlinsunlikeview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.sunlikeview.SunLikeView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SunLikeView.create(this)
    }
}
