package zed.rainxch.plscribbledash.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zed.rainxch.plscribbledash.data.repository.PaintRepositoryImpl
import zed.rainxch.plscribbledash.domain.repository.PaintRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindPaintRepository(
        paintRepositoryImpl: PaintRepositoryImpl
    ): PaintRepository
}