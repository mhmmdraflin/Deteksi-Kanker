package com.dicoding.asclepius.helper

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.camera.core.ImageProxy
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.CastOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier


class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 3,
    val modelName: String = "cancer_classification.tflite",
    val context: Context,
    private val listener: ClassifierListener? = null
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }


    private fun setupImageClassifier() {
        // TODO: Menyiapkan Image Classifier untuk memproses gambar.
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)
        val baseOptionsBuilder = BaseOptions.builder()
            .setNumThreads(4)
        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier = ImageClassifier.createFromFileAndOptions(
                context,
                modelName,
                optionsBuilder.build()
            )
        } catch (e: IllegalStateException) {
            listener?.onError("Failed to load model: ${e.message}")
        }
    }

    fun classifyStaticImage(image: Bitmap): Pair<String, Float> {
        if (imageClassifier == null) {
            setupImageClassifier()
        }
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
            .add(CastOp(DataType.UINT8))
            .build()

        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(image))

        try {
            val results = imageClassifier?.classify(tensorImage)
            val resultString = results?.get(0)?.categories?.get(0)?.label ?: "Unknown"
            val confidenceScore = results?.get(0)?.categories?.get(0)?.score ?: 0f

            listener?.onResults(results, confidenceScore)
            return Pair(resultString, confidenceScore)
        } catch (e: Exception) {
            listener?.onError("Error during classification: ${e.message}")
            return Pair("Error", 0f)
        }
    }

    private fun toBitmap(image: ImageProxy): Bitmap {
        val bitmapBuffer = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        image.close()
        return bitmapBuffer
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
            confidenceScore: Float
        )
    }

    companion object {
        private const val TAG = "ImageClassifierHelper"
    }
}
