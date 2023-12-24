package com.example.cube.view.old

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.*
import com.example.cube.`object`.Object
import com.example.cube.`object`.structs.Point
import com.example.cube.view.*
import com.example.cube.view.gesture.ScaleGestureListener
import com.example.cube.view.gesture.SimpleGestureListener
import com.example.cube.view.old.structs.ProjectedConnection
import com.example.cube.view.old.structs.ProjectedPoint
import com.example.cube.view.old.structs.ProjectedWall
import com.example.cube.view.old.structs.Scene
import kotlinx.coroutines.*


class ObjectView: SurfaceView, SurfaceHolder.Callback {

    var currentObject: Object? = null
    val scene = Scene()

    private val scaleListener = ScaleGestureListener(scene)
    private val gestureListener = SimpleGestureListener(scene)

    private val scaleDetector = ScaleGestureDetector(context, scaleListener)
    private val gestureDetector = GestureDetector(context, gestureListener)

    private val paintLine = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 1F
    }

    private val paintWall = Paint().apply {
        style = Paint.Style.FILL
    }

    private val wallColors = listOf(
            Color.BLUE,
            Color.rgb(255, 165, 0),
            Color.WHITE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW
    )

    private var running: Boolean = false
    private var viewJob: Job? = null

    private val surfaceHolder by lazy {
        val holder = holder
        holder.addCallback(this)
        holder
    }


    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
            context,
            attrs,
            defStyle
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
                    scene.moveAngle()
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
            val connections = connections.map {
                val p1 = points[it.p1]!!
                val p2 = points[it.p2]!!
                ProjectedConnection(it, p1, p2)
            }
            val walls = walls.mapIndexed { i, wall ->
                val wallPoints = wall.points.map {
                    points[it]!!
                }
                val color = wallColors[i % wallColors.size]
                ProjectedWall(wall, color, wallPoints)
            }.sortedBy { wall ->
                wall.points.minOf {
                    it.z
                }
            }
            draw(points.values.toList(), connections, walls)
        }
    }

    private fun draw(points: List<ProjectedPoint>, connections: List<ProjectedConnection>, walls: List<ProjectedWall>) {
        val canvas = surfaceHolder.lockCanvas()
        canvas.drawColor(Color.BLACK)
        //drawPoints(canvas, points)
        //drawConnections(canvas, connections)
        drawWalls(canvas, walls)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    private fun drawPoints(canvas: Canvas, points: List<ProjectedPoint>) {
        val centerX = canvas.width / 2
        val centerY = canvas.height / 2
        for (point in points) {
            val x = point.x + centerX
            val y = point.y + centerY
            canvas.drawCircle(x, y, 10F, paintLine)
        }
    }

    private fun drawConnections(canvas: Canvas, connections: List<ProjectedConnection>) {
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
            canvas.drawLine(p1x, p1y, p2x, p2y, paintLine)
        }
    }

    private fun drawWalls(canvas: Canvas, walls: List<ProjectedWall>) {
        val centerX = canvas.width / 2
        val centerY = canvas.height / 2
        val path = Path()
        walls.forEachIndexed { i, projectedWall ->
            path.reset()
            projectedWall.points.forEachIndexed { i, point ->
                val x = point.x + centerX
                val y = point.y + centerY
                if (i == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }
            paintWall.color = projectedWall.colors
            canvas.drawPath(path, paintWall)
            canvas.drawPath(path, paintLine)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        gestureListener.width = null
        gestureListener.height = null
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        gestureListener.width = width
        gestureListener.height = height
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gestureListener.width = null
        gestureListener.height = null
    }
}