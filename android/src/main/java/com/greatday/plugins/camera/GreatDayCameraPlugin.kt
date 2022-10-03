package com.greatday.plugins.camera

import android.app.Activity
import androidx.activity.result.ActivityResult
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.ActivityCallback
import com.getcapacitor.annotation.CapacitorPlugin
import com.senjuid.camera.CameraPlugin
import com.senjuid.camera.CameraPluginListener
import com.senjuid.camera.CameraPluginOptions
import org.jetbrains.annotations.NotNull
import org.json.JSONException
import org.json.JSONObject


@CapacitorPlugin(name = "GreatDayCamera", requestCodes = [CameraPlugin.REQUEST])
class GreatDayCameraPlugin : Plugin() {

    private var cameraPlugin: CameraPlugin? = null
    private var listener: CameraPluginListener? = null

    @PluginMethod
    fun getCamera(call: PluginCall) {
        val photoName = call.getString("photoName")
        val quality = parseQuality(call.getString("quality"))
        val maxSize = parseMaxSize(call.getString("maxSize"))

        val options: CameraPluginOptions = CameraPluginOptions.Builder()
            .setName(photoName!!)
            .setDisableFacingBack(true)
            .setMaxSize(maxSize)
            .setQuality(quality)
            .build()

        this.takePhoto(call, options)
    }

    @PluginMethod
    fun getCameraSwap(call: PluginCall) {
        val photoName = call.getString("photoName")
        val quality = parseQuality(call.getString("quality"))
        val maxSize = parseMaxSize(call.getString("maxSize"))

        val options: CameraPluginOptions = CameraPluginOptions.Builder()
            .setName(photoName!!)
            .setDisableFacingBack(false)
            .setMaxSize(maxSize)
            .setQuality(quality)
            .build()

        this.takePhoto(call, options)
    }

    private fun takePhoto(call: PluginCall, options: CameraPluginOptions) {
        cameraPlugin = CameraPlugin(activity)
        listener = object : CameraPluginListener {
            override fun onSuccess(@NotNull photoPath: String, native: Boolean) {
                val jsonLocation = JSONObject()
                try {
                    jsonLocation.put("path", photoPath)
                    jsonLocation.put("native", native)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                val ret = JSObject()
                ret.put("result", jsonLocation.toString())
                call.resolve(ret)
            }

            override fun onCancel() {
                val ret = JSObject()
                ret.put("result", "cancelled")
                call.resolve(ret)
            }
        }
        cameraPlugin?.setCameraPluginListener(listener)

        startActivityForResult(
            call,
            cameraPlugin?.getIntent(options),
            "requestCamera"
        )
    }

    @ActivityCallback
    private fun requestCamera(call: PluginCall?, result: ActivityResult) {
        if (call == null) {
            return
        }
        if (result.resultCode == Activity.RESULT_OK) {
            val performNativeCamera = result.data?.getBooleanExtra("native", false)
            listener?.let {
                val photoPath = result.data?.getStringExtra("photo")
                if(performNativeCamera!!) {
                    it.onSuccess("", true)
                } else {
                    if (photoPath != null) {
                        it.onSuccess(photoPath, false)
                    } else {
                        it.onCancel()
                    }
                }
            }
        } else {
            listener?.onCancel()
        }
    }

    private fun parseQuality(qualityStr: String?): Int {
        return if (qualityStr != null && qualityStr.isNotEmpty()) {
            qualityStr.toInt()
        } else 100
    }

    private fun parseMaxSize(maxSizeStr: String?): Int {
        return if (maxSizeStr != null && maxSizeStr.isNotEmpty()) {
            maxSizeStr.toInt()
        } else 1024
    }
} 