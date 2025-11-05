package com.example.roomdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdemo.ui.theme.RoomDemoTheme
import com.example.roomdemo.Product

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

    Spacer(modifier = Modifier.height(20.dp))

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        val buttonColors = buttonColors(containerColor = Color(0xFF6050DC))
        val buttonModifier = Modifier
            .size(width = 93.dp, height = 38.dp)
            .padding(horizontal = 1.dp)

        Button(
            onClick = {
                viewModel.addProduct(productName, quantity.toIntOrNull() ?: 0)
                productName = ""
                quantity = ""
            },
            colors = buttonColors,
            shape = RoundedCornerShape(20.dp),
            modifier = buttonModifier
        ) {
            Text("Add",fontSize = 13.sp)
        }
        Button(
            onClick = {viewModel.findProduct(productName)},
            colors = buttonColors,
            shape = RoundedCornerShape(20.dp),
            modifier = buttonModifier
        ) {
            Text("Search",fontSize = 13.sp)
        }
        Button(
            onClick = {viewModel.deleteProduct(productName)},
            colors = buttonColors,
            shape = RoundedCornerShape(20.dp),
            modifier = buttonModifier
        ) {
            Text("Delete",fontSize = 13.sp)
        }
        Button(
            onClick = {
                productName = ""
                quantity = ""
            },
            colors = buttonColors,
            shape = RoundedCornerShape(20.dp),
            modifier = buttonModifier
        ) {
            Text("Clear",fontSize = 13.sp)
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    LazyColumn {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6050DC))
                    .padding(vertical = 4.dp)
            ) {
                Text("ID", Modifier.weight(1f), color = Color.White)
                Text("Product", Modifier.weight(3f), color = Color.White)
                Text("Quantity", Modifier.weight(2f), color = Color.White)
            }
        }
        items(productsToDisplay) { product ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            ) {
                Text(product.id.toString(), Modifier.weight(1f))
                Text(product.productName, Modifier.weight(3f))
                Text(product.quantity.toString(), Modifier.weight(2f))
            }
        }
    }

}


