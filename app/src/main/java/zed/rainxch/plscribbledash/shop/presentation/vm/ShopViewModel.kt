package zed.rainxch.plscribbledash.shop.presentation.vm

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import zed.rainxch.plscribbledash.core.data.datasource.ShopCanvasDataSource
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val dataSource: ShopCanvasDataSource
) : ViewModel() {
    fun getCanvasList() = dataSource.getCanvasList()

    fun getPenList() = dataSource.getCanvasList()
}