package com.example.app20240105_android.models

import io.realm.kotlin.types.annotations.PrimaryKey
import org.jetbrains.annotations.NotNull

class Subject {
    @PrimaryKey
    @NotNull
    var id: Int = 0

}