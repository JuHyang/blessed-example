package juhyang.practice.blessedexample

import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.util.Log
import com.welie.blessed.*
import timber.log.Timber
import java.lang.String
import java.util.*

/**

 * Created by juhyang on 4/19/21.

 */
class BluetoothHandler {
    companion object {
        var instance : BluetoothHandler? = null
        fun getInstance(context: Context) : BluetoothHandler {
            if (instance == null) {
                instance = BluetoothHandler(context)
            }
            return instance!!
        }
    }

    var bluetoothCentralManager : BluetoothCentralManager? = null
    val context : Context
    private val handler = Handler(Looper.getMainLooper())

    private val HRS_SERVICE_UUID = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb")


    private constructor(context: Context) {
        this.context = context

        bluetoothCentralManager = BluetoothCentralManager(
            context, bluetoothCentralManagerCallback, Handler(
                Looper.getMainLooper()
            )
        )
        bluetoothCentralManager!!.setScanMode(ScanMode.LOW_LATENCY)
        bluetoothCentralManager!!.startPairingPopupHack()
        handler.postDelayed({
            bluetoothCentralManager!!.scanForPeripherals()
        }, 1000)
    }

    val bluetoothCentralManagerCallback : BluetoothCentralManagerCallback = object : BluetoothCentralManagerCallback() {
        override fun onConnectedPeripheral(peripheral: BluetoothPeripheral) {
            super.onConnectedPeripheral(peripheral)
            Log.d("hyang@connected", "onConnectedPeripheral")
            Log.d("hyang@peripheral", "${peripheral}")
        }

        override fun onConnectionFailed(peripheral: BluetoothPeripheral, status: HciStatus) {
            super.onConnectionFailed(peripheral, status)

            Log.d("hyang@onConnectionFailed", "onConnectionFailed")
            Log.d("hyang@peripheral", "${peripheral}")
            Log.d("hyang@status", "${status}")
        }

        override fun onDisconnectedPeripheral(peripheral: BluetoothPeripheral, status: HciStatus) {
            super.onDisconnectedPeripheral(peripheral, status)

            Log.d("hyang@onDisconnectedPeripheral", "onDisconnectedPeripheral")
            Log.d("hyang@peripheral", "${peripheral}")
            Log.d("hyang@status", "${status}")
        }

        override fun onDiscoveredPeripheral(
            peripheral: BluetoothPeripheral,
            scanResult: ScanResult
        ) {
            super.onDiscoveredPeripheral(peripheral, scanResult)

            val central = bluetoothCentralManager ?: return

            Log.d("hyang@onDiscoveredPeripheral", "onDiscoveredPeripheral")
            Log.d("hyang@peripheral", "${peripheral}")
            Log.d("hyang@scanResult", "${scanResult}")

            Timber.d("DviceName : ${scanResult.device.name}")

            central.stopScan()
            central.autoConnectPeripheral(peripheral, peripheralCallback)
//                central.connectPeripheral(peripheral, peripheralCallback)
        }

        override fun onBluetoothAdapterStateChanged(state: Int) {
            super.onBluetoothAdapterStateChanged(state)

            Log.d("hyang@onBluetoothAdapterStateChanged", "onBluetoothAdapterStateChanged")
            Log.d("hyang@state", "${state}")
        }

        override fun onScanFailed(scanFailure: ScanFailure) {
            super.onScanFailed(scanFailure)

            Log.d("hyang@scanFailure", "scanFailure")
            Log.d("hyang@scanFailure", "${scanFailure}")
        }
    }

    val peripheralCallback = object : BluetoothPeripheralCallback() {
        override fun onConnectionUpdated(
            peripheral: BluetoothPeripheral,
            interval: Int,
            latency: Int,
            timeout: Int,
            status: GattStatus
        ) {
            super.onConnectionUpdated(peripheral, interval, latency, timeout, status)

//            peripheral.createBond()
        }

        override fun onBondingStarted(peripheral: BluetoothPeripheral) {
            super.onBondingStarted(peripheral)

            Timber.d("BondingStarted")
        }

        override fun onBondingSucceeded(peripheral: BluetoothPeripheral) {
            super.onBondingSucceeded(peripheral)

            Timber.d("onBondingSucceeded")
        }

        override fun onBondingFailed(peripheral: BluetoothPeripheral) {
            super.onBondingFailed(peripheral)

            Timber.d("onBondingFailed")
        }
    }

}
