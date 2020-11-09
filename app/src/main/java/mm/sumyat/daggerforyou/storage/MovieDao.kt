package mm.sumyat.daggerforyou.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.*

/**
 * Defines access layer to movie table
 */
@Dao
interface MovieDao {

    /**
     * Insert movie into the table
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<Movie>)

    @Query("DELETE FROM movie_table")
    fun clearAllMovie()

    /**
     * Get all the movie from table
     */
    @Query("SELECT * FROM movie_table")
    fun getMovies(): Observable<List<Movie>>
}