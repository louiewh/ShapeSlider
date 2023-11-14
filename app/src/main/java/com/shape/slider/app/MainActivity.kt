package com.shape.slider.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.shape.slider.ShapeSlider
import com.shape.slider.AutraSliderColors
import com.shape.slider.ui.theme.ShapeSliderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShapeSliderTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                ) {
                    SliderPreview("Shape Slider")
                }
            }
        }
    }
}


@Preview
@Composable
fun GreetingPreview() {
    SliderPreview("Slider Shape")
}

@Composable
fun SliderPreview(name:String) {

    Column {
        Text(text = name)

        var sliderPosition by remember { mutableStateOf(0.2f) }
        val colors  by remember {
            mutableStateOf(
                AutraSliderColors(
                    thumbColor = Color(0xFF3481F5),
                    activeTrackColor = Color.White,
                    activeTickColor = Color.Red,
                    inactiveTrackColor = Color(0xFFDCE0E9),
                    inactiveTickColor = Color.Red,

                    disabledThumbColor = Color.Cyan,
                    disabledActiveTrackColor = Color.LightGray,
                    disabledActiveTickColor = Color.Gray,
                    disabledInactiveTrackColor = Color.Cyan,
                    disabledInactiveTickColor = Color.Red
                )
            )
        }

        Text(text = sliderPosition.toString())
        ShapeSlider(
            sliderPosition,
            Modifier,
            enabled = true,
            colors = colors,
            onValueChange = { sliderPosition = it
                Log.d("AutraSlider", "AutraSliderPreview onValueChange $it")
            },
            tickHeight = 82.dp,
            trackOffset = 10.dp,
            tickSize = 5.dp,
            modifierShapeTrack = Modifier
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x291E293B),
                    ambientColor = Color(0x291E293B)
                )
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x0A1E293B),
                    ambientColor = Color(0x0A1E293B)
                )
                .height(82.dp)
                .background(
                    color = colors.trackColor(true, false).value,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            modifierShapeActive = Modifier
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .height(82.dp)
                .background(
                    color = colors.trackColor(true, true).value,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
            modifierThumb = Modifier
                .size(DpSize(8.dp, 50.dp))
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .width(8.dp)
                .height(50.dp)
                .background(
                    color = colors.thumbColor(true).value,
                    shape = RoundedCornerShape(size = 8.dp)
                )
        )

        var sliderPosition1 by remember { mutableStateOf(40f) }
        Text(text = sliderPosition1.toString())
        ShapeSlider(
            sliderPosition1,
            valueRange = 0f..100f,
            steps = 9,
            modifier = Modifier.semantics {  },
            colors = colors,
            onValueChange = { sliderPosition1 = it
                Log.d("AutraSlider","AutraSliderPreview onValueChange $it")
            },
            tickHeight = 22.dp,
            trackOffset = 10.dp,
            tickSize = 5.dp,
            modifierShapeTrack = Modifier
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x291E293B),
                    ambientColor = Color(0x291E293B)
                )
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x0A1E293B),
                    ambientColor = Color(0x0A1E293B)
                )
                .height(22.dp)
                .background(color = Color(0xFFDCE0E9), shape = RoundedCornerShape(size = 12.dp)),
            modifierShapeActive = Modifier
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .height(22.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp)),
            modifierThumb = Modifier
                .size(DpSize(8.dp, 30.dp))
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .width(8.dp)
                .height(30.dp)
                .background(color = Color(0xFF3481F5), shape = RoundedCornerShape(size = 8.dp))
        )

        var sliderPosition11 by remember { mutableStateOf(60f) }
        Text(text = sliderPosition11.toString())
        ShapeSlider(
            sliderPosition11,
            valueRange = 0f..100f,
            steps = 9,
            modifier = Modifier.semantics {  },
            colors = colors,
            onValueChange = { sliderPosition11 = it
                Log.d("AutraSlider","AutraSliderPreview onValueChange $it")
            },
            tickHeight = 32.dp,
            trackOffset = 10.dp,
            tickSize = 10.dp,
            modifierShapeTrack = Modifier
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x291E293B),
                    ambientColor = Color(0x291E293B)
                )
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x0A1E293B),
                    ambientColor = Color(0x0A1E293B)
                )
                .height(22.dp)
                .background(color = Color(0xFFDCE0E9), shape = RoundedCornerShape(size = 12.dp)),
            modifierShapeActive = Modifier
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .height(22.dp)
                .background(color = Color(0xFFFFFFFF), shape = RoundedCornerShape(size = 8.dp)),
            modifierThumb = Modifier
                .size(DpSize(8.dp, 30.dp))
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .width(8.dp)
                .height(30.dp)
                .background(color = Color(0xFF3481F5), shape = RoundedCornerShape(size = 8.dp))
        )

        var sliderPosition13 by remember { mutableStateOf(0.8f) }
        val enabled by remember { mutableStateOf(false) }
        Text(text = sliderPosition13.toString())
        ShapeSlider(
            sliderPosition13,
            Modifier,
            enabled = enabled,
            colors = colors,
            onValueChange = { sliderPosition13 = it
                Log.d("AutraSlider", "AutraSliderPreview onValueChange $it")
            },
            tickHeight = 22.dp,
            trackOffset = 10.dp,
            tickSize = 5.dp,
            modifierShapeTrack = Modifier
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x291E293B),
                    ambientColor = Color(0x291E293B)
                )
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x0A1E293B),
                    ambientColor = Color(0x0A1E293B)
                )
                .height(20.dp)
                .background(
                    color = colors.trackColor(enabled, false).value,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            modifierShapeActive = Modifier
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .height(20.dp)
                .background(
                    color = colors.trackColor(enabled, true).value,
                    shape = RoundedCornerShape(size = 8.dp)
                ),
            modifierThumb = Modifier
                .size(DpSize(8.dp, 50.dp))
                .shadow(
                    elevation = 2.dp,
                    spotColor = Color(0x1F1E293B),
                    ambientColor = Color(0x1F1E293B)
                )
                .width(8.dp)
                .height(20.dp)
                .background(
                    color = colors.thumbColor(enabled).value,
                    shape = RoundedCornerShape(size = 8.dp)
                )
        )


        ComposeSliderPreview()

        DrawLinePreview()
    }
}

