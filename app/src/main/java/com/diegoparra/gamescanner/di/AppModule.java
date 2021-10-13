package com.diegoparra.gamescanner.di;

import com.diegoparra.gamescanner.data.GamesRepository;
import com.diegoparra.gamescanner.data.GamesRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

    @Singleton
    @Binds
    abstract GamesRepository bindsGamesRepository(GamesRepositoryImpl gamesRepository);

}
