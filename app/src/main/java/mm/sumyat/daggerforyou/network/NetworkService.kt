package mm.sumyat.daggerforyou.network

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Observable
import mm.sumyat.daggerforyou.network.model.PlayingMoviewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("now_playing?page=1")
    fun getPlayingMovie(): Observable<PlayingMoviewsResponse>
}