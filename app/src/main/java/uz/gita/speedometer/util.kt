package uz.gita.speedometer

import kotlin.math.PI

val Float.toRadian: Float get() = (this * PI / 180f).toFloat()