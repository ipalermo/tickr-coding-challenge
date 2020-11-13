package com.tickr.challenge.di

import com.tickr.challenge.api.GuardianService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideGuardianService(): GuardianService {
        return GuardianService.create()
    }
}
