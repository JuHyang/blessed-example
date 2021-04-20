package juhyang.practice.blessedexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.welie.blessed.BluetoothCentralManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothManager = BluetoothCentralManager(this)
    }
}
