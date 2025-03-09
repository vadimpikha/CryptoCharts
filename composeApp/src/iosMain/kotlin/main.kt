import androidx.compose.ui.window.ComposeUIViewController
import com.vadimpikha.App
import com.vadimpikha.di.initKoin
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
}
