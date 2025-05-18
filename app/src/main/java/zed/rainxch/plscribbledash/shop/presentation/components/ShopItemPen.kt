package zed.rainxch.plscribbledash.shop.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.domain.model.ShopPen
import zed.rainxch.plscribbledash.core.presentation.components.HeadlineSmallText
import zed.rainxch.plscribbledash.core.presentation.components.LabelSmallText

@Composable
fun ShopItemPen(
    shopPen: ShopPen,
    onItemClicked: () -> Unit,
    cantAfford : Boolean,
    modifier: Modifier = Modifier
) {
    val equippedBorderColor = Color(0xff0DD280)
    val legendaryContainerColor = Color(0xffFA852C)
    val basicContainerColor = Color(0xffFFFFFF)
    val premiumContainerColor = Color(0xffAB5CFA)

    val alpha = if(cantAfford) Modifier.alpha(.4f) else Modifier

    when (val state = shopPen) {
        is ShopPen.Basic -> {
            val isBought = state.penBought
            val isEquipped = state.penEquipped
            Box(
                modifier = Modifier.then(alpha)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .then(
                            if (isEquipped) Modifier.border(
                                2.dp,
                                equippedBorderColor,
                                RoundedCornerShape(16.dp)
                            ) else Modifier
                        )
                        .background(Color.White)
                        .clickable {
                            onItemClicked()
                        }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LabelSmallText(
                        text = state.type.uppercase(),
                        textColor = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(basicContainerColor)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.surfaceContainerHigh,
                                RoundedCornerShape(14.dp)
                            )
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_template_pen),
                            contentDescription = stringResource(R.string.template_pen),
                            tint = state.color
                        )
                        if (!isBought) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = stringResource(R.string.lock),
                                modifier = Modifier.align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    if (!isBought) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_coin),
                                contentDescription = "Coin",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            HeadlineSmallText(
                                text = "${state.penPrice}",
                                textColor = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = stringResource(R.string.cart_icon),
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }

                }

                if (isEquipped) {
                    Image(
                        painter = painterResource(R.drawable.ic_check_on),
                        contentDescription = "Pen is bought",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    )
                }
            }
        }

        is ShopPen.Premium -> {
            val isBought = state.penBought
            val isEquipped = state.penEquipped
            Box(
                modifier = Modifier.then(alpha)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .then(
                            if (isEquipped) Modifier.border(
                                2.dp,
                                equippedBorderColor,
                                RoundedCornerShape(16.dp)
                            ) else Modifier
                        )
                        .background(premiumContainerColor)
                        .clickable {
                            onItemClicked()
                        }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LabelSmallText(
                        text = state.type.uppercase(),
                        textColor = Color.White
                    )

                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_template_pen),
                            contentDescription = stringResource(R.string.template_pen),
                            tint = state.color
                        )
                        if (!isBought) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = stringResource(R.string.lock),
                                modifier = Modifier.align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    if (!isBought) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_coin),
                                contentDescription = "Coin",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            HeadlineSmallText(
                                text = "${state.penPrice}",
                                textColor = Color.White
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = stringResource(R.string.cart_icon),
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }

                }

                if (isEquipped) {
                    Image(
                        painter = painterResource(R.drawable.ic_check_on),
                        contentDescription = "Pen is bought",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    )
                }
            }
        }

        is ShopPen.Legendary -> {
            val isBought = state.penBought
            val isEquipped = state.penEquipped
            val path = remember {
                PathParser().parsePathString(
                    "M2.091,20.362C1.901,17.943 1.995,16.535 2.506,14.162C3.403,10.001 5.408,7.252 7.431,4.44C8.489,2.971 12.007,0.383 12.878,3.393C14.702,9.698 14.77,15.988 14.77,24.113C14.77,26.49 14.512,31.283 17.445,29.678C21.305,27.565 23.644,13.157 28.893,17.108C31.653,19.186 35.557,22.886 38.841,23.753C43.414,24.961 45.896,23.927 50.606,23C56.579,21.825 60.336,20.099 66,17.867"
                )
            }.toPath()
            Box (
                modifier = Modifier.then(alpha)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .then(
                            if (isEquipped) Modifier.border(
                                2.dp,
                                equippedBorderColor,
                                RoundedCornerShape(16.dp)
                            ) else Modifier
                        )
                        .background(legendaryContainerColor)
                        .clickable {
                            onItemClicked()
                        }
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LabelSmallText(
                        text = state.type.uppercase(),
                        textColor = Color.White
                    )

                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color.White)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            withTransform({
                                scale(3f, 3f)
                                translate(size.width / 3f, size.height / 3f)
                            }
                            ) {
                                drawPath(
                                    path = path,
                                    brush = state.brush(),
                                    style = Stroke(width = 2f, cap = StrokeCap.Round)
                                )
                            }
                        }

                        if (!isBought) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = stringResource(R.string.lock),
                                modifier = Modifier.align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    if (!isBought) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_coin),
                                contentDescription = "Coin",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            HeadlineSmallText(
                                text = "${state.penPrice}",
                                textColor = Color.White
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_cart),
                            contentDescription = stringResource(R.string.cart_icon),
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }

                }

                if (isEquipped) {
                    Image(
                        painter = painterResource(R.drawable.ic_check_on),
                        contentDescription = "Pen is bought",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    )
                }
            }
        }
    }

}