package com.project.demiwatch.core.utils.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.project.demiwatch.R
import com.project.demiwatch.core.data.repository.PatientRepository
import com.project.demiwatch.core.data.repository.UserRepository
import com.project.demiwatch.core.utils.Resource
import com.project.demiwatch.core.utils.data_mapper.JsonMapper
import com.project.demiwatch.features.patient_detail.PatientDetailActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val workerParams: WorkerParameters,

    private val patientRepository: PatientRepository,
    private val userUserRepository: UserRepository,

    ) : CoroutineWorker(appContext, workerParams) {

    private val channelName = inputData.getString(NOTIFICATION_CHANNEL_ID)

    private fun getPendingIntent(): PendingIntent? {
        val intent = Intent(applicationContext, PatientDetailActivity::class.java)
        return TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            } else {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }
    }

    override suspend fun doWork(): Result {
        val channelId = "Notifications Channel"
        val pendingIntent = getPendingIntent()

        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_notifications_24).setContentTitle("Patie")
            .setContentText("Pasien sedang dalam keadaan darurat")
            .setContentIntent(pendingIntent).setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            channel.description = "Notification Channel"
            notification.setChannelId(channelId)
            notificationManager.createNotificationChannel(channel)
        }

        val token = userUserRepository.getTokenUser().firstOrNull() ?: return Result.failure()
        val patientProfile =
            patientRepository.getCachePatientProfile().firstOrNull() ?: return Result.failure()

        if (patientProfile.isNotEmpty()) {
            val profile = JsonMapper.convertToPatientProfile(patientProfile)

            runBlocking {
                patientRepository.getLocationPatient(token, profile.watchCode).collect { patient ->
                    when (patient) {
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                        is Resource.Message -> {

                        }
                        is Resource.Success -> {
                            if (patient.data != null) {
                                if (patient.data.emergency == true) {
                                    notificationManager.notify(101, notification.build())
                                }

                            }
                        }
                    }
                }
            }
        }

        return Result.success()
    }

}