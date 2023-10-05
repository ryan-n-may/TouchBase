package com.example.touchbase.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.touchbase.backend.*
import com.example.touchbase.ui.components.*
import com.example.touchbase.viewmodel.TouchBaseViewModel
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun NewCameraScreen(
    navController: NavHostController,
    viewmodel: TouchBaseViewModel
) {
    val visibility = remember{ mutableStateOf(0.0f) }
    // Variables specific to operation of the camera
    val cameraDirection = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()
    val previewView = remember{ PreviewView(context) }
    val imageCapture : ImageCapture = remember{ ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(cameraDirection).build()

    LaunchedEffect(cameraDirection){
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Scaffold(
        topBar = { },
        floatingActionButton = {
            Row{
                ToggledBackButton(navController, visibility)
                FloatingActionButton(
                    modifier = Modifier.padding(5.dp),
                    onClick = {
                        Log.d(TAG,"Taking a photo")
                        takePhotograph(
                            imageCapture = imageCapture,
                            executor = viewmodel.cameraExecute,
                            onImageCaptured = viewmodel::handleImageCapture,
                            onError = viewmodel::handleImageError,
                        )
                        if(visibility.value == 0.0f){
                            visibility.value = 1.0f
                        } else {
                            visibility.value = 0.0f
                        }
                }) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Take photo")
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            // Where we handle taking the photo in the GUI
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        }
    }
}

private fun takePhotograph(
    imageCapture: ImageCapture,
    onImageCaptured: (Bitmap) -> Unit,
    onError: (Exception) -> Unit,
    executor: Executor,
){
    imageCapture.takePicture(
        executor,
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onError(exception: ImageCaptureException) {
                Log.e("ImageCapture", "Photo error: ", exception)
                onError(exception)
            }
            override fun onCaptureSuccess(image : ImageProxy) {
                // get bitmap
                Log.e("ImageCapture", "Photo captured")
                val bitmap = imageProxyToBitmap(image)
                onImageCaptured(bitmap)
                super.onCaptureSuccess(image)
            }
        }
    )
}

private fun imageProxyToBitmap(image : ImageProxy) : Bitmap {
    val planeProxy = image.planes[0]
    val buffer : ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return rotateImage(BitmapFactory.decodeByteArray(bytes, 0, bytes.size), 90f)!!
}
fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(angle)
    return Bitmap.createBitmap(
        source, 0, 0, source.width, source.height,
        matrix, true
    )
}
private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}
