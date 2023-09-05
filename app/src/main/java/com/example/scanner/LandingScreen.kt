package com.example.scanner
import androidx.compose.ui.unit.dp
import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.scanner.ui.theme.Navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@Composable
fun LandingScreen(navController: NavController) {
   PermissionScreen(navController)
}

@ExperimentalPermissionsApi
@Composable
fun PermissionScreen(navController: NavController) {
    val viewModel = viewModel<MainViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA,
                isGranted = isGranted,
        )}
    )
    Surface(
        modifier = Modifier.fillMaxSize(),

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(5.dp),)
            Image(painter = painterResource(R.drawable.lllllll), contentDescription ="")
            Text(
                text = "Need Permission to access camera",
                color = Color.White
            )
            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )
            OutlinedButton(onClick = {
                cameraPermissionResultLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
            ) {
                Text(
                    text = "Request Permission", color = Color.Black
                )
            }
            OutlinedButton(onClick = {
                navController.navigate(Navigation.MainScreen.route)
            }
            ) {
                Text(
                    text = "Continue", color = Color.Black
                )
            }

        }
    }
}