package com.example.recipeapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class RectangleMinusSemicircleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadius = with(density) { -80.dp.toPx() }
        val rectHeight = size.height/2

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, rectHeight)

            arcTo(
                rect = Rect(
                    left = 0f,
                    top = size.height - rectHeight - cornerRadius, // Изменяем координату top
                    right = size.width,
                    bottom = size.height - cornerRadius // Изменяем координату bottom
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            lineTo(0f, rectHeight)
            close()
        }

        return Outline.Generic(path)
    }
}

@Composable
fun RectangleMinusSemicirclePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary, shape = RectangleMinusSemicircleShape())
        )
    }
}

@Composable
@Preview
fun RectangleMinusSemicircleShapePreview() {
    RectangleMinusSemicirclePreview()
}