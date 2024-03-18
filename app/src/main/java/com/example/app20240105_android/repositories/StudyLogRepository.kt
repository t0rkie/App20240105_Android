package com.example.app20240105_android.repositories

import com.example.app20240105_android.models.StudyLog
import com.example.app20240105_android.models.Subject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import javax.inject.Inject

class StudyLogRepository @Inject constructor() {
    // 参考: https://note.com/masato1230/n/n84f17dc95ce5
    private fun getRealmInstance(): Realm {
        val config = RealmConfiguration
            .Builder(schema = setOf(
                StudyLog::class,
                Subject::class
            ))
            .build()
        return Realm.open(config)
    }
    // Create
    suspend fun createStudyLog(studyLog: StudyLog) {
        getRealmInstance().apply {
            write { copyToRealm(studyLog) }
        }.close()
    }

    // Read
    fun getAllStudyLog(): List<StudyLog> {
        getRealmInstance().apply {
            val studyLogs = query<StudyLog>().find().map {
                StudyLog().apply {
                    id = it.id
                    studyTime = it.studyTime
                    studyTimeStr = it.studyTimeStr
                    memo = it.memo
                    subject = it.subject
                }
            }
            close()
            return studyLogs
        }
    }

    // Update
    suspend fun updateStudyLog(studyLog: StudyLog) {
        getRealmInstance().apply {
            write {
                query<StudyLog>("id == $0", studyLog.id).find().first().apply {
                    studyTime = studyLog.studyTime
                    studyTimeStr = studyLog.studyTimeStr
                    subject = studyLog.subject
                    memo = studyLog.memo
                }
            }
        }.close()
    }

    //Delete
    suspend fun deleteStudyLog(studyLog: StudyLog) {
        getRealmInstance().apply {
            write {
                delete(query<StudyLog>("id == $0", studyLog.id).find())
            }
        }.close()
    }
}