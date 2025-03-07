import androidx.compose.ui.window.ComposeUIViewController
import com.vadimpikha.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
