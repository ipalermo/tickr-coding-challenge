package com.tickr.challenge.di

import com.tickr.challenge.api.GuardianService
import com.tickr.challenge.data.GuardianRepository
import com.tickr.challenge.data.DefaultGuardianRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    @Named("defaultRepository")
    fun provideDefaultRepository(
        service: GuardianService
    ): GuardianRepository {
        return DefaultGuardianRepository(service)
    }
}
