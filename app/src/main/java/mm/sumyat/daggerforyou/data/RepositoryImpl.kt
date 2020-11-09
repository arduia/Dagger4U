package mm.sumyat.daggerforyou.data

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import mm.sumyat.daggerforyou.domain.Repository
import mm.sumyat.daggerforyou.network.NetworkService
import mm.sumyat.daggerforyou.storage.Movie
import mm.sumyat.daggerforyou.storage.MovieDao
import mm.sumyat.daggerforyou.util.ViewState
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val service: NetworkService
) : Repository {

    /**
     * Fetch the movie list from database if exist else fetch from web
     * and persist them in the database
     */

    override fun getMovieList(): Observable<ViewState<List<Movie>>> {

        return service.getPlayingMovie()
            .map {  it.movieResults.map {movie->
                Movie(
                    movie.id,
                    movie.title,
                    movie.poster_path,
                    movie.vote_average.toString(),
                    movie.overview,
                    movie.release_date
                )
            }}
            .doOnNext {
                movieDao.insertMovies(it)
            }
            .switchIfEmpty(movieDao.getMovies())
            .onErrorResumeWith(movieDao.getMovies())
            .map { ViewState.success(it) }
            .onErrorReturn {error ->
                ViewState.error(error.message.orEmpty())
            }
            .subscribeOn(Schedulers.io())
            .startWithItem(ViewState.loading())

    }

}