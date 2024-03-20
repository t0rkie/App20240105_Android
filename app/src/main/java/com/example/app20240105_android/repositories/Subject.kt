package com.example.app20240105_android.repositories

import com.example.app20240105_android.models.Subject
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import javax.inject.Inject

class SubjectRepository @Inject constructor() {

    private fun getRealmInstance(): Realm {
        val config = RealmConfiguration
            .Builder(schema = setOf(Subject::class))
            .build()
        return Realm.open(config)
    }

    suspend fun createSubject(subject: Subject) {
        getRealmInstance().apply {
            write { copyToRealm(subject) }
        }.close()
    }

    fun getAllSubjects(): List<Subject> {
        getRealmInstance().apply {
            val subjects = query<Subject>().find().map {
                Subject().apply {
                    id = it.id
                    subjectName = it.subjectName
                }
            }
            close()
            return subjects
        }
    }

    suspend fun deleteSubject(subject: Subject) {
        getRealmInstance().apply {
            write {
                delete(query<Subject>("id == $0", subject.id).find().first())
            }
        }.close()
    }
}