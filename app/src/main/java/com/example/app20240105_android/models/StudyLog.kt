package com.example.app20240105_android.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.UUID

class StudyLog: RealmObject {
    @PrimaryKey
    @NotNull
    var id: String = UUID.randomUUID().toString()
    var studyTime: Int = 0
    var studyTimeStr: String = "" // 00:00:00表記
    var subject: Subject? = null // Subjectモデルへの参照
    var memo: String = ""
}