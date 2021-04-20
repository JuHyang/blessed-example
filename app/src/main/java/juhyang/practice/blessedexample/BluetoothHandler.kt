package juhyang.practice.blessedexample

import android.bluetooth.BluetoothAssignedNumbers
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.welie.blessed.*

/**

 * Created by juhyang on 4/19/21.

 */
class BluetoothHandler {
    companion object {
        var instance : BluetoothHandler? = null
        fun getInstance(context : Context) : BluetoothHandler {
            if (instance == null) {
                instance = BluetoothHandler(context)
            }
            return instance!!
        }
    }

    var bluetoothCentralManager : BluetoothCentralManager? = null

    val bluetoothCentralManagerCallback : BluetoothCentralManagerCallback = object : BluetoothCentralManagerCallback() {
        override fun onConnectedPeripheral(peripheral: BluetoothPeripheral) {
            super.onConnectedPeripheral(peripheral)
        }

        override fun onConnectionFailed(peripheral: BluetoothPeripheral, status: HciStatus) {
            super.onConnectionFailed(peripheral, status)
        }

        override fun onDisconnectedPeripheral(peripheral: BluetoothPeripheral, status: HciStatus) {
            super.onDisconnectedPeripheral(peripheral, status)
        }

        override fun onDiscoveredPeripheral(
            peripheral: BluetoothPeripheral,
            scanResult: ScanResult
        ) {
            super.onDiscoveredPeripheral(peripheral, scanResult)
        }

        override fun onBluetoothAdapterStateChanged(state: Int) {
            super.onBluetoothAdapterStateChanged(state)
        }

        override fun onScanFailed(scanFailure: ScanFailure) {
            super.onScanFailed(scanFailure)
        }
    }

    private constructor(context : Context) {
        bluetoothCentralManager = BluetoothCentralManager(context, bluetoothCentralManagerCallback, Handler(
            Looper.getMainLooper()))
    }
}
