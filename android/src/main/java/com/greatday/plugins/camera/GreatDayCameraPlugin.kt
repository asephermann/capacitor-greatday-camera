package com.greatday.plugins.camera

import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.senjuid.camera.CameraPlugin
import com.senjuid.camera.CameraPluginListener
import com.senjuid.camera.CameraPluginOptions
import org.json.JSONException
import org.json.JSONObject


@CapacitorPlugin(name = "GreatDayCamera")
class GreatDayCameraPlugin : Plugin() {

    private var cameraPlugin: CameraPlugin? = null

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
        cameraPlugin!!.setCameraPluginListener(object : CameraPluginListener {
            override fun onSuccess(photoPath: String, native: Boolean) {
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
        })

        startActivityForResult(
            call,
            cameraPlugin?.getIntent(options),
            CameraPlugin.REQUEST
        )
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