import androidx.compose.ui.window.ComposeUIViewController
import com.vadimpikha.App
import com.vadimpikha.di.initKoin
import platform.UIKit.UIViewController

@Suppress("FunctionName", "unused")
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}
