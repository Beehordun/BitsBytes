package com.biodun.core.utils

import android.content.Intent

private const val APP_PACKAGE_NAME = "com.example.currencyconverter"
private const val PACKAGE_NAME = "com.biodun"

fun intentTo(addressableActivity: AddressableActivity): Intent {
    return Intent(Intent.ACTION_VIEW).setClassName(
        APP_PACKAGE_NAME,
        addressableActivity.className
    )
}

interface AddressableActivity {
    val className: String
}

object Activities {
    object BitcoinChartActivity : AddressableActivity {
        override val className = "$PACKAGE_NAME.bitcoinChart.ui.BitcoinChartActivity"
    }
}
