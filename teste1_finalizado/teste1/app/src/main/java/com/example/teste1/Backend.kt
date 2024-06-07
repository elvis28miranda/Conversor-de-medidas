package com.example.teste1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teste1.R

class Backend : AppCompatActivity() {

    //Elementos do activity_main2.xml
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var spinner3: Spinner
    private lateinit var btnCalcular: Button
    private lateinit var txtResultado: TextView
    private lateinit var input1: EditText
    private lateinit var input2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_backend)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialize os elementos da interface do usuário após inflar o layout
        // (pega os elementos pelo id)
        spinner1 = findViewById(R.id.spinner4)
        spinner2 = findViewById(R.id.spinner5)
        spinner3 = findViewById(R.id.spinner6)
        btnCalcular = findViewById(R.id.btnCalcular)
        txtResultado = findViewById(R.id.txtResultado)
        input1 = findViewById(R.id.valor1)
        input2 = findViewById(R.id.valor2)

        // array com as medidas
        val options1 = arrayOf("Comprimento", "Massa", "Temperatura", "Frequencia")
        //Conjunto de arrays para unidades de medidas da respectiva medida
        val options2 = mapOf(
            "Comprimento" to arrayOf( "Quilometros", "Metros", "Centimetros", "Milimetros",
                "Pes", "Polegadas"),
            "Massa" to arrayOf("Toneladas", "Quilos", "Gramas", "Miligramas", "Libras"),
            "Temperatura" to arrayOf("Celsius", "Fahrenheit", "Kelvin"),
            "Frequencia" to arrayOf ("Hertz", "Quilo-hertz", "Mega-hertz", "Gigahertz"),
        )

        // Chama a função e  passa os valores(arrays) para os spinners
        setupSpinners(options1, options2)
    }

    private fun setupSpinners(options1: Array<String>, options2: Map<String, Array<String>>) {
        val adapter1 =
            ArrayAdapter(this@Backend, android.R.layout.simple_spinner_item, options1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = adapter1

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                val options = options2[selectedItem] ?: arrayOf()

                // Passa valores para o spinner 2 (primeira unidade de medida)
                val adapter2 =
                    ArrayAdapter(this@Backend, android.R.layout.simple_spinner_item, options)
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner2.adapter = adapter2
                spinner2.visibility = if (options.isNotEmpty()) View.VISIBLE else View.GONE

                // Passa valores para o spinner 3 (segunda unidade de medida)
                val adapter3 =
                    ArrayAdapter(this@Backend, android.R.layout.simple_spinner_item, options)
                adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner3.adapter = adapter3
                spinner3.visibility = if (options.isNotEmpty()) View.VISIBLE else View.GONE
            }

            //Função sem utilidade por enquanto,
            // PODEM APAGAR CASO QUEIRAM OU COMENTEM ELA, MAS NO GERAL NÃO ATRAPALHA NADA ESTA FUNCAO
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ação quando nenhum item é selecionado
            }
        }
    }

    //Função que valida os campos(inputs), para ver se estão vazios
    fun validarCampos(valorInput1: String, valorInput2: String): Boolean {

        var camposValidados = true

        if (valorInput1.isBlank()) {
            camposValidados = false
        }

        return camposValidados
    }

    //Função que pega os valores dos inputs e mostra a respectiva unidade de medida
    fun calcular(view: View) {

        //Pega o valor atual do spinner
        val unidadeMedida = spinner1.selectedItem.toString()
        val unidade1 = spinner2.selectedItem.toString()
        val unidade2 = spinner3.selectedItem.toString()

        //Pega o valor do input
        val valorInput1 = input1.text.toString()
        val valorInput2 = input2.text.toString()

        //Chama a função e passa os valores dos inputs
        val validaCampos = validarCampos(valorInput1, valorInput2)

        /*Se o retorno da funcao validar campos for false ele exibe a msg e
        para o fluxo do codigo */
        if (!validaCampos) {
            txtResultado.text = "Preencha os campos!"
            return
        }
        txtResultado.text = ""

        //Verifica qual é a medida e chama a função respectiva daquela medida
        when (unidadeMedida) {
            "Comprimento" -> {
                Comprimento(unidade1, unidade2, valorInput1, valorInput2)
            }

            "Massa" -> {
                Massa(unidade1, unidade2, valorInput1, valorInput2)
            }

            "Temperatura" -> {
                Temperatura(unidade1, unidade2, valorInput1, valorInput2)
            }

            "Frequencia" ->{
                Frequencia(unidade1, unidade2, valorInput1, valorInput2)
            }
        }

    }

    fun Comprimento(unidade1: String, unidade2: String, valor1: String, valor2: String) {
        var valorConvertido: Double

        if (unidade1 == unidade2) {
            txtResultado.text = "selecione uma medida diferente"
            input2.setText("")
            return
        }
        txtResultado.text = ""

        if (unidade1 == "Metros") {
            if (unidade2 == "Centimetros") {
                valorConvertido = valor1.toDouble() * 100.0
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Milimetros") {
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Quilometros") {
                valorConvertido = valor1.toDouble() / 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Polegadas") {
                valorConvertido = valor1.toDouble() * 39.37
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Pes") {
                valorConvertido = valor1.toDouble() * 3.281
                input2.setText(valorConvertido.toString())
                return
            }

        }

        //Conversão caso a unidade seja centímetros
        if (unidade1 == "Centimetros") {
            if (unidade2 == "Metros") {
                valorConvertido = valor1.toDouble() / 100
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Milimetros") {
                valorConvertido = valor1.toDouble() * 10
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Quilometros") {
                valorConvertido = valor1.toDouble() / 100000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Polegadas") {
                valorConvertido = valor1.toDouble() / 2.54
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Pes") {
                valorConvertido = valor1.toDouble() / 30.48
                input2.setText(valorConvertido.toString())
                return
            }

        }

        //Conversão caso a unidade seja milímetros
        if (unidade1 == "Milimetros") {
            if (unidade2 == "Metros") {
                valorConvertido = valor1.toDouble() / 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Centimetros") {
                valorConvertido = valor1.toDouble() / 10
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Quilometros") {
                valorConvertido = valor1.toDouble() / 1e+6
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Polegadas") {
                valorConvertido = valor1.toDouble() / 25.4
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Pes") {
                valorConvertido = valor1.toDouble() / 304.8
                input2.setText(valorConvertido.toString())
                return
            }
        }

        //Conversão para caso a unidade seja quilômetros
        if (unidade1 == "Quilometros") {
            if (unidade2 == "Metros") {
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Centimetros") {
                valorConvertido = valor1.toDouble() / 100000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Milimetros") {
                valorConvertido = valor1.toDouble() * 1e+6
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Polegadas") {
                valorConvertido = valor1.toDouble() * 39370
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Pes") {
                valorConvertido = valor1.toDouble() / 3281
                input2.setText(valorConvertido.toString())
                return
            }

        }


        if (unidade1 == "Polegadas") {
            if (unidade2 == "Metros") {
                valorConvertido = valor1.toDouble() / 39.37
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Centimetros") {
                valorConvertido = valor1.toDouble() * 2.54
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Milimetros") {
                valorConvertido = valor1.toDouble() * 25.4
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Quilometros") {
                valorConvertido = valor1.toDouble() / 39370
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Pes") {
                valorConvertido = valor1.toDouble() / 12
                input2.setText(valorConvertido.toString())
                return
            }
        }
    }

    //Conversão para as unidades de Massa
    fun Massa(unidade1: String, unidade2: String, valor1: String, valor2: String) {
        var valorConvertido: Double

        if (unidade1 == unidade2) {
            txtResultado.text = "selecione uma medida diferente"
            input2.setText("")
            return
        }
        txtResultado.text = ""

        //Conversão para caso a unidade seja Quilos
        if (unidade1 == "Quilos") {
            if (unidade2 == "Toneladas") {
                valorConvertido = valor1.toDouble() / 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Gramas") {
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Miligrama") {
                valorConvertido = valor1.toDouble() * 1e+6
                input2.setText(valorConvertido.toString())
                return
            }


            if (unidade2 == "Libras") {
                valorConvertido = valor1.toDouble() * 2.205
                input2.setText(valorConvertido.toString())
                return
            }
        }

        //Conversão caso a unidade seja Toneladas
        if (unidade1 == "Toneladas") {
            if (unidade2 == "Quilos") {
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Gramas") {
                valorConvertido = valor1.toDouble() * 1e+6
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Miligrama") {
                valorConvertido = valor1.toDouble() * 1e+9
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Libras") {
                valorConvertido = valor1.toDouble() * 2205
                input2.setText(valorConvertido.toString())
                return
            }
        }


        if (unidade1 == "Gramas") {
            if (unidade2 == "Quilos") {
                valorConvertido = valor1.toDouble() / 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Toneladas") {
                valorConvertido = valor1.toDouble() / 1e+6
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Miligrama") {
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Libras") {
                valorConvertido = valor1.toDouble() / 453.6
                input2.setText(valorConvertido.toString())
                return
            }


        }

        if (unidade1 == "Miligramas") {
            if (unidade2 == "Quilos") {
                valorConvertido = valor1.toDouble() / 1e+6
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Toneladas") {
                valorConvertido = valor1.toDouble() / 1e+9
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Gramas ") {
                valorConvertido = valor1.toDouble() / 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Libras") {
                valorConvertido = valor1.toDouble() / 453600
                input2.setText(valorConvertido.toString())
                return
            }


        }

        if (unidade1 == "Libras") {
            if (unidade2 == "Quilos") {
                valorConvertido = valor1.toDouble() / 2.205
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Toneladas") {
                valorConvertido = valor1.toDouble() / 2205
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Gramas ") {
                valorConvertido = valor1.toDouble() * 453.6
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Miligramas") {
                valorConvertido = valor1.toDouble() / 453600

                input2.setText(valorConvertido.toString())
                return
            }


        }

    }

    //Conversão para as unidades de Temperatura
    fun Temperatura(unidade1: String, unidade2: String, valor1: String, valor2: String) {
        var valorConvertido: Double

        if(unidade1 == unidade2) {
            txtResultado.text = "selecione uma medida diferente"
            input2.setText("")
            return
        }
        txtResultado.text = ""

        //Conversão caso a unidade seja Celsius
        if (unidade1 == "Celsius") {
            if (unidade2 == "Fahrenheit") {
                valorConvertido = (valor1.toDouble() * 9 / 5) + 32
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Kelvin") {
                valorConvertido = valor1.toDouble() + 273.15
                input2.setText(valorConvertido.toString())
                return
            }

            return
        }

        //Conversão caso a unidade seja Fahrenheit
        if (unidade1 == "Fahrenheit") {
            if (unidade2 == "Celsius") {
                valorConvertido = (valor1.toDouble() - 32) * 5 / 9
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Kelvin") {
                valorConvertido = (valor1.toDouble() - 32) * 5 / 9 + 273.15
                input2.setText(valorConvertido.toString())
                return
            }
        }

        //Conversão caso a unidade seja Kelvin
        if (unidade1 == "Kelvin") {
            if (unidade2 == "Celsius") {
                valorConvertido = valor1.toDouble() - 273.15
                input2.setText(valorConvertido.toString())
                return
            }

            if (unidade2 == "Fahrenheit") {
                valorConvertido = (valor1.toDouble() - 273.15) * 9 / 5 + 32
                input2.setText(valorConvertido.toString())
                return
            }

        }
    }

    //Conversão para as unidades de frequencia
    fun Frequencia(unidade1:String, unidade2: String, valor1: String, valor2: String){
        var valorConvertido: Double

        if(unidade1 == unidade2) {
            txtResultado.text = "selecione uma medida diferente"
            input2.setText("")
            return
        }
        txtResultado.text = ""

        if (unidade1 == "Hertz"){
            if(unidade2 == "Quilo-hertz"){
                valorConvertido = valor1.toDouble() /1000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Mega-hertz"){
                valorConvertido = valor1.toDouble() /1000000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Gigahertz"){
                valorConvertido = valor1.toDouble() /1000000000
                input2.setText(valorConvertido.toString())
                return
            }
        }

        if (unidade1 == "Quilo-hertz"){
            if(unidade2 == "Hertz"){
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Mega-hertz"){
                valorConvertido = valor1.toDouble() / 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Gigahertz"){
                valorConvertido = valor1.toDouble() /1000000
                input2.setText(valorConvertido.toString())
                return
            }
        }

        if (unidade1 == "Mega-hertz"){

            if(unidade2 == "Hertz"){
                valorConvertido = valor1.toDouble() *1000000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Quilo-hertz"){
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Gigahertz"){
                valorConvertido = valor1.toDouble() / 1000
                input2.setText(valorConvertido.toString())
                return
            }
        }

        if (unidade1 == "Gigahertz"){

            if(unidade2 == "Hertz"){
                valorConvertido = valor1.toDouble() *1000000000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Quilo-hertz"){
                valorConvertido = valor1.toDouble() * 1000000
                input2.setText(valorConvertido.toString())
                return
            }

            if(unidade2 == "Mega-hertz"){
                valorConvertido = valor1.toDouble() * 1000
                input2.setText(valorConvertido.toString())
                return
            }

        }
    }
}