package zed.rainxch.plscribbledash.core.domain.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import zed.rainxch.plscribbledash.core.data.db.AppDatabase
import zed.rainxch.plscribbledash.core.data.db.dao.StatisticsDao
import zed.rainxch.plscribbledash.game.data.repository.GameRepositoryImpl
import zed.rainxch.plscribbledash.game.data.repository.PaintRepositoryImpl
import zed.rainxch.plscribbledash.statistics.domain.repository.StatisticsRepositoryImpl
import zed.rainxch.plscribbledash.game.domain.repository.GameRepository
import zed.rainxch.plscribbledash.game.domain.repository.PaintRepository
import zed.rainxch.plscribbledash.statistics.data.repository.StatisticsRepository
import zed.rainxch.plscribbledash.statistics.presentation.utils.DominantColorExtractor
import javax.inject.Singleton
import androidx.datastore.preferences.preferencesDataStoreFile
import zed.rainxch.plscribbledash.core.data.db.dao.ShopCanvasDao
import zed.rainxch.plscribbledash.core.data.db.dao.ShopPenDao
import zed.rainxch.plscribbledash.core.data.repository.PlayerRepositoryImpl
import zed.rainxch.plscribbledash.core.data.utils.managers.DataStoreManager
import zed.rainxch.plscribbledash.core.domain.repository.PlayerRepository
import zed.rainxch.plscribbledash.shop.data.ShopRepositoryImpl
import zed.rainxch.plscribbledash.shop.domain.ShopRepository

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

    @Binds
    @Singleton
    abstract fun bindPlayerRepository(
        impl: PlayerRepositoryImpl
    ): PlayerRepository

    @Binds
    @Singleton
    abstract fun bindShopRepository(
        impl: ShopRepositoryImpl
    ): ShopRepository

    companion object {
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Provides
        @Singleton
        fun provideDatabase(application: Application): AppDatabase {
            return Room.databaseBuilder(
                context = application,
                klass = AppDatabase::class.java,
                name = "scribble_dash_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        @Singleton
        fun provideStatisticsDao(appDatabase: AppDatabase): StatisticsDao =
            appDatabase.statisticsDao

        @Provides
        @Singleton
        fun provideShopCanvasDao(appDatabase: AppDatabase): ShopCanvasDao =
            appDatabase.shopCanvasDao

        @Provides
        @Singleton
        fun provideShopPenDao(appDatabase: AppDatabase): ShopPenDao = appDatabase.shopPenDao

        @Provides
        @Singleton
        fun provideDominantColorExtractor(context: Context): DominantColorExtractor {
            return DominantColorExtractor(context)
        }

        @Provides
        @Singleton
        fun provideDataStorePreferences(@ApplicationContext context: Context): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create(
                produceFile = { context.preferencesDataStoreFile("player") }
            )
        }

        @Provides
        @Singleton
        fun provideDataStoreManager(dataStore: DataStore<Preferences>): DataStoreManager =
            DataStoreManager(dataStore = dataStore)
    }
}