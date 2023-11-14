package com.shape.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderPositions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.lerp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

/**
 * @description:
 * @author
 * @date :2023/11/9 下午9:18
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapeSlider(
    value: Float,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    /*@IntRange(from = 0)*/
    steps: Int = 0,
    colors: AutraSliderColors,
    onValueChange: (Float) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },

    tickHeight: Dp,
    trackOffset: Dp = 0.dp,
    tickSize:Dp = 2.dp,

    modifierShapeTrack: Modifier = Modifier,
    modifierShapeActive: Modifier = Modifier,
    modifierThumb: Modifier = Modifier,
) {
    Slider(
        modifier = modifier,
        value = value,
        enabled = enabled,
        onValueChange = onValueChange,
        valueRange = valueRange,
        onValueChangeFinished = {
            // launch some business logic update with the state you hold
            // viewModel.updateSelectedSliderValue(sliderPosition)
        },
        interactionSource = interactionSource,
        steps = steps,
        track = { sliderPositions ->
            SharpTrack(
                tickHeight = tickHeight,
                colors = colors,
                sliderPositions = sliderPositions,
                enabled = enabled,
                offset = trackOffset,
                tickSize = tickSize,
                modifierTrack = modifierShapeTrack,
                modifierActive = modifierShapeActive
            )
        },
        thumb = {
            ShapeThumb(
                interactionSource = interactionSource,
                modifierThumb = modifierThumb,
            )
        }
    )
}

@Composable
@ExperimentalMaterial3Api
fun SharpTrack(
    sliderPositions: SliderPositions,
    modifier: Modifier = Modifier,
    colors: AutraSliderColors,
    enabled: Boolean = true,
    modifierTrack: Modifier = Modifier,
    modifierActive: Modifier = Modifier,
    tickHeight: Dp,
    offset: Dp = 0.dp,
    tickSize: Dp = 2.dp,
) {
    val inactiveTickColor = colors.tickColor(enabled, active = false)
    val activeTickColor = colors.tickColor(enabled, active = true)

    val trackFullID = "TrackFull"
    val trackActiveID = "TrackActive"

    Layout(
        {
            Spacer(
                modifier = Modifier
                    .layoutId(trackFullID)
                    .fillMaxWidth()
                    .composed {
                        modifierTrack
                    }
            )

            Spacer(
                modifier = Modifier
                    .layoutId(trackActiveID)
                    .fillMaxWidth()
                    .composed {
                        modifierActive
                    }
            )
        },
        modifier = Modifier
    ) { measurables, constraints ->

        val fullPlaceable = measurables.first { it.layoutId == trackFullID }.measure(
            constraints.copy(
                minWidth = 0,
                maxWidth = constraints.maxWidth + offset.toPx().toInt(),
                minHeight = 0
            )
        )

        val maxTrackWidth = constraints.maxWidth
        val activePlaceable = measurables.first { it.layoutId == trackActiveID }.measure(
            constraints.copy(
                minWidth = 0,
                maxWidth = (maxTrackWidth * sliderPositions.positionFraction).toInt() + offset.toPx().toInt(),
                minHeight = 0
            )
        )

        val sliderWidth = max(fullPlaceable.width, activePlaceable.width)
        val sliderHeight = max(activePlaceable.height, fullPlaceable.height)

        val activeOffsetX = 0
        val activeOffsetY = (max(activePlaceable.height, fullPlaceable.height) - min(activePlaceable.height, fullPlaceable.height)) / 2

        val fullOffsetX = 0
        val fullOffsetY = (max(activePlaceable.height, fullPlaceable.height) - min(activePlaceable.height, fullPlaceable.height)) / 2

        layout(sliderWidth, sliderHeight) {

            fullPlaceable.placeRelative(fullOffsetX, fullOffsetY)
            activePlaceable.placeRelative(activeOffsetX, activeOffsetY)
        }
    }

    Canvas(
        modifier
            .fillMaxWidth()
            .height(tickHeight) ){
        val isRtl = layoutDirection == LayoutDirection.Rtl
        val sliderLeft = Offset(0f, center.y)
        val sliderRight = Offset(size.width, center.y)
        val sliderStart = if (isRtl) sliderRight else sliderLeft
        val sliderEnd = if (isRtl) sliderLeft else sliderRight

        sliderPositions.tickFractions.groupBy {
            it > sliderPositions.positionFraction || it < 0f
        }.forEach { (outsideFraction, list) ->
            drawPoints(
                list.map {
                    Offset(lerp(sliderStart, sliderEnd, it).x, center.y)
                },
                PointMode.Points,
                (if (outsideFraction) inactiveTickColor else activeTickColor).value,
                tickSize.toPx(),
                StrokeCap.Round
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun ShapeThumb(
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    modifierThumb: Modifier,
) {
    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }

    Spacer(
        modifier
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = 40.dp / 2
                )
            )
            .hoverable(interactionSource = interactionSource)
            .composed {
                modifierThumb
            }
    )
}


@Immutable
class AutraSliderColors(
    val thumbColor: Color,
    val activeTrackColor: Color,
    val activeTickColor: Color,
    val inactiveTrackColor: Color,
    val inactiveTickColor: Color,
    val disabledThumbColor: Color,
    val disabledActiveTrackColor: Color,
    val disabledActiveTickColor: Color,
    val disabledInactiveTrackColor: Color,
    val disabledInactiveTickColor: Color,
) {
    @Composable
    fun thumbColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) thumbColor else disabledThumbColor)
    }

    @Composable
    fun trackColor(enabled: Boolean, active: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (active) activeTrackColor else inactiveTrackColor
            } else {
                if (active) disabledActiveTrackColor else disabledInactiveTrackColor
            }
        )
    }

    @Composable
    fun tickColor(enabled: Boolean, active: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (active) activeTickColor else inactiveTickColor
            } else {
                if (active) disabledActiveTickColor else disabledInactiveTickColor
            }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is AutraSliderColors) return false

        if (thumbColor != other.thumbColor) return false
        if (activeTrackColor != other.activeTrackColor) return false
        if (activeTickColor != other.activeTickColor) return false
        if (inactiveTrackColor != other.inactiveTrackColor) return false
        if (inactiveTickColor != other.inactiveTickColor) return false
        if (disabledThumbColor != other.disabledThumbColor) return false
        if (disabledActiveTrackColor != other.disabledActiveTrackColor) return false
        if (disabledActiveTickColor != other.disabledActiveTickColor) return false
        if (disabledInactiveTrackColor != other.disabledInactiveTrackColor) return false
        if (disabledInactiveTickColor != other.disabledInactiveTickColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = thumbColor.hashCode()
        result = 31 * result + activeTrackColor.hashCode()
        result = 31 * result + activeTickColor.hashCode()
        result = 31 * result + inactiveTrackColor.hashCode()
        result = 31 * result + inactiveTickColor.hashCode()
        result = 31 * result + disabledThumbColor.hashCode()
        result = 31 * result + disabledActiveTrackColor.hashCode()
        result = 31 * result + disabledActiveTickColor.hashCode()
        result = 31 * result + disabledInactiveTrackColor.hashCode()
        result = 31 * result + disabledInactiveTickColor.hashCode()
        return result
    }
}

