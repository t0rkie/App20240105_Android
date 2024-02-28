package com.example.app20240105_android

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.Ignore
import io.realm.kotlin.types.annotations.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.UUID

class StudyLog: RealmObject {
    @Ignore
    @PrimaryKey
    @NotNull
    var id: UUID = UUID.randomUUID()
    var studyTime: Int = 0
    var studyTimeStr: String = "" // 00:00:00表記
    var subjectId: Int = 0 // 科目ID
    var memo: String = ""
}