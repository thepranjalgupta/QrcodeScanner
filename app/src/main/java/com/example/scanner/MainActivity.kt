@file:OptIn(ExperimentalPermissionsApi::class)
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.scanner.MainScreen
import com.example.scanner.MainViewModel
import com.example.scanner.LandingScreen
import com.example.scanner.ui.theme.Navigation
import com.example.scanner.ui.theme.ScannerTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus.*

@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScannerTheme {
                    Scanner()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalPermissionsApi
@Composable
fun NavigationLogic() {
    val viewModel = viewModel<MainViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Navigation.LandingScreen.route,
    ) {
        composable(
            route = Navigation.LandingScreen.route
        ){
            LandingScreen(navController)
        }
        composable(
            route = Navigation.MainScreen.route
        ) {
                    MainScreen()
        }
    }
}
@ExperimentalPermissionsApi
@Preview
@Composable
fun Scanner(){
        NavigationLogic()

}
