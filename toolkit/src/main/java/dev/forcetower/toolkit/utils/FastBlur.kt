package dev.forcetower.toolkit.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur


object FastBlur {
    fun blur(context: Context, inBitmap: Bitmap, radius: Float): Bitmap {
        val bitmap: Bitmap = inBitmap.copy(inBitmap.config, true)

        val rs = RenderScript.create(context)
        val input = Allocation.createFromBitmap(
            rs, inBitmap, Allocation.MipmapControl.MIPMAP_NONE,
            Allocation.USAGE_SCRIPT
        )
        val output = Allocation.createTyped(rs, input.type)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setRadius(radius)
        script.setInput(input)
        script.forEach(output)
        output.copyTo(bitmap)
        return bitmap
    }
}