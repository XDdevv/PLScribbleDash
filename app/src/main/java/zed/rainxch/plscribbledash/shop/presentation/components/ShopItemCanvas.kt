package zed.rainxch.plscribbledash.shop.presentation.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import zed.rainxch.plscribbledash.R
import zed.rainxch.plscribbledash.core.domain.model.ShopCanvas
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.HeadlineSmallText
import zed.rainxch.plscribbledash.core.presentation.desingsystem.components.LabelSmallText

@Composable
fun ShopItemCanvas(
    onItemClicked: () -> Unit,
    shopCanvas: ShopCanvas,
    modifier: Modifier = Modifier,
    canAfford: Boolean
) {
    val equippedBorderColor = Color(0xff0DD280)
    val basicContainerColor = Color(0xffFFFFFF)
    val premiumContainerColor = Color(0xffAB5CFA)
    val legendaryContainerColor = Color(0xffFA852C)
    val alpha = if (canAfford) Modifier.alpha(.4f) else Modifier

    when (val state = shopCanvas) {
        is ShopCanvas.Basic -> {
            val isBought = state.canvasBought
            val isEquipped = state.canvasEquipped
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
                        .background(basicContainerColor)
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
                            .height(60.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(state.color)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.surfaceContainerHigh,
                                RoundedCornerShape(14.dp)
                            )
                            .padding(16.dp)
                    ) {
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
                                text = "${state.canvasPrice}",
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

        is ShopCanvas.Premium -> {
            val isBought = state.canvasBought
            val isEquipped = state.canvasEquipped
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
                            .height(60.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(state.color)
                            .padding(16.dp),
                    ) {
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
                                text = "${state.canvasPrice}",
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

        is ShopCanvas.Legendary -> {
            val isBought = state.canvasBought
            val isEquipped = state.canvasEquipped

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
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(state.imageRes),
                            contentDescription = "Image of canvas",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
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
                                text = "${state.canvasPrice}",
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