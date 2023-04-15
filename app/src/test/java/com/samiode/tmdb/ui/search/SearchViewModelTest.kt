package com.samiode.tmdb.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.samiode.core.domain.model.Movie
import com.samiode.core.domain.usecase.MovieInteractor
import com.samiode.tmdb.utils.CoroutineTestRule
import com.samiode.tmdb.utils.DataDummy.exception
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
class SearchViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var searchViewModel: SearchViewModel

    private val query = "movie"
    private val dummyMovies = generateMovies()
    private val dummyError = exception

    private val responseSuccess: Flow<Result<List<Movie>>> =
        flowOf(Result.success(dummyMovies))
    private val responseFailed: Flow<Result<List<Movie>>> =
        flowOf(Result.failure(dummyError))

    @Before
    fun setup() {
        searchViewModel = SearchViewModel(movieInteractor)
    }

    @Test
    fun `Get Search Result by Query - Successfully`() = runTest {
        Mockito.`when`(movieInteractor.getMovieByQuery(query)).thenReturn(responseSuccess)

        searchViewModel.searchMovieByQuery(query).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getMovieByQuery(query)

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
    fun `Get Search Result by Query - Failed`() = runTest {
        Mockito.`when`(movieInteractor.getMovieByQuery(query)).thenReturn(responseFailed)

        searchViewModel.searchMovieByQuery(query).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getMovieByQuery(query)

            Assert.assertNotNull(actualMovies)
            Assert.assertTrue(actualMovies.isFailure)
            Assert.assertFalse(actualMovies.isSuccess)

            actualMovies.onFailure { error ->
                Assert.assertNotNull(error)
                Assert.assertEquals(dummyError.message, error.message)
            }
        }
    }
}