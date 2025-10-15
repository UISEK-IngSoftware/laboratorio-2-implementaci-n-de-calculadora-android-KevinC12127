package ec.edu.uisek.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.calculator.ui.theme.CalculatorEvent
import ec.edu.uisek.calculator.ui.theme.CalculatorViewModel
import ec.edu.uisek.calculator.ui.theme.Purple40
import ec.edu.uisek.calculator.ui.theme.Purple80
import ec.edu.uisek.calculator.ui.theme.UiSekBlue

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {
    val state = viewModel.state

    // Pantalla principal con fondo negro
    androidx.compose.foundation.layout.Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Establece el fondo negro
            .padding(8.dp),
        // Esta línea es crucial: alinea el contenido en la parte inferior
        verticalArrangement = Arrangement.Bottom
    ) {
        // Display de la calculadora
        Text(
            text = state.display,
            modifier = Modifier
                .fillMaxWidth()
                // Usamos weight para que ocupe el espacio restante y empuje los botones abajo
                .weight(1f)
                .padding(vertical = 24.dp, horizontal = 16.dp),
            textAlign = TextAlign.End,
            fontSize = 72.sp, // Aumentado el tamaño para mejor visibilidad en pantalla negra
            color = Color.White // Cambiado a blanco para contraste
        )

        // Cuadrícula de botones
        CalculatorGrid(onEvent = viewModel::onEvent)
    }
}

@Composable
fun CalculatorGrid(onEvent: (CalculatorEvent) -> Unit) {
    val buttons = listOf(
        "7", "8", "9", "÷",
        "4", "5", "6", "×",
        "1", "2", "3", "−",
        "0", ".", "=", "+"
    )

    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(buttons.size) { index ->
            val label = buttons[index]
            CalculatorButton(label = label) {
                when (label) {
                    in "0".."9" -> onEvent(CalculatorEvent.Number(label))
                    "." -> onEvent(CalculatorEvent.Decimal)
                    "=" -> onEvent(CalculatorEvent.Calculate)
                    else -> onEvent(CalculatorEvent.Operator(label))
                }
            }
        }

        item(span = { GridItemSpan(2) }) { CalculatorButton(label = "AC") { onEvent(CalculatorEvent.AllClear) } }
        item {}
        item { CalculatorButton(label = "C") { onEvent(CalculatorEvent.Clear) } }
    }
}

@Composable
fun CalculatorButton(label: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            // Si el botón es "AC", ocupa el doble de ancho
            .aspectRatio(if (label == "AC") 2f else 1f)
            .padding(6.dp)
            .clip(CircleShape)
            .background(
                color = when (label) {
                    in listOf("÷", "×", "−", "+", "=", ".") -> Purple40
                    in listOf("AC", "C") -> Color.Red
                    else -> UiSekBlue
                }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    CalculatorScreen()
}