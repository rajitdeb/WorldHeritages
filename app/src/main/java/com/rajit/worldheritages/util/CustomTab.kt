package com.rajit.worldheritages.util

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object CustomTab {

    /**
     * Load @param [url] in the Custom Tav
     * [CustomTabsIntent.Builder] is used to build the custom tab along with the specified customizations
     **/
    fun loadURL(context: Context, url: String) {

        CustomTabsIntent.Builder().apply {

            // Show Title of the Website in Custom Tab ToolBar
            setShowTitle(true)

            // Build & Launch URL in Custom Tab
            build().launchUrl(context, Uri.parse(url))

        }

    }

}