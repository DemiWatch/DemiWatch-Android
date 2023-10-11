package com.project.demiwatch.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class RouteResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class LegsItem(

	@field:SerializedName("duration")
	val duration: Any? = null,

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("distance")
	val distance: Any? = null,

	@field:SerializedName("weight")
	val weight: Any? = null,

	@field:SerializedName("via_waypoints")
	val viaWaypoints: List<Any?>? = null,

	@field:SerializedName("steps")
	val steps: List<StepsItem?>? = null,

	@field:SerializedName("admins")
	val admins: List<AdminsItem?>? = null
)

data class MapboxStreetsV8(

	@field:SerializedName("class")
	val jsonMemberClass: String? = null
)

data class WaypointsItem(

	@field:SerializedName("distance")
	val distance: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: List<Any?>? = null
)

data class RoutesItem(

	@field:SerializedName("duration")
	val duration: Any? = null,

	@field:SerializedName("distance")
	val distance: Any? = null,

	@field:SerializedName("legs")
	val legs: List<LegsItem?>? = null,

	@field:SerializedName("weight_name")
	val weightName: String? = null,

	@field:SerializedName("weight")
	val weight: Any? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null
)

data class IntersectionsItem(

	@field:SerializedName("entry")
	val entry: List<Boolean?>? = null,

	@field:SerializedName("bearings")
	val bearings: List<Int?>? = null,

	@field:SerializedName("classes")
	val classes: List<String?>? = null,

	@field:SerializedName("is_urban")
	val isUrban: Boolean? = null,

	@field:SerializedName("admin_index")
	val adminIndex: Int? = null,

	@field:SerializedName("geometry_index")
	val geometryIndex: Int? = null,

	@field:SerializedName("location")
	val location: List<Any?>? = null,

	@field:SerializedName("mapbox_streets_v8")
	val mapboxStreetsV8: MapboxStreetsV8? = null,

	@field:SerializedName("out")
	val output: Int? = null,

	@field:SerializedName("in")
	val input: Int? = null,

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("turn_duration")
	val turnDuration: Int? = null,

	@field:SerializedName("turn_weight")
	val turnWeight: Int? = null
)

data class Maneuver(

	@field:SerializedName("instruction")
	val instruction: String? = null,

	@field:SerializedName("bearing_after")
	val bearingAfter: Int? = null,

	@field:SerializedName("bearing_before")
	val bearingBefore: Int? = null,

	@field:SerializedName("location")
	val location: List<Any?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("modifier")
	val modifier: String? = null
)

data class Data(

	@field:SerializedName("routes")
	val routes: List<RoutesItem?>? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("waypoints")
	val waypoints: List<WaypointsItem?>? = null,

	@field:SerializedName("uuid")
	val uuid: String? = null
)

data class Geometry(

	@field:SerializedName("coordinates")
	val coordinates: List<List<Any?>?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class AdminsItem(

	@field:SerializedName("iso_3166_1")
	val iso31661: String? = null,

	@field:SerializedName("iso_3166_1_alpha3")
	val iso31661Alpha3: String? = null
)

data class StepsItem(

	@field:SerializedName("duration")
	val duration: Any? = null,

	@field:SerializedName("mode")
	val mode: String? = null,

	@field:SerializedName("distance")
	val distance: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("weight")
	val weight: Any? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("driving_side")
	val drivingSide: String? = null,

	@field:SerializedName("intersections")
	val intersections: List<IntersectionsItem?>? = null,

	@field:SerializedName("maneuver")
	val maneuver: Maneuver? = null
)
