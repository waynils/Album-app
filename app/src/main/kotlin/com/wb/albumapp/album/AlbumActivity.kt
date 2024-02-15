package com.wb.albumapp.album

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.wb.albumApp.R
import com.wb.albumapp.network.NetworkConnectivity
import com.wb.ui.compose.molecules.toppappbar.TopBarApp
import com.wb.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class AlbumActivity : ComponentActivity() {

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    private val viewModel: AlbumViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }
                    val topBarAppData = viewModel.topAppBarStateFlow.collectAsState()

                    LaunchedEffect(Unit) {
                        networkConnectivity.isNetworkConnected.collectLatest { isNetworkConnected ->
                            when (isNetworkConnected) {
                                true -> snackbarHostState.currentSnackbarData?.dismiss()
                                false -> snackbarHostState.showSnackbar(
                                    getString(R.string.no_network),
                                    duration = SnackbarDuration.Indefinite
                                )
                            }
                        }
                    }

                    Scaffold(
                        scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState),
                        topBar = {
                            TopBarApp(
                                data = topBarAppData.value,
                                onBackAction = {
                                    navController.popBackStack()
                                    viewModel.updateTopAppBarList()
                                })
                        },
                        content = { paddingValues ->

                            AlbumNavGraph(
                                navController,
                                navigateToDetail = { albumId ->
                                    viewModel.updateTopAppBarDetail()

                                    navController.navigate(
                                        AlbumScreenType.Detail.createRoute(
                                            albumId
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .padding(paddingValues)
                                    .padding(horizontal = 16.dp)
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                }
            }
        }
    }
}