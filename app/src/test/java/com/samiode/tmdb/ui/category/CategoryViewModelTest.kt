package com.samiode.tmdb.ui.category

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
class CategoryViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var movieInteractor: MovieInteractor
    private lateinit var categoryViewModel: CategoryViewModel

    private val region = "US"
    private val dummyMovies = generateMovies()
    private val dummyError = exception

    private val responseSuccess: Flow<Result<List<Movie>>> =
        flowOf(Result.success(dummyMovies))
    private val responseFailed: Flow<Result<List<Movie>>> =
        flowOf(Result.failure(dummyError))

    @Before
    fun setup() {
        categoryViewModel = CategoryViewModel(movieInteractor)
    }

    @Test
    fun `Get Trending Movies - Successfully`() = runTest {
        Mockito.`when`(movieInteractor.getTrendingMovies(region)).thenReturn(responseSuccess)

        categoryViewModel.getTrendingMovies(region).getOrAwaitValue().also { actualMovies ->
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

        categoryViewModel.getTrendingMovies(region).getOrAwaitValue().also { actualMovies ->
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

        categoryViewModel.getPopularMovies(region).getOrAwaitValue().also { actualMovies ->
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

        categoryViewModel.getPopularMovies(region).getOrAwaitValue().also { actualMovies ->
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

        categoryViewModel.getUpcomingMovies(region).getOrAwaitValue().also { actualMovies ->
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

        categoryViewModel.getUpcomingMovies(region).getOrAwaitValue().also { actualMovies ->
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