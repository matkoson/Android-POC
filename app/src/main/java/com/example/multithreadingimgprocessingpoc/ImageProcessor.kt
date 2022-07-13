import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Color.*
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Pixel(
    val r: Int,
    val g: Int,
    val b: Int,
    val alpha: Int,
)

class ImageProcessor(private val originalBitmap: Bitmap) {
    fun lighter(pixel: Int): Pixel {
        val r: Int = red(pixel) + 100;
        val g: Int = green(pixel) + 100;
        val b: Int = blue(pixel) + 100;
        val alpha: Int = alpha(pixel) + 100;

        return Pixel(r, g, b, alpha);
    }

    fun darker(pixel: Int): Pixel {
        val r: Int = red(pixel) - 100;
        val g: Int = green(pixel) - 100;
        val b: Int = blue(pixel) - 100;
        val alpha: Int = alpha(pixel) - 100;

        return Pixel(r, g, b, alpha);
    }

    fun processWithSingleThread(processingFun: (Int) -> Pixel): Bitmap {
        val bitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)

        for (i in 0 until originalBitmap.width) {
            Thread.sleep(50)
            for (j in 0 until originalBitmap.height) {
                val pixel = originalBitmap.getPixel(i, j)
                val lighterPx = processingFun(pixel);
                val (r, g, b, alpha) = lighterPx;

                bitmap.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }
        return bitmap;
    }

    suspend fun processWithMultiThread(processingFun: (Int) -> Pixel): Bitmap {
        Log.d("ImageProcessor", "processWithMultiThread")
        val bitmap = Bitmap.createBitmap(originalBitmap.width, originalBitmap.height, Bitmap.Config.ARGB_8888)

            for (i in 0 until originalBitmap.width) {
                Thread.sleep(50)
                for (j in 0 until originalBitmap.height) {
                    val pixel = originalBitmap.getPixel(i, j)
                    val lighterPx = processingFun(pixel);
                    val (r, g, b, alpha) = lighterPx;

                    bitmap.setPixel(i, j, Color.argb(alpha, r, g, b));
            }
        }
        return bitmap;
    }
}
