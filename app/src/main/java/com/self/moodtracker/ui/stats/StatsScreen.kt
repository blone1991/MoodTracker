package com.self.moodtracker.ui.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.common.utils.DataUtils
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.ui.barchart.models.SelectionHighlightData
import co.yml.charts.ui.barchart.models.drawBarGraph
import kotlin.random.Random

@Composable
fun StatsScreen (viewModel: StatsViewModel = hiltViewModel()) {
    val emotionsMap = viewModel.emotionsMap.collectAsState()
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Mood Stats", fontWeight = FontWeight.Bold)
        BarChartComposable(viewModel)
        Row {
            Text("Most Frequent: ", fontWeight = FontWeight.Bold)
            Text(emotionsMap.value.entries.maxByOrNull { it.value }?.let { it-> it.key }?: "", fontWeight = FontWeight.Bold)
            Text(emotionsMap.value.entries.maxByOrNull { it.value }?.let { it-> " ( ${it.value } times )" }?:"", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun BarChartComposable (viewModel: StatsViewModel) {
    val emotionsMap = viewModel.emotionsMap.collectAsState()
    val dataCount = 13

    if (emotionsMap.value.isNotEmpty()) {
        val maxRange = 30
        val barData : MutableList<BarData> = mutableListOf()
        emotionsMap.value.values.forEachIndexed() { index, value ->
            barData += BarData(
                point = Point(
                    x = value.toFloat(),
                    y = index.toFloat(),
                ),
                color = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)),
            )
        }

        val xAxisData = AxisData.Builder().build()
        val yAxisData = AxisData.Builder()
            .labelAndAxisLinePadding(0.dp)
            .axisOffset(20.dp)
            .setDataCategoryOptions(DataCategoryOptions(isDataCategoryInYAxis = true))
            .startDrawPadding(50.dp)
            .labelData { index -> emotionsMap.value.keys.toList()[index] }
            .build()

        val barChartData = BarChartData(
            chartData = barData,
            xAxisData = xAxisData,
            yAxisData = yAxisData,

            barStyle = BarStyle(
                isGradientEnabled = false,
                paddingBetweenBars = 10.dp,
                barWidth = 30.dp,
                selectionHighlightData = SelectionHighlightData(
                    popUpLabel = { x, _ -> " Value : $x " },
                    barChartType = BarChartType.HORIZONTAL
                ),
            ),
            showYAxis = true,
            showXAxis = true,
            horizontalExtraSpace = 20.dp,
            barChartType = BarChartType.HORIZONTAL
        )
        BarChart(
            modifier = Modifier.height(260.dp),
            barChartData = barChartData
        )
    }
}