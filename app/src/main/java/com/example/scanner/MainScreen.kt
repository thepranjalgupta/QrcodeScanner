package com.example.scanner

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import java.lang.Exception

@ExperimentalMaterial3Api
@ExperimentalPermissionsApi
@Composable
fun MainScreen() {
        Column(
            verticalArrangement = Arrangement.Top,
        ) {
            val sheetState = rememberModalBottomSheetState()
            var isSheetOpen by rememberSaveable {
                mutableStateOf(false)
            }
            var code by remember { mutableStateOf("null") }
            val context = LocalContext.current
            val cameraProviderFuture = remember {
                ProcessCameraProvider.getInstance(context)
            }
            val lifecycleOwner = LocalLifecycleOwner.current
            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = AbsoluteRoundedCornerShape(1f)),
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = androidx.camera.core.Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setTargetResolution(
                            Size(
                                previewView.height,
                                previewView.width
                            )
                        )
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(context),
                        QrCodeAnalyzer { result ->
                            code = result
                        }
                    )
                    try {
                        cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            selector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    previewView
                }

            )
            if (code != "null") {
                //if(isSheetOpen){
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Blue
                ) {
                    ModalBottomSheet(
                        sheetState = sheetState,
                        onDismissRequest = { sheetState},
                    ) {
                        Text(
                            text = code,
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                    }
                    //}
                }

            }
        }
}


