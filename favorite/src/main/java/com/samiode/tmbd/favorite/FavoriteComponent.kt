package com.samiode.tmbd.favorite

import android.content.Context
import com.samiode.tmdb.di.FavoriteModuleDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependency::class])
interface FavoriteComponent {
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependency: FavoriteModuleDependency): Builder
        fun build(): FavoriteComponent
    }
}