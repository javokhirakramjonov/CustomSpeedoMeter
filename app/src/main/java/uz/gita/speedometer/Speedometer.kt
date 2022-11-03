package uz.gita.speedometer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

class Speedometer @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attributes, defStyle) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawMainArc(canvas)
        innerArc(canvas)
        smallInterval(canvas)
        bigInterval(canvas)
        numbers(canvas)
        currentSpeed(canvas)
        cursor(canvas)
    }

    private val paint = Paint().apply {
        isAntiAlias = true
    }

    private val radiusMainArc = 400
    private val radiusInnerArc = 200
    private var fullSpeed: Int = 300
    private var currentSpeed: Int = 120

    fun setCurrentSpeed(speed: Int) {
        currentSpeed = max(0, min(speed, fullSpeed))
        invalidate()
    }

    private fun drawMainArc(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 50f
        paint.color = Color.parseColor("#252525")
        canvas.drawArc(
            x - radiusMainArc, y - radiusMainArc, x + radiusMainArc, y + radiusMainArc, 139f, 264f, false,
            paint
        )
    }

    private fun innerArc(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.parseColor("#BABABA")
        canvas.drawArc(
            x - radiusInnerArc, y - radiusInnerArc, x + radiusInnerArc, y + radiusInnerArc, 139f, 264f,
            false,
            paint
        )
    }

    private fun smallInterval(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5f
        paint.color = Color.parseColor("#BABABA")

        val length = 20f
        var angle = 50f.toRadian
        var x1 = 0f
        var x2 = 0f
        var y1 = 0f
        var y2 = 0f
        val radius = radiusMainArc - 60

        for (i in 0..99) {

            x1 = x - radius * sin(angle)
            y1 = y + radius * cos(angle)

            x2 = x1 + length * sin(angle)
            y2 = y1 - length * cos(angle)

            canvas.drawLine(x1, y1, x2, y2, paint)

            angle += 2.62f.toRadian
        }

    }

    private fun bigInterval(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.WHITE

        val length = 40f
        var angle = 50f.toRadian
        var x1 = 0f
        var x2 = 0f
        var y1 = 0f
        var y2 = 0f
        val radius = radiusMainArc - 40

        val angleInterval = 26.2f.toRadian

        for (i in 0..10) {

            x1 = x - radius * sin(angle)
            y1 = y + radius * cos(angle)

            x2 = x1 + length * sin(angle)
            y2 = y1 - length * cos(angle)

            canvas.drawLine(x1, y1, x2, y2, paint)

            angle += angleInterval
        }

    }

    private fun numbers(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.WHITE

        val length = 50f
        var angle = 50f.toRadian
        var x1 = 0f
        var x2 = 0f
        var y1 = 0f
        var y2 = 0f
        val radius = radiusMainArc - 80

        for (i in 0..10) {

            x1 = x - radius * sin(angle)
            y1 = y + radius * cos(angle)

            x2 = x1 + length * sin(angle)
            y2 = y1 - length * cos(angle)

            paint.strokeWidth = 0f
            paint.textSize = 40f
            paint.style = Paint.Style.FILL
            paint.color = Color.WHITE
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("${i * fullSpeed / 10}", x2, y2, paint)

            angle += 26.2f.toRadian
        }

    }

    private fun currentSpeed(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f + 30
        paint.textSize = 120f
        paint.isFakeBoldText = true
        canvas.drawText("$currentSpeed", x, y, paint)
    }

    private fun cursor(canvas: Canvas) {
        val x = width / 2f
        val y = height / 2f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 50f
        paint.color = Color.parseColor("#FD9502")

        val currentAngle = currentSpeed * 264f / fullSpeed

        canvas.drawArc(
            x - radiusMainArc, y - radiusMainArc, x + radiusMainArc, y + radiusMainArc, 139f, currentAngle, false,
            paint
        )

        val xx = x + (radiusMainArc) * cos(221f.toRadian - currentAngle.toRadian)
        val yy = y - (radiusMainArc) * sin(221f.toRadian - currentAngle.toRadian)

        paint.style = Paint.Style.FILL
        paint.strokeWidth = 0f

        canvas.drawCircle(xx, yy, 25f, paint)
    }
}