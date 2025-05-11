package zed.rainxch.plscribbledash.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun DisplayLargeText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.displayLarge,
        fontSize = 66.sp
    )
}

@Composable
fun DisplayMediumText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center,
        fontSize = 40.sp
    )
}

@Composable
fun HeadlineLargeText(
    text: String,
    color: Color,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 34.sp
    )
}

@Composable
fun HeadlineMediumText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 26.sp
    )
}

@Composable
fun HeadlineSmallText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineSmall,
        fontSize = 18.sp
    )
}

@Composable
fun HeadlineXSmallText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = textColor,
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
        fontSize = 14.sp,
    )
}

@Composable
fun BodyLargeText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
    )
}

@Composable
fun BodyMediumText(
    text: String,
    textColor: Color,
    align: TextAlign,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 16.sp,
        textAlign = align
    )
}

@Composable
fun BodySmallText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.bodySmall,
        fontSize = 14.sp,
    )
}


@Composable
fun LabelXLargeText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
    )
}

@Composable
fun LabelLargeText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    )
}

@Composable
fun LabelMediumText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
        color = textColor,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    )
}

@Composable
fun LabelSmallText(
    text: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    )
}

