package com.biodun.core.sharedView

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.biodun.core.R
import com.biodun.core.utils.DurationUtil
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_duration_selection.view.*

class DurationSelectionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), LayoutContainer {

    override val containerView: View?
        get() = this

    private var onDurationClickListener: ((duration: String) -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_duration_selection, this)
        configureView()
    }

    fun setOnDurationSelectedListener(listener: ((type: String) -> Unit)?) {
        this.onDurationClickListener = listener
    }

    private fun configureView() {
        sevenDaysTextView.setOnClickListener { handleDurationClicks(DurationUtil.default) }
        thirtyDaysTextView.setOnClickListener { handleDurationClicks(DurationUtil.thirtyDays) }
        sixtyDaysTextView.setOnClickListener { handleDurationClicks(DurationUtil.sixtyDays) }
        oneHundredAndEightyDaysTextView.setOnClickListener {
            handleDurationClicks(DurationUtil.oneHundredAndEightyDays)
        }
    }

    private fun handleDurationClicks(duration: String) {
        setStatesToNormal()
        setSelectedState(duration)
        onDurationClickListener?.invoke(duration)
    }

    private fun setStatesToNormal() {
        sevenDaysTextView.apply {
            background = resources.getDrawable(R.drawable.rectangle_rounded_start_normal)
            setTextColor(resources.getColor(R.color.black))
        }
        thirtyDaysTextView.apply {
            background = resources.getDrawable(R.drawable.rectangle_normal)
            setTextColor(resources.getColor(R.color.black))
        }
        sixtyDaysTextView.apply {
            background = resources.getDrawable(R.drawable.rectangle_normal)
            setTextColor(resources.getColor(R.color.black))
        }
        oneHundredAndEightyDaysTextView.apply {
            background = resources.getDrawable(R.drawable.rectangle_rounded_end_normal)
            setTextColor(resources.getColor(R.color.black))
        }
    }

    private fun setSelectedState(duration: String) {
        when (duration) {
            DurationUtil.default -> {
                sevenDaysTextView.apply {
                    background = resources.getDrawable(R.drawable.rectangle_rounded_start_selected)
                    setTextColor(resources.getColor(R.color.white))
                }
            }

            DurationUtil.thirtyDays -> {
                thirtyDaysTextView.apply {
                    background = resources.getDrawable(R.drawable.rectangle_selected)
                    setTextColor(resources.getColor(R.color.white))
                }
            }

            DurationUtil.sixtyDays -> {
                sixtyDaysTextView.apply {
                    background = resources.getDrawable(R.drawable.rectangle_selected)
                    setTextColor(resources.getColor(R.color.white))
                }
            }

            DurationUtil.oneHundredAndEightyDays -> {
                oneHundredAndEightyDaysTextView.apply {
                    background = resources.getDrawable(R.drawable.rectangle_rounded_end_selected)
                    setTextColor(resources.getColor(R.color.white))
                }
            }
        }
    }
}
