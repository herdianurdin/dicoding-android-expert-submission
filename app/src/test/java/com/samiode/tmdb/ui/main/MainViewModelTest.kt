package com.samiode.tmdb.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.samiode.core.domain.model.Movie
import com.samiode.core.domain.usecase.MovieInteractor
import com.samiode.tmdb.utils.CoroutineTestRule
import com.samiode.tmdb.utils.DataDummy
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
class MainViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var mainViewModel: MainViewModel

    private val region = "US"
    private val dummyMovies = DataDummy.generateMovies()
    private val dummyError = DataDummy.exception

    private val responseSuccess: Flow<Result<List<Movie>>> =
        flowOf(Result.success(dummyMovies))
    private val responseFailed: Flow<Result<List<Movie>>> =
        flowOf(Result.failure(dummyError))

    @Before
    fun setup() {
        mainViewModel = MainViewModel(movieInteractor)
    }

    @Test
    fun `Get Trending Movies - Successfully`() = runTest {
        Mockito.`when`(movieInteractor.getTrendingMovies(region)).thenReturn(responseSuccess)

        mainViewModel.getTrendingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getTrendingMovies(region)

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
    fun `Get Trending Movies - Failed`() = runTest {
        Mockito.`when`(movieInteractor.getTrendingMovies(region)).thenReturn(responseFailed)

        mainViewModel.getTrendingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getTrendingMovies(region)

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
    fun `Get Popular Movies - Successfully`() = runTest {
        Mockito.`when`(movieInteractor.getPopularMovies(region)).thenReturn(responseSuccess)

        mainViewModel.getPopularMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getPopularMovies(region)

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
    fun `Get Popular Movies - Failed`() = runTest {
        Mockito.`when`(movieInteractor.getPopularMovies(region)).thenReturn(responseFailed)

        mainViewModel.getPopularMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getPopularMovies(region)

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
    fun `Get Upcoming Movies - Successfully`() = runTest {
        Mockito.`when`(movieInteractor.getUpcomingMovies(region)).thenReturn(responseSuccess)

        mainViewModel.getUpcomingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getUpcomingMovies(region)

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
    fun `Get Upcoming Movies - Failed`() = runTest {
        Mockito.`when`(movieInteractor.getUpcomingMovies(region)).thenReturn(responseFailed)

        mainViewModel.getUpcomingMovies(region).getOrAwaitValue().also { actualMovies ->
            Mockito.verify(movieInteractor).getUpcomingMovies(region)

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