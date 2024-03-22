package com.example.app20240105_android.repositories

import android.util.Log
import com.example.app20240105_android.models.StudyLog
import com.example.app20240105_android.models.Subject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.isValid
import io.realm.kotlin.ext.query
import java.lang.IllegalStateException
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
    suspend fun createStudyLog(studyLog: StudyLog) {
        getRealmInstance().apply {
            write {
                val subjectId = studyLog.subject?.id ?: throw  IllegalArgumentException("Subject ID is null")
                val existingSubject = query<Subject>("id == $0", subjectId).first().find()
                existingSubject?.let {
                    if (it.isValid()) {
                        studyLog.subject = it
                        copyToRealm(studyLog)
                    } else {
                        throw IllegalStateException("The subject object is not valid.")
                    }
                } ?: throw IllegalStateException("Subject with ID $subjectId not found")
            }
        }.close()
    }

    // Read
    fun getAllStudyLog(): List<StudyLog> {
        getRealmInstance().apply {
            val studyLog = query<StudyLog>().find().map {
                copyFromRealm(it)
            }
            close()
            return studyLog
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