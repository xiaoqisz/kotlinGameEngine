package org.itheima.kotlin.game.core

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import javafx.stage.Stage
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

abstract class Window(val title: String = "黑马程序员"
                      , val icon: String = "icon/logo.png"
                      , val width: Int = 800
                      , val height: Int = 600) : Application() {
    internal val canvas = MyCanvas(width, height)
    internal val looper: Looper = Looper(this)
    internal var fps = 0L

    private val keyPool = Executors.newSingleThreadExecutor()
    //    private val pool = Executors.newSingleThreadExecutor()
    private val pool = Executors.newScheduledThreadPool(1)
    private var running = false

    private val keyRecorderPool = Executors.newScheduledThreadPool(1)
    private val keyRecorder = mutableMapOf<KeyCode, KeyEvent>()
    private var currentKey: KeyCode? = null


    override fun start(primaryStage: Stage?) {
        val group = Group()
        group.children.add(canvas)

        val scene = Scene(group, width.toDouble(), height.toDouble())

        //为画笔设置源
        Painter.set(canvas.graphicsContext2D)

        primaryStage?.let {
            with(primaryStage) {
                this.title = this@Window.title
                this.scene = scene
                this.isResizable = false
                this.icons.add(Image(icon))

                this.setOnCloseRequest {
                    looper.stop()
                    keyPool.shutdownNow()
                    keyRecorderPool.shutdown()
                    running = false
                    pool.shutdownNow()
                }
                show()
            }
        }

        scene.onKeyPressed = EventHandler() { event ->
            keyPool.submit {
                Thread.currentThread().name = "hm-key"
                currentKey = event.code
                //记录
                keyRecorder.put(event.code, event)
                this@Window.onKeyPressed(event)
            }
        }

        scene.onKeyReleased = EventHandler() { event ->
            keyRecorder.remove(event.code)
            if (currentKey == event.code) {
                currentKey = null
            }
        }

        //初始化回调
        onCreate()

        looper.start()

        keyRecorderPool.scheduleWithFixedDelay({
            keyRecorder.filter { entry ->
                entry.key != currentKey
            }.forEach { t, u ->
                onKeyPressed(u)
            }
        }, 100, 120, TimeUnit.MILLISECONDS)

        // pool.scheduleWithFixedDelay({
        pool.scheduleAtFixedRate({
            this@Window.onRefresh()
        }, 100, 20, TimeUnit.MILLISECONDS)

    }


    fun getFps(): Long = fps

    abstract fun onCreate()

    abstract fun onDisplay()

    abstract fun onRefresh()

    abstract fun onKeyPressed(event: KeyEvent)
}

class Looper(private val window: Window) : AnimationTimer() {
    val width = window.width.toDouble()
    val height = window.height.toDouble()
    private var lastTime = 0L

    override fun handle(now: Long) {
        if (lastTime == 0L) {
            lastTime = now
        } else {
//        println(">>>>>>>>>>> ${(now - lastTime) / 1000 / 1000} <<<<<<<<<<<<<<")
//        window.sleep = (now - lastTime) / 1000 / 1000
            window.fps = 1000 * 1000 * 1000 / (now - lastTime)
        }
        lastTime = now

        val gc = window.canvas.graphicsContext2D
        //清屏
//        gc.clearRect(0.0, 0.0, width, height)

        gc.fill = Color.BLACK
        gc.fillRect(0.0, 0.0, width, height)

        window.onDisplay()

        System.gc()
    }

}

class MyCanvas(private val width: Int = 800
               , private val height: Int = 600) : Canvas() {

    init {
        setWidth(width.toDouble())
        setHeight(height.toDouble())
    }

}