@Composable
private fun ComposeSliderPreview() {
    Text(text = "Compose Slider", modifier = Modifier.padding(top = 30.dp))
    var sliderPosition2 by remember { mutableStateOf(0.3f) }
    Text(text = sliderPosition2.toString(), modifier = Modifier.padding(top = 30.dp))
    Slider(value = sliderPosition2,
        onValueChange = { sliderPosition2 = it }
    )

    val sliderValue = remember { mutableStateOf(40f) }
    Slider(
        value = sliderValue.value,
        enabled = true,
        onValueChange = { newValue ->
            sliderValue.value = newValue
        },
        valueRange = 0f..100f,
        steps = 9,
    )
}

@Composable
private fun DrawLinePreview() {
    Text(text = "drawLine", modifier = Modifier.padding(top = 30.dp))
    Canvas(
        Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(20.dp)) {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f, center.y)
        val sliderRight = Offset(size.width, center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight
        val trackStrokeWidth = 20.dp.toPx()
        drawLine(
            Color.Magenta,
            sliderStart,
            sliderEnd,
            trackStrokeWidth,
            StrokeCap.Round
        )
    }


    Canvas(
        Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(20.dp)) {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f, center.y)
        val sliderRight = Offset(size.width, center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight
        val trackStrokeWidth = 20.dp.toPx()

        drawLine(
            Color.Magenta,
            sliderStart,
            sliderEnd,
            trackStrokeWidth,
            StrokeCap.Butt
        )
    }


    Canvas(
        Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .height(20.dp)) {
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f, center.y)
        val sliderRight = Offset(size.width, center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight
        val trackStrokeWidth = 20.dp.toPx()

        drawLine(
            Color.Magenta,
            sliderStart,
            sliderEnd,
            trackStrokeWidth,
            StrokeCap.Square
        )
    }
}