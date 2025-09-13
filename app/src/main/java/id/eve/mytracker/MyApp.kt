package id.eve.mytracker

import android.app.Application
import id.eve.mytracker.core.databaseModule
import id.eve.mytracker.core.projectUseCaseModule
import id.eve.mytracker.core.repositoryModule
import id.eve.mytracker.core.taskUseCaseModule
import id.eve.mytracker.core.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                databaseModule,
                repositoryModule,
                projectUseCaseModule,
                taskUseCaseModule,
                viewModelModule
            )
        }
    }
}