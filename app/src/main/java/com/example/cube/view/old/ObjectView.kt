package com.example.cube.view.old

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.*
import com.example.cube.`object`.Object
import com.example.cube.`object`.structs.Point
import com.example.cube.view.*
import com.example.cube.view.gesture.ScaleGestureListener
import com.example.cube.view.gesture.SimpleGestureListener
import com.example.cube.view.old.structs.ProjectedConnection
import com.example.cube.view.old.structs.ProjectedPoint
import com.example.cube.view.old.structs.Scene
import kotlinx.coroutines.*


class ObjectView: SurfaceView, SurfaceHolder.Callback {

    private val scene = Scene()

    private val scaleListener = ScaleGestureListener(scene)
    private val gestureListener = SimpleGestureListener(scene)

    private val scaleDetector = ScaleGestureDetector(context, scaleListener)
    private val gestureDetector = GestureDetector(context, gestureListener)

    private val paint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        strokeWidth = 1F
    }

    private var running: Boolean = false
    private var viewJob: Job? = null

    private val surfaceHolder by lazy {
        val holder = holder
        holder.addCallback(this)
        holder
    }

    var currentObject: Object? = null

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(ev)
        gestureDetector.onTouchEvent(ev)
        when {
            ev.action == MotionEvent.ACTION_DOWN && ev.pointerCount == 1 -> {
                scene.isTouching = true
            }
            ev.action == MotionEvent.ACTION_UP && ev.pointerCount == 1 -> {
                scene.isTouching = false
            }
        }
        return true
    }

    fun pause() {
        running = false
        runBlocking {
            viewJob?.cancelAndJoin()
        }
    }

    fun resume() {
        if (!running) {
            running = true
            viewJob = GlobalScope.launch {
                while (running) {
                    if (surfaceHolder.surface.isValid) {
                        drawObject()
                    }
                    scene.move()
                }
            }
        }
    }

    private fun drawObject() {
        currentObject?.apply {
            val points = mutableMapOf<Point, ProjectedPoint>()
            this.points.forEach {
                val vec = it.toVector()
                val rotatedX = rotationX(scene.angleX).preMultiply(vec)
                val rotatedY = rotationY(scene.angleY).preMultiply(rotatedX)
                val rotated = rotationZ(scene.angleZ).preMultiply(rotatedY)
                val scale = 100.0 * scene.scaleFactor
                val scaled = scale(scale).preMultiply(rotated)
                val projection = projection().preMultiply(scaled)
                val projectedPoint = ProjectedPoint(it, projection)
                points[it] = projectedPoint
            }
            drawPoints(points.values.toList())
            val connections = connections.map {
                val p1 = points[it.p1]!!
                val p2 = points[it.p2]!!
                ProjectedConnection(it, p1, p2)
            }
            drawConnections(connections)
        }
    }

    private fun drawPoints(points: List<ProjectedPoint>) {
        val canvas = surfaceHolder.lockCanvas()
        canvas.drawColor(Color.BLACK)
        val centerX = canvas.width / 2
        val centerY = canvas.height / 2
        for (point in points) {
            val x = point.x + centerX
            val y = point.y + centerY
            canvas.drawCircle(x, y, 10F, paint)
        }
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    private fun drawConnections(connections: List<ProjectedConnection>) {
        val canvas = surfaceHolder.lockCanvas()
        canvas.drawColor(Color.BLACK)
        val centerX = canvas.width / 2
        val centerY = canvas.height / 2
        for (connection in connections) {
            val p1 = connection.p1
            val p2 = connection.p2
            val p1x = p1.x + centerX
            val p1y = p1.y + centerY
            val p2x = p2.x + centerX
            val p2y = p2.y + centerY
            canvas.drawLine(p1x, p1y, p2x, p2y, paint)
        }
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        gestureListener.width = null
        gestureListener.height = null
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        gestureListener.width = width
        gestureListener.height = height
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        gestureListener.width = null
        gestureListener.height = null
    }
}