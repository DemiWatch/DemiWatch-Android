package com.project.demiwatch.core.utils.constants

enum class PatientStatus(val status: String) {
    NOT_ACTIVE("At home"),
    ACTIVE("On the way to destination."),
    DANGER("Darurat"),
    ARRIVED("Arrived at destination."),

    //for history data
    SAFE("Aman"),
    TROUBLE("Terkendala"),
}