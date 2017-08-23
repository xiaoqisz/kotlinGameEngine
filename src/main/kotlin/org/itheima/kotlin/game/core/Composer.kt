package org.itheima.kotlin.game.core

import java.io.File
import javax.sound.sampled.AudioSystem

object Composer {


    fun play(soundPath: String) {
        val clip = AudioSystem.getClip()
        clip?.let {
            val file = File(javaClass.getResource("/${soundPath}").path)
            val stream = AudioSystem.getAudioInputStream(file)
            stream?.let {
                clip.open(stream)
                clip.start()
            }
        }
    }

    fun playLoop(soundPath: String) {
        val clip = AudioSystem.getClip()
        clip?.let {
            val file = File(javaClass.getResource("/${soundPath}").path)
            val stream = AudioSystem.getAudioInputStream(file)
            stream?.let {
                clip.open(stream)
                clip.loop(-1)
                clip.start()
            }
        }
    }
}