package com.kms.Animation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kms.Animation.catchs.CatchRoute
import com.kms.Animation.flipcard.CardFlipRoute
import com.kms.Animation.ui.theme.AnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationTheme {
                CatchRoute()
            }
        }
    }
}
