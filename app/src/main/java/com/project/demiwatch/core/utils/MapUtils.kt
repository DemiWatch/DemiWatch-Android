package com.project.demiwatch.core.utils

import com.google.gson.Gson
import com.mapbox.geojson.Point

object MapUtils {
    fun calculateMidPoint(point1: Point, point2: Point): Point {
        val midLatitude = (point1.latitude() + point2.latitude()) / 2
        val midLongitude = (point1.longitude() + point2.longitude()) / 2
        return Point.fromLngLat(midLongitude, midLatitude)
    }

    fun convertToPoint(jsonValue: String): Point = Gson().fromJson(jsonValue, Point::class.java)


}