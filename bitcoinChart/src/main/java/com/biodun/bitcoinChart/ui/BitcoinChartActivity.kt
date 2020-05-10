package com.biodun.bitcoinChart.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.biodun.bitcoinChart.R
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.di.DaggerBitcoinChartComponent
import com.biodun.bitcoinChart.presentation.BitcoinChartViewModel
import com.biodun.bitcoinChart.presentation.BitcoinChartViewModelFactory
import com.biodun.blockchainmarket.BlockChainMarketApp
import com.biodun.core.stateManagement.ResultState
import com.biodun.core.utils.DurationUtil
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_bitcoin_chart.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BitcoinChartActivity : AppCompatActivity() {

    @Inject
    lateinit var bitcoinChartViewModelFactory: BitcoinChartViewModelFactory

    private val bitcoinChartViewModel: BitcoinChartViewModel by lazy {
        ViewModelProvider(this, bitcoinChartViewModelFactory)
            .get(BitcoinChartViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val coreComponent = BlockChainMarketApp.coreComponent(this)
        DaggerBitcoinChartComponent.factory().create(coreComponent).inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitcoin_chart)
        bitcoinChartViewModel.getBitcoinData(DurationUtil.default)
        configureView()
    }

    private fun configureView() {

        observeBitcoinChartLiveData()

    }

    private fun observeBitcoinChartLiveData() {
        bitcoinChartViewModel.bitcoinChartLiveData.observe(this,
            Observer { result ->
                when (result.state) {
                    is ResultState.LOADING -> showProgressDialog()
                    is ResultState.ERROR -> handleError()
                    is ResultState.SUCCESS -> result.data?.let { handleSuccess(it)  }
                }
            }
        )
    }

    private fun handleSuccess(data: BitcoinData) {
        val entryList = data.values.map { Entry(it.time.toFloat(), it.bitcoinValue.toFloat()) }
        displayBitcoinGraph(entryList, data.name, data.description)
    }

    private fun displayBitcoinGraph(entries: List<Entry>, name: String, descriptionText: String) {
        val lineDataSet = LineDataSet(entries, name).apply {
                setDrawCircles(false)
                mode = LineDataSet.Mode.CUBIC_BEZIER
                setDrawValues(false)
                setDrawFilled(true)
                setDrawHighlightIndicators(false)
                lineWidth = 0f
                //fillDrawable = ResourcesCompat.getDrawable(resources, R.drawable.graph_filled_area_bg, theme)
        }
        //mBinding.chartTitle.text = descriptionText

        val lineData = LineData(lineDataSet)
        bitcoinChartGraph.apply {
            data = lineData
            description.text = ""
            scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
            setDrawGridBackground(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            axisLeft.apply {
                isEnabled = true
                spaceTop = 40.0f
                spaceBottom = 40.0f
            }
            axisRight.isEnabled = false
            xAxis.apply {
                isEnabled = true
                position = XAxis.XAxisPosition.TOP_INSIDE
                textSize = 10f
                setDrawAxisLine(false)
                setDrawGridLines(true)
                textColor = Color.BLACK
                setCenterAxisLabels(true)
                valueFormatter = object: ValueFormatter() {
                    private val mFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)

                    override fun getFormattedValue(value: Float): String {
                        val millis = TimeUnit.DAYS.toMillis(value.toLong())
                        return mFormat.format(Date(millis))
                    }
                }
            }
            //setNoDataText(getString())
            setNoDataTextColor(Color.BLACK)
            animateX(2500)

        }
     bitcoinChartGraph.invalidate()
    }


    private fun handleError() {

    }

    private fun showProgressDialog() {

    }
}
