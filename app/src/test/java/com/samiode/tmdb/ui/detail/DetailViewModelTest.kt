package com.samiode.tmdb.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.samiode.core.domain.model.Cast
import com.samiode.core.domain.model.Movie
import com.samiode.core.domain.usecase.MovieInteractor
import com.samiode.tmdb.utils.CoroutineTestRule
import com.samiode.tmdb.utils.DataDummy.exception
import com.samiode.tmdb.utils.DataDummy.generateCasts
import com.samiode.tmdb.utils.DataDummy.generateMovies
import com.samiode.tmdb.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var detailViewModel: DetailViewModel

    private val dummyId = 1
    private val dummyCasts = generateCasts()
    private val dummyMovies = generateMovies()
    private val dummyMovie = dummyMovies[dummyId]
    private val dummyError = exception

    private val responseCastsSuccess: Flow<Result<List<Cast>>> =
        flowOf(Result.success(dummyCasts))
    private val responseCastsFailed: Flow<Result<List<Cast>>> =
        flowOf(Result.failure(dummyError))

    private val responseMoviesSuccess: Flow<Result<List<Movie>>> =
        flowOf(Result.success(dummyMovies))
    private val responseMoviesFailed: Flow<Result<List<Movie>>> =
        flowOf(Result.failure(dummyError))

    @Before
    fun setup() {
        detailViewModel = DetailViewModel(movieInteractor)
    }

    @Test
    fun `Get Casts of The Movie - Successfully`() = runTest {
        Mockito.`when`(movieInteractor.getMovieCasts(dummyId)).thenReturn(responseCastsSuccess)

        detailViewModel.getMovieCasts(dummyId).getOrAwaitValue().also { actualCasts ->
            Mockito.verify(movieInteractor).getMovieCasts(dummyId)

            Assert.assertNotNull(actualCasts)
            Assert.assertTrue(actualCasts.isSuccess)
            Assert.assertFalse(actualCasts.isFailure)

            actualCasts.onSuccess { casts ->
                Assert.assertNotNull(casts)

                casts.forEachIndexed { index, cast ->
                    Assert.assertEquals(dummyCasts[index].name, cast.name)
                }
            }
        }
    }

    @Test
    fun `Get Casts of The Movie - Failed`() = runTest {
        Mockito.`when`(movieInteractor.getMovieCasts(dummyId)).thenReturn(responseCastsFailed)

        detailViewModel.getMovieCasts(dummyId).getOrAwaitValue().also { actualCasts ->
            Mockito.verify(movieInteractor).getMovieCasts(dummyId)

            Assert.assertNotNull(actualCasts)
            Assert.assertTrue(actualCasts.isFailure)
            Assert.assertFalse(actualCasts.isSuccess)

            actualCasts.onFailure { error ->
                Assert.assertNotNull(error)
                Assert.assertEquals(dummyError.message, error.message)
            }
        }
    }

    @Test
    fun `Get Recommendation Movies - Successfully`() = runTest {
        Mockito.`when`(movieInteractor.getRecommendationMovies(dummyId)).thenReturn(responseMoviesSuccess)

        detailViewModel.getRecommendationMovies(dummyId).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getRecommendationMovies(dummyId)

            Assert.assertNotNull(actualMovies)
            Assert.assertTrue(actualMovies.isSuccess)
            Assert.assertFalse(actualMovies.isFailure)

            actualMovies.onSuccess { movies ->
                Assert.assertNotNull(movies)

                movies.forEachIndexed { index, movie ->
                    Assert.assertEquals(dummyMovies[index].title, movie.title)
                }
            }
        }
    }

    @Test
    fun `Get Recommendation Movies - Failed`() = runTest {
        Mockito.`when`(movieInteractor.getRecommendationMovies(dummyId)).thenReturn(responseMoviesFailed)

        detailViewModel.getRecommendationMovies(dummyId).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getRecommendationMovies(dummyId)

            Assert.assertNotNull(actualMovies)
            Assert.assertTrue(actualMovies.isFailure)
            Assert.assertFalse(actualMovies.isSuccess)

            actualMovies.onFailure { error ->
                Assert.assertNotNull(error)
                Assert.assertEquals(dummyError.message, error.message)
            }
        }
    }

    @Test
    fun `Get Favorite Movie State`() = runTest {
        val response = flowOf(true)

        Mockito.`when`(movieInteractor.isFavoriteMovie(dummyMovie.id)).thenReturn(response)

        detailViewModel.isFavoriteMovie(dummyMovie.id).getOrAwaitValue().also { actualResponse ->
            Mockito.verify(movieInteractor).isFavoriteMovie(dummyMovie.id)

            Assert.assertNotNull(actualResponse)
            Assert.assertTrue(actualResponse)
        }
    }

    @Test
    fun `Put Movie as Favorite`() = runTest {
        detailViewModel.putMovieAsFavorite(dummyMovie).also {
            Mockito.verify(movieInteractor).putMovieAsFavorite(dummyMovie)
        }
    }

    @Test
    fun `Remove Movie From Favorite`() = runTest {
        detailViewModel.removeMovieFromFavorite(dummyMovie.id).also {
            Mockito.verify(movieInteractor).removeMovieFromFavorite(dummyMovie.id)
        }
    }
}