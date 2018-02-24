package ui.anwesome.com.sunlikeview

/**
 * Created by anweshmishra on 24/02/18.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
class SunLikeView(ctx : Context) : View(ctx) {
    val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = SunLikeRenderer(this)
    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0, var jDir : Int = 1) {
        val scales : Array<Float> = arrayOf(0f, 0f)
        fun update(stopcb : (Float) -> Unit) {
            scales[j] += 0.1f * dir
            if(Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += jDir
                if(Math.abs(scales[j] - prevScale) > 1) {
                    jDir *= -1
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }
        fun startUpdating(startcb : () -> Unit) {
            if(dir == 0f) {
                dir = 1f
                startcb()
            }
        }
    }
    data class Animator(var view : View, var animated : Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if( !animated ) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class SunLike(var w : Float, var h : Float) {
        val state = State()
        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
        fun draw(canvas : Canvas, paint : Paint) {
            val r = Math.min(w, h) / 3
            val size = Math.min(w, h) / 15
            val k = 10
            val deg = 360f / k
            canvas.save()
            canvas.translate(w / 2, h / 2)
            canvas.drawCircle((h/2 + r) * (1 - state.scales[0]) , 0f, r, paint)
            for(i in 0 .. k - 1) {
                canvas.save()
                canvas.rotate(deg * i)
                canvas.drawLine( r + size , 0f , r + size + size * state.scales[1], 0f, paint)
                canvas.restore()
            }
            canvas.restore()
        }
    }
    data class SunLikeRenderer(var view : SunLikeView, var time : Int = 0) {
        var sunLike : SunLike ?= null
        val animator = Animator(view)
        fun render(canvas : Canvas, paint : Paint) {
            if(time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                sunLike = SunLike(w, h)
                paint.strokeWidth = Math.min(w , h)/60
                paint.strokeCap = Paint.Cap.ROUND
                paint.color = Color.parseColor("#BF360C")
            }
            canvas.drawColor(Color.parseColor("#212121"))
            sunLike?.draw(canvas, paint)
            time++
            animator.animate {
                sunLike?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            sunLike?.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity : Activity):SunLikeView {
            val view = SunLikeView(activity)
            activity.setContentView(view)
            return view
        }
    }
}
