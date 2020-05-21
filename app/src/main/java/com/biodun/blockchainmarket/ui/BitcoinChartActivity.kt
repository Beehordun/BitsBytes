package com.biodun.blockchainmarket.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.biodun.blockchainmarket.R
import com.biodun.blockchainmarket.data.model.BitcoinData
import com.biodun.blockchainmarket.di.inject
import com.biodun.blockchainmarket.presentation.BitcoinChartViewModel
import com.biodun.core.extentions.gone
import com.biodun.core.extentions.visible
import com.biodun.core.stateManagement.ResultState
import com.biodun.core.utils.DurationUtil
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_bitcoin_chart.*
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit
import java.util.Date
import java.util.Locale
import javax.inject.Inject

const val SPACE_MARGIN = 40.0f
const val TEXT_SIZE = 10f
const val WIDTH_MARGIN = 0f
const val ANIMATION_TIME_MILLIS = 2000

class BitcoinChartActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val bitcoinChartViewModel: BitcoinChartViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(BitcoinChartViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitcoin_chart)
        configureView()
    }

    private fun configureView() {
        bitcoinChartViewModel.getBitcoinData(DurationUtil.default)
        observeBitcoinChartLiveData()
        durationView.setOnDurationSelectedListener {
            bitcoinChartViewModel.getBitcoinData(it)
        }
    }

    private fun observeBitcoinChartLiveData() {
        bitcoinChartViewModel.bitcoinChartLiveData.observe(this,
            Observer { result ->
                when (result.state) {
                    is ResultState.LOADING -> showProgressDialog()
                    is ResultState.ERROR -> result.exception?.let { handleError(it) }
                    is ResultState.SUCCESS -> result.data?.let { handleSuccess(it) }
                }
            }
        )
    }

    private fun handleSuccess(data: BitcoinData) {
        chartDescription.text = data.description
        circularProgress.gone()
        val entryList = data.values.map { Entry(it.time.toFloat(), it.bitcoinValue.toFloat()) }
        displayBitcoinGraph(entryList, data.name)
    }

    private fun displayBitcoinGraph(entries: List<Entry>, name: String) {
        val lineDataSet = LineDataSet(entries, name).apply {
            setDrawCircles(false)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawValues(false)
            setDrawFilled(true)
            setDrawHighlightIndicators(false)
            lineWidth = WIDTH_MARGIN
        }
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
                spaceTop = SPACE_MARGIN
                spaceBottom = SPACE_MARGIN
            }
            axisRight.isEnabled = false
            xAxis.apply {
                isEnabled = true
                position = XAxis.XAxisPosition.TOP_INSIDE
                textSize = TEXT_SIZE
                setDrawAxisLine(false)
                setDrawGridLines(true)
                textColor = Color.BLACK
                setCenterAxisLabels(true)
                valueFormatter = object : ValueFormatter() {
                    private val mFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)

                    override fun getFormattedValue(value: Float): String {
                        val millis = TimeUnit.SECONDS.toMillis(value.toLong())
                        return mFormat.format(Date(millis))
                    }
                }
            }

            setNoDataTextColor(Color.BLACK)
            animateX(ANIMATION_TIME_MILLIS)
        }

        bitcoinChartGraph.invalidate()
    }

    private fun handleError(error: Exception) {
        circularProgress.gone()
        Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressDialog() {
        circularProgress.visible()
    }
}
