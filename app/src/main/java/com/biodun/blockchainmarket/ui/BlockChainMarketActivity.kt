package com.biodun.blockchainmarket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.biodun.blockchainmarket.R
import com.biodun.core.utils.Activities
import com.biodun.core.utils.intentTo

class BlockChainMarketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_chain_market)
        startActivity(intentTo(Activities.BitcoinChartActivity))
        finish()
    }
}
