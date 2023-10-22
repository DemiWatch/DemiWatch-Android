package com.project.demiwatch.core.utils.constants

enum class PatientStatus(val status: String){
    NOT_ACTIVE("Tidak Aktif"),
    ACTIVE("Perjalanan"),
    DANGER("Darurat"),

    //for history data
    SAFE("Aman"),
    TROUBLE("Terkendala"),
}