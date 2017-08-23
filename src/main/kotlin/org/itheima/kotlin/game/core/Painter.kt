package org.itheima.kotlin.game.core

import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image
import javafx.scene.paint.Color

object Painter {
    private lateinit var gc: GraphicsContext
    //图片缓存
    private val imageCache = mutableMapOf<String, Image>()

    internal fun set(graphicsContext: GraphicsContext) {
        gc = graphicsContext
    }

    /**
     * 获取图片大小
     */
    fun size(imagePath: String): Array<Int> {
        val cache = imageCache[imagePath]
        if (cache != null) arrayOf(cache.width.toInt(), cache.height.toInt())

        val image = Image(imagePath)
        imageCache.put(imagePath, image)
        return arrayOf(image.width.toInt(), image.height.toInt())
    }

    /**
     * 绘制图片
     */
    fun drawImage(imagePath: String, x: Int, y: Int) {
        val cache = imageCache[imagePath]
        if (cache != null) gc.drawImage(cache, x.toDouble(), y.toDouble())

        val image = Image(imagePath)
        imageCache.put(imagePath, image)
        gc.drawImage(image, x.toDouble(), y.toDouble())
    }

    /**
     * 绘制颜色
     */
    fun drawColor(color: Color, x: Int, y: Int, width: Int, height: Int) {
        gc.fill = color
        gc.fillRect(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())
        gc.fill = Color.BLACK
    }

    /**
     * 绘制文本
     */
    fun drawText(text: String, x: Int, y: Int) {
        gc.fillText(text, x.toDouble(), y.toDouble())
    }

}