package zed.rainxch.plscribbledash.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import zed.rainxch.plscribbledash.data.datasource.StatisticsDataSource
import zed.rainxch.plscribbledash.data.local.db.AppDatabase
import zed.rainxch.plscribbledash.data.local.db.dao.StatisticsDao
import zed.rainxch.plscribbledash.data.repository.GameRepositoryImpl
import zed.rainxch.plscribbledash.data.repository.PaintRepositoryImpl
import zed.rainxch.plscribbledash.data.repository.StatisticsRepositoryImpl
import zed.rainxch.plscribbledash.domain.repository.GameRepository
import zed.rainxch.plscribbledash.domain.repository.PaintRepository
import zed.rainxch.plscribbledash.domain.repository.StatisticsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindPaintRepository(
        impl: PaintRepositoryImpl
    ): PaintRepository

    @Binds
    @Singleton
    abstract fun bindGameRepository(
        impl: GameRepositoryImpl
    ): GameRepository

    @Binds
    @Singleton
    abstract fun bindStatisticsRepository(
        impl: StatisticsRepositoryImpl
    ): StatisticsRepository

    companion object {
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        @Singleton
        fun provideDatabase(application: Application) : AppDatabase {
            return Room.databaseBuilder(
                context = application,
                klass = AppDatabase::class.java,
                name = "scribble_dash_db"
            ).build()
        }

        @Provides
        @Singleton
        fun provideStatisticsDao(appDatabase: AppDatabase): StatisticsDao {
            return appDatabase.statisticsDao()
        }

//        @Provides
//        @Singleton
//        fun provideStatisticsDataSource(statisticsDao: StatisticsDao): StatisticsDataSource {
//            return StatisticsDataSource(statisticsDao)
//        }
    }
}