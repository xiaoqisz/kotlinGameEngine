package org.itheima.kotlin.game.core

import java.io.BufferedInputStream
import java.io.File
import javax.sound.sampled.AudioSystem

object Composer {


    fun play(soundPath: String) {
        doPlay(soundPath, false)
    }

    fun playLoop(soundPath: String) {
        doPlay(soundPath, true)
    }

    private fun doPlay(soundPath: String, loop: Boolean) {
        val clip = AudioSystem.getClip()
        clip?.let {
            val resourceAsStream = javaClass.getResourceAsStream("/${soundPath}")
            val stream = AudioSystem.getAudioInputStream(BufferedInputStream(resourceAsStream))
            stream?.let {
                clip.open(stream)
                if (loop) clip.loop(-1)
                clip.start()
            }
        }
    }
}