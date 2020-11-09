package mm.sumyat.daggerforyou.domain

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import mm.sumyat.daggerforyou.storage.Movie
import mm.sumyat.daggerforyou.util.ViewState

interface Repository {
    fun getMovieList() : Observable<ViewState<List<Movie>>>
}