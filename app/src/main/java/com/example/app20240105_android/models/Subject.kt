package com.example.app20240105_android.models

import io.realm.kotlin.types.annotations.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.UUID

class Subject {
    @PrimaryKey
    @NotNull
    var id: String = UUID.randomUUID().toString()
    var subjectName: String = ""

}