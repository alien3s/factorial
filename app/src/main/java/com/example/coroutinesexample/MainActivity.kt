package com.example.coroutinesexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.EditText
import com.example.coroutinesexample.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var stringBuilder= StringBuilder()
    private val parametroTarea1 = 5
    private val parametroTarea2 = 10
    private lateinit var task1:MyTask
    private lateinit var job1:Job
    lateinit var coso1:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        coso1=findViewById(R.id.coso)

        binding.statusText?.movementMethod= ScrollingMovementMethod()
        stringBuilder = StringBuilder("Empezando actividad\n")
        stringBuilder.append("Esperando click.\n")
        binding.statusText.text = "${stringBuilder.toString()}"

        binding.progressBar.max = parametroTarea1 + parametroTarea2

        task1 = MyTask(this, "Tarea 1", 1.0)

        binding.btnAsync.setOnClickListener {

            startTasks(coso1.text.toString().toInt())
        }


    }

    fun startTasks(num:Int){
        job1 = MainScope().launch {
            task1.execute(num)
        }

    }



    suspend fun actualizacion(valor:Int, nombre:String) = withContext(Dispatchers.Main){
        stringBuilder.append("Tarea: ${nombre}. Tratando el parametro: ${valor}\n")
        binding.statusText.text = "${stringBuilder.toString()}"
        binding.progressBar.progress = binding.progressBar.progress + 1
    }

    suspend fun finTarea(mensaje:String, nombre: String) = withContext(Dispatchers.Main){
        stringBuilder.append("Tarea: ${nombre}.  ${mensaje}\n")
        binding.statusText.text = "${stringBuilder.toString()}"
        binding.progressBar.progress = 0
    }


}