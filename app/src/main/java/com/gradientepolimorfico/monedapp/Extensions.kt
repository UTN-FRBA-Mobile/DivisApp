package com.gradientepolimorfico.monedapp

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*

fun LineChart.configureForList(context: Context, entries: ArrayList<Entry>) {
    val mChart: LineChart = this
    val dataSet = LineDataSet(entries, "Label") // add entries to dataset

    if (entries[entries.lastIndex].y <= entries[entries.lastIndex - 1].y)
        dataSet.color = ContextCompat.getColor(context, R.color.graphColorLinePositive)
    else
        dataSet.color = ContextCompat.getColor(context, R.color.graphColorLineNegative)
    dataSet.setDrawValues(false)

    val drawable: Drawable
    if (entries[entries.lastIndex].y <= entries[entries.lastIndex - 1].y)
        drawable = ContextCompat.getDrawable(context, R.drawable.graph_gradient)!!
    else
        drawable = ContextCompat.getDrawable(context, R.drawable.graph_gradient_negative)!!
    dataSet.fillDrawable = drawable
    dataSet.setDrawFilled(true)
    dataSet.setDrawCircles(false)
    dataSet.notifyDataSetChanged()

    val lineData = LineData(dataSet)
    mChart.data = lineData
    mChart.setTouchEnabled(false)
    mChart.setViewPortOffsets(0f, 0f, 0f, 0f)
    mChart.description.isEnabled = false
    mChart.legend.isEnabled = false
    mChart.xAxis.isEnabled = false
    mChart.axisRight.isEnabled = false
    mChart.axisLeft.isEnabled = false
    mChart.animateX(3000, Easing.EasingOption.EaseOutQuart)
}

fun LineChart.configureForHistory(context: Context, entries: ArrayList<Entry>, range: Int) {
    val mChart: LineChart = this
    val dataSet = LineDataSet(entries, "Label") // add entries to dataset
    if (entries[entries.lastIndex].y <= entries[entries.lastIndex - 1].y)
        dataSet.color = ContextCompat.getColor(context, R.color.graphColorLinePositive)
    else
        dataSet.color = ContextCompat.getColor(context, R.color.graphColorLineNegative)
    dataSet.setDrawValues(false)
    val drawable: Drawable
    if (entries[entries.lastIndex].y <= entries[entries.lastIndex - 1].y)
        drawable = ContextCompat.getDrawable(context, R.drawable.graph_gradient)!!
    else
        drawable = ContextCompat.getDrawable(context, R.drawable.graph_gradient_negative)!!
    dataSet.fillDrawable = drawable
    dataSet.setDrawFilled(true)
    dataSet.setDrawCircles(false)
    dataSet.highLightColor = ContextCompat.getColor(context, R.color.colorTextSelected)

    val lineData = LineData(dataSet)
    mChart.data = lineData
    mChart.description.isEnabled = false
    mChart.legend.isEnabled = false
    mChart.xAxis.isEnabled = false
    mChart.axisRight.setDrawLabels(true)
    mChart.axisRight.textColor = Color.GRAY
    mChart.axisRight.gridColor = Color.GRAY
    mChart.axisRight.setDrawAxisLine(false)
    mChart.axisRight.axisMinimum = 0.toFloat()
    mChart.axisLeft.isEnabled = false
    mChart.setVisibleXRangeMaximum(range.toFloat())
    mChart.setVisibleXRangeMinimum(range.toFloat())
    mChart.moveViewToX(entries[entries.size - 1].x)
    mChart.isDragEnabled = false
    mChart.isDoubleTapToZoomEnabled = false
    mChart.isHighlightPerDragEnabled = true
    mChart.notifyDataSetChanged()
    //mChart.animateY(1000, Easing.EasingOption.EaseOutQuart)
}