package com.healthcare.mymolina.data.di

import com.healthcare.mymolina.data.repository.MolinaRepositoryImpl
import com.healthcare.mymolina.domain.repository.MolinaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepositoryInterface(repositoryImpl: MolinaRepositoryImpl): MolinaRepository
}