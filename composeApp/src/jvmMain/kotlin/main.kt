import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import coil3.SingletonImageLoader
import java.awt.Dimension
import com.vadimpikha.App
import com.vadimpikha.di.initKoin
import com.vadimpikha.presentation.coil.createImageLoader
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun main() {
    System.setProperty("apple.awt.application.appearance", "system")
    initComponents()

    application {
        Window(
            title = "CryptoCharts",
            state = rememberWindowState(
                position = WindowPosition.Aligned(Alignment.Center),
                width = 800.dp,
                height = 600.dp
            ),
            onCloseRequest = ::exitApplication,
        ) {
            window.minimumSize = Dimension(350, 600)
            App()
        }
    }
}

private fun initComponents() {
    Napier.base(DebugAntilog())
    initKoin()
    SingletonImageLoader.setSafe { createImageLoader() }
}

@Preview
@Composable
fun AppPreview() {
    App()
}