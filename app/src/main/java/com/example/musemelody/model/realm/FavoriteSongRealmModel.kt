package com.example.musemelody.model.realm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.*

class FavoriteSongRealmModel: RealmObject {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var songId: String = ""
}