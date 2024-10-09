package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * MainActivity es la actividad principal de la aplicación.
 * Esta actividad se encarga de validar el DNI ingresado por el usuario
 * comparándolo con la letra correspondiente calculada.
 */
class MainActivity : AppCompatActivity() {

    // Declaración de variables para los elementos de la interfaz de usuario.
    private lateinit var dniEditText: EditText  // Campo de texto para ingresar el número de DNI.
    private lateinit var letraEditText: EditText // Campo de texto para ingresar la letra del DNI.
    private lateinit var validarButton: Button    // Botón para iniciar la validación del DNI.

    /**
     * Método que se llama al crear la actividad.
     *
     * @param savedInstanceState Estado de la instancia anterior de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el diseño de borde a borde en la actividad.
        setContentView(R.layout.activity_main) // Establece el diseño de la actividad.

        // Configurar Window Insets para ajustar el padding en función de las barras del sistema.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets // Devuelve los insets.
        }

        // Inicializar los elementos de la interfaz de usuario.
        dniEditText = findViewById(R.id.DNI)
        letraEditText = findViewById(R.id.letra)
        validarButton = findViewById(R.id.buttonValidar)

        // Establecer un listener para el botón que llama al método validateDNI() cuando se hace clic.
        validarButton.setOnClickListener { validateDNI() }
    }

    /**
     * Método para validar el DNI ingresado por el usuario.
     * Comprueba que el número tenga 8 dígitos y que la letra sea correcta.
     */
    private fun validateDNI() {
        val dniNumber = dniEditText.text.toString() // Obtiene el texto del campo DNI como una cadena.
        val letraInput = letraEditText.text.toString().uppercase() // Convierte la letra a mayúsculas.

        // Verifica que el DNI tenga exactamente 8 dígitos y que todos sean numéricos.
        if (dniNumber.length != 8 || !dniNumber.all { it.isDigit() }) {
            showToast("El DNI debe tener 8 dígitos.") // Muestra un mensaje de error si la validación falla.
            return // Sale del método si la validación falla.
        }

        // Llama al método para calcular la letra del DNI.
        val calculatedLetter = calculateDNILetter(dniNumber.toInt())

        // Comparar la letra introducida con la letra calculada.
        if (letraInput == calculatedLetter) {
            showToast("DNI válido.") // Muestra un mensaje indicando que el DNI es válido.
        } else {
            showToast("DNI no válido.") // Muestra un mensaje indicando que el DNI no es válido.
        }
    }

    /**
     * Método para calcular la letra del DNI en función del número ingresado.
     *
     * @param dni Número del DNI como entero.
     * @return La letra correspondiente al número de DNI.
     */
    private fun calculateDNILetter(dni: Int): String {
        val letters = "TRWAGMYFPDXBNJZSQVHLCKET" // Cadena de letras posibles para el DNI.
        return letters[dni % 23].toString() // Calcula la letra correspondiente utilizando el resto de la división por 23.
    }

    /**
     * Método para mostrar mensajes de tipo Toast.
     *
     * @param message Mensaje que se desea mostrar en el Toast.
     */
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show() // Crea y muestra un Toast con el mensaje proporcionado.
    }
}
