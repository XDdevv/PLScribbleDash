package zed.rainxch.plscribbledash.core.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zed.rainxch.plscribbledash.data.repository.GameRepositoryImpl
import zed.rainxch.plscribbledash.data.repository.PaintRepositoryImpl
import zed.rainxch.plscribbledash.domain.repository.GameRepository
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

    @Binds
    @Singleton
    abstract fun bindGameRepository(
        gameRepositoryImpl: GameRepositoryImpl
    ): GameRepository

    companion object {
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }
    }
}