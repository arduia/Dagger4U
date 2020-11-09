package mm.sumyat.daggerforyou.ui.screen

import androidx.lifecycle.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import mm.sumyat.daggerforyou.domain.Repository
import mm.sumyat.daggerforyou.storage.Movie
import mm.sumyat.daggerforyou.util.AbsentLiveData
import mm.sumyat.daggerforyou.util.ViewState
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    //kind of action to call repository
    private val _callRepo = MutableLiveData<String>()

    //listen everytime data changes insdie _call
    val callRepo: LiveData<String> = _callRepo

    private var movieDisposable: Disposable? = null

    private val _results = MutableLiveData<ViewState<List<Movie>>>()
    val results: LiveData<ViewState<List<Movie>>> = Transformations.switchMap(callRepo) {
        if (it.isEmpty()) AbsentLiveData.create<ViewState<List<Movie>>>()
        else {
            updateMovieList()
            _results
        }
    }

    private fun updateMovieList() {
        movieDisposable = repository.getMovieList()
            .subscribeOn(Schedulers.io())
            .subscribe(_results::postValue)
    }

    init {
        _callRepo.value = "init"
    }

    fun refreshCall() {
        _callRepo.value = "refresh"
    }

    override fun onCleared() {
        super.onCleared()
        movieDisposable?.dispose()
    }
}