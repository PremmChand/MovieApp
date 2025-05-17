package com.example.movieapp

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.ui.theme.MovieAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.navigation.Navigation
import com.example.movieapp.viewModal.MovieViewModal
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    /* Source for this app
  MVVM Movie App in Android Jetpack Compose part#4 PAGINATION.

Mohsen Mashkour  Youtube channel*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                WindowCompat.setDecorFitsSystemWindows(window, false)
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
                val linearGradientBrush = Brush.linearGradient(
                    colors = listOf(
                        Color(0XFFB226E1),
                        Color(0XFFFC6603),
                        Color(0XFF5595EE),
                        Color(0XFF3D3535),
                    ),
                    start = Offset(0f, 0f),                  // Start at the top-left corner
                    end = Offset(1000f, 1000f)                // End at a finite point
                )
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    val movieViewModal = viewModel<MovieViewModal>()
                    val state = movieViewModal.state
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(linearGradientBrush)){
                        Navigation()
                    }

                }
            }
        }
    }
}
