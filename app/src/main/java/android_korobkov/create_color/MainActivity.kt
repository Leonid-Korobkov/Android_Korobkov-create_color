package android_korobkov.create_color

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android_korobkov.create_color.databinding.ActivityMainBinding
import android_korobkov.create_color.databinding.ColorPickBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var colorPickBinding: ColorPickBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Привязываем макет activity_main.xml к активити
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        // Устанавливаем слушатель для кнопки выбора цвета
        mainBinding.btnPickColor.setOnClickListener {
            // Отображаем диалоговое окно выбора цвета
            showDialog()
        }
    }

    // Функция для отображения диалогового окна выбора цвета
    private fun showDialog() {
        // Загружаем макет color_pick.xml для диалогового окна
        val dialogView = layoutInflater.inflate(R.layout.color_pick, null)
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Выбор цвета") // Устанавливаем заголовок диалогового окна

        // Создаем и отображаем диалоговое окно
        val dialog = dialogBuilder.show()

        // Привязываем View-элементы из color_pick.xml с помощью ViewBinding
        colorPickBinding = ColorPickBinding.bind(dialogView)

        // Устанавливаем слушатель для кнопки "OK"
        colorPickBinding.btnOk.setOnClickListener {
            // Получаем значения компонентов цвета из текстовых полей
            val red = colorPickBinding.editRed.text.toString().toIntOrNull() ?: 0
            val green = colorPickBinding.editGreen.text.toString().toIntOrNull() ?: 0
            val blue = colorPickBinding.editBlue.text.toString().toIntOrNull() ?: 0

            // Устанавливаем выбранный цвет
            setColor(red, green, blue)

            // Закрываем диалоговое окно
            dialog.dismiss()
        }

        // Устанавливаем слушатель для кнопки "Отмена"
        colorPickBinding.btnCencel.setOnClickListener {
            // Закрываем диалоговое окно
            dialog.dismiss()
        }

        // Устанавливаем слушатели для ползунков изменения цвета
        colorPickBinding.barRed.setOnSeekBarChangeListener(createSeekBarChangeListener(colorPickBinding.editRed))
        colorPickBinding.barGreen.setOnSeekBarChangeListener(createSeekBarChangeListener(colorPickBinding.editGreen))
        colorPickBinding.barBlue.setOnSeekBarChangeListener(createSeekBarChangeListener(colorPickBinding.editBlue))
    }

    // Функция для создания слушателя изменения ползунка
    private fun createSeekBarChangeListener(editText: EditText): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Устанавливаем текст в соответствующее текстовое поле
                editText.setText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        }
    }

    // Функция для установки выбранного цвета
    private fun setColor(red: Int, green: Int, blue: Int) {
        // Создаем цвет на основе заданных значений RGB
        val color = Color.rgb(red, green, blue)
        // Устанавливаем цвет фона для фона активности
        mainBinding.backgroundColor.setBackgroundColor(color)
    }
}
