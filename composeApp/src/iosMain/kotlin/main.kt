import androidx.compose.ui.window.ComposeUIViewController
import coil3.SingletonImageLoader
import com.vadimpikha.App
import com.vadimpikha.di.initKoin
import com.vadimpikha.presentation.coil.createImageLoader
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.UIKit.UIViewController

@Suppress("FunctionName", "unused")
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { initComponents() }
) {
    App()
}

private fun initComponents() {
    initKoin()
    Napier.base(DebugAntilog())
    SingletonImageLoader.setSafe { createImageLoader() }
}
