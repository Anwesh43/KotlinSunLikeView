package ui.anwesome.com.sunlikeview

/**
 * Created by anweshmishra on 24/02/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class SunLikeView(ctx : Context) : View(ctx) {
    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas : Canvas) {

    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}
