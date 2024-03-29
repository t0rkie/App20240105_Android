// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    // Hilt追加
    id("com.google.dagger.hilt.android") version "2.44" apply false
    // Realm追加
//    id ("io.realm.kotlin") version "1.0.0" apply false
    id ("io.realm.kotlin") version "1.11.0" apply false
}