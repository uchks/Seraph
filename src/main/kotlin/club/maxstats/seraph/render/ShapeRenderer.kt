package club.maxstats.seraph.render

import club.maxstats.seraph.event.ResizeWindowEvent
import club.maxstats.seraph.render.shader.BlurProgram
import club.maxstats.seraph.render.shader.RoundedRectProgram
import club.maxstats.seraph.util.mc
import net.weavemc.loader.api.event.SubscribeEvent
import org.lwjgl.opengl.GL11.*

val roundedRectProgram = RoundedRectProgram()
val blurProgram = BlurProgram()

/* Specifically meant for event listening */
class ShapeRenderer() {
    @SubscribeEvent
    fun onResize(event: ResizeWindowEvent) {
        blurProgram.blurBuffer.createBindFramebuffer(mc.displayWidth, mc.displayHeight)
    }
}
fun drawRoundedRect(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    topLeftRadius: Float = 4f,
    topRightRadius: Float = 4f,
    bottomLeftRadius: Float = 4f,
    bottomRightRadius: Float = 4f,
    color: Color = Color.white
) {
    roundedRectProgram.render(x, y, width, height, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius, color)
}
fun drawRoundedRect(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: Float = 4f,
    color: Color = Color.white
) {
    drawRoundedRect(x, y, width, height, radius, radius, radius, radius, color)
}

fun drawBlurredRect(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    topLeftRadius: Float = 4f,
    topRightRadius: Float = 4f,
    bottomLeftRadius: Float = 4f,
    bottomRightRadius: Float = 4f,
    blurRadius: Float = 18f
) {
    blurProgram.render(x, y, width, height, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius, blurRadius)
}
fun drawBlurredRect(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    rectRadius: Float = 4f,
    blurRadius: Float = 18f
) {
    drawBlurredRect(x, y, width, height, rectRadius, rectRadius, rectRadius, rectRadius, blurRadius)
}

fun drawQuad(
    x: Float,
    y: Float,
    x1: Float,
    y1: Float
) {
    glBegin(GL_QUADS)
    glVertex2f(x, y)
    glVertex2f(x, y1)
    glVertex2f(x1, y1)
    glVertex2f(x1, y)
    glEnd()
}

fun scissor(x: Float, y: Float, x1: Float, y1: Float, scaleFactor: Int, displayHeight: Int) {
    glScissor(
        (x * scaleFactor).toInt(),
        (displayHeight - (y * scaleFactor)).toInt(),
        ((x1 - x) * scaleFactor).toInt(),
        ((y1 - y) * scaleFactor).toInt()
    );
}