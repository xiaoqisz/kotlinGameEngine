package org.itheima.kotlin.game.core

import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.LineListener

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

                clip.addLineListener(LineListener {event ->
                    if (event.framePosition.toInt() == clip.frameLength) {
                        clip.close()
                    }
                })

                clip.open(stream)
                if (loop) clip.loop(-1)

                clip.start()

            }
        }
    }
}