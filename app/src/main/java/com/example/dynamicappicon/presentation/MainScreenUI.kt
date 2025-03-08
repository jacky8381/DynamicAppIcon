package com.example.dynamicappicon.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.dynamicappicon.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainScreen(
    modifier : Modifier,
    onClick : (String) -> Unit
) {
    val statusBarColor = MaterialTheme.colorScheme.outlineVariant
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = useDarkIcons
        )
        onDispose { }
    }

    val listOfIcons = listOf(
        IconData(painterResource(id = R.drawable.batman),"Batman"),
        IconData(painterResource(id = R.drawable.captain),"CaptainAmerica"),
        IconData(painterResource(id = R.drawable.super2),"Superman"),

        )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.outline),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOfIcons.forEachIndexed { index, iconData ->
                Column(
                    modifier = Modifier ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .size(65.dp)
                            .clip(RoundedCornerShape(35.dp))
                            .clickable {
                                onClick.invoke(iconData.name)
                                Log.d("AppIcon on click",iconData.name )

                            },
                        painter = iconData.icon,
                        contentDescription = iconData.name,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = iconData.name,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier ,
                       // maxLines = 2,
                        //minLines = 2,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Clip
                    )
                }
            }
        }
    }
}

data class IconData(
    val icon : Painter,
    val name : String
)