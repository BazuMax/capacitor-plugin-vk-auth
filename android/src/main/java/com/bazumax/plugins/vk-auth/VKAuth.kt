package com.bazumax.plugins.vk

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import com.getcapacitor.*
import com.getcapacitor.annotation.ActivityCallback
import com.getcapacitor.annotation.CapacitorPlugin
import com.vk.api.sdk.VK
import com.vk.api.sdk.VK.initialize
import com.vk.api.sdk.auth.*
import com.vk.api.sdk.ui.VKWebViewAuthActivity
import com.vk.api.sdk.utils.VKUtils

@CapacitorPlugin
class VKAuth : Plugin() {
    @PluginMethod
    fun initWithId(call: PluginCall) {
        val value = call.getString("id")

//        Log.w("VKPlugin","AppID: " + VK.getAppId(context))
        VK.initialize(context)

        val ret = JSObject()
        ret.put("id", value + "kek")
        call.success(ret)
    }
    @PluginMethod
    fun auth(call: PluginCall) {
        val value = call.getString("id")

        val scope = call.getArray("scope").toList<String>();
        val vkScope = VKScope.values().filter { l ->  l.toString().toLowerCase() in scope }


        val params = VKAuthParams(VK.getAppId(activity), scope = vkScope)

        val VK_APP_AUTH_ACTION = "com.vkontakte.android.action.SDK_AUTH"
        val VK_APP_PACKAGE_ID = "com.vkontakte.android"
        if (VKUtils.isIntentAvailable(activity, VK_APP_AUTH_ACTION, null, VK_APP_PACKAGE_ID)) {
            val intent = Intent(VK_APP_AUTH_ACTION, null).apply {
                setPackage(VK_APP_PACKAGE_ID)
                putExtras(params.toExtraBundle())
            }
            startActivityForResult(call, intent, "vkLoginResult")
            startActivityForResult(call, intent, "vkLoginResult")
        } else {
            val intent = Intent(activity, VKWebViewAuthActivity::class.java)
                    .putExtra(VKWebViewAuthActivity.VK_EXTRA_AUTH_PARAMS, params.toBundle())
            startActivityForResult(call, intent, "vkLoginResult")
        }
    }



    @ActivityCallback
    private fun vkLoginResult(call: PluginCall, result: ActivityResult) {
        val callback = object: VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                // User passed authorization
                val ret = JSObject()
                        .put("result", true)
                        .put("token", token.accessToken)
                        .put("created", token.created)
//                        .put("expiresIn", token.)
                        .put("userId", token.userId)
                        .put("email", token.email)
                notifyListeners("vkAuthFinished", ret)
            }

            override fun onLoginFailed(errorCode: Int) {
                // User didn't pass authorization
                val ret = JSObject()
                        .put("result", false)
                        .put("error", errorCode)
                notifyListeners("vkAuthFinished", ret)
            }
        }

        VK.onActivityResult(282, result.resultCode, result.data, callback);
    }
}
