package com.example.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdemo.ui.theme.RoomDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoomDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScreenSetup(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(modifier: Modifier = Modifier) {
    MainScreen(modifier)
}
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel()) {
    val products by viewModel.allProducts.observeAsState(emptyList())
    var productName by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("") }
    val searchResults by viewModel.searchResults.observeAsState(emptyList())
    val productsToDisplay = searchResults.ifEmpty { products }


}

