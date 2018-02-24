package ui.anwesome.com.kotlinsunlikeview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import ui.anwesome.com.sunlikeview.SunLikeView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view = SunLikeView.create(this)
        fullScreen()
        view.addSunLikeListener({
            Toast.makeText(this, "sun has risen", Toast.LENGTH_SHORT).show()
        }, {
            Toast.makeText(this, "sun has set", Toast.LENGTH_SHORT).show()
        })
    }
}
fun AppCompatActivity.fullScreen() {
    supportActionBar?.hide()
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}
