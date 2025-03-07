package com.vadimpikha

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vadimpikha.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@AndroidApp)
        }
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

@Preview
@Composable
fun AppPreview() { App() }
