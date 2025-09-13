package id.eve.mytracker.core

import androidx.room.Room
import id.eve.mytracker.data.db.AppDatabase
import id.eve.mytracker.data.repository.ProjectRepositoryImpl
import id.eve.mytracker.data.repository.TaskRepositoryImpl
import id.eve.mytracker.domain.repository.ProjectRepository
import id.eve.mytracker.domain.repository.TaskRepository
import id.eve.mytracker.domain.usecase.DeleteProjectUseCase
import id.eve.mytracker.domain.usecase.DeleteTaskUseCase
import id.eve.mytracker.domain.usecase.GetAllProjectsUseCase
import id.eve.mytracker.domain.usecase.GetAllProjectsWithTasksFlow
import id.eve.mytracker.domain.usecase.GetAllTasksByProjectFlow
import id.eve.mytracker.domain.usecase.GetProjectByIdUseCase
import id.eve.mytracker.domain.usecase.GetTaskByIdUseCase
import id.eve.mytracker.domain.usecase.InsertProjectUseCase
import id.eve.mytracker.domain.usecase.InsertTaskUseCase
import id.eve.mytracker.domain.usecase.UpdateProjectUseCase
import id.eve.mytracker.domain.usecase.UpdateTaskUseCase
import id.eve.mytracker.present.viewmodel.ProjectViewModel
import id.eve.mytracker.present.viewmodel.TaskViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single { get<AppDatabase>().projectDao() }
    single { get<AppDatabase>().taskDao() }
}

val repositoryModule = module {
    single<ProjectRepository> { ProjectRepositoryImpl(get()) }
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}

val projectUseCaseModule = module {
    single { GetAllProjectsUseCase(get()) }
    single { InsertProjectUseCase(get()) }
    single { UpdateProjectUseCase(get()) }
    single { DeleteProjectUseCase(get()) }
    single { GetAllProjectsWithTasksFlow(get()) }
    single { GetProjectByIdUseCase(get()) }
}

val taskUseCaseModule = module {
    single { GetAllTasksByProjectFlow(get()) }
    single { InsertTaskUseCase(get()) }
    single { UpdateTaskUseCase(get()) }
    single { DeleteTaskUseCase(get()) }
    single { GetTaskByIdUseCase(get()) }
}
val viewModelModule = module {
    viewModel {
        ProjectViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get())
    }
    viewModel {
        TaskViewModel(
            get(),
            get(),
            get(),
            get())
    }
}