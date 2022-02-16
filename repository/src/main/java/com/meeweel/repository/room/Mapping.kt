package com.meeweel.repository.room

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meeweel.model.DataModel
import com.meeweel.model.Meanings
import java.lang.reflect.Type

val gson: Gson = Gson()
val type: Type = object : TypeToken<List<com.meeweel.model.Meanings>>() {}.type

fun convertDataModelToEntity(dataModel: com.meeweel.model.DataModel) : Entity {
    return Entity(dataModel.text ?: "", if (dataModel.meanings != null) gson.toJson(dataModel.meanings)  else "")
}

fun convertEntityListToDataModelList(list: List<Entity>) : List<com.meeweel.model.DataModel> {
    return list.map {
        com.meeweel.model.DataModel(
            it.text,
            gson.fromJson<List<com.meeweel.model.Meanings>>(it.meanings, type)
        )
    }
}

fun convertDataModelListToEntityList(list: List<com.meeweel.model.DataModel>) : List<Entity> {
    return list.map {
        Entity(
            it.text ?: "",
            if (it.meanings != null) gson.toJson(it.meanings) else ""
        )
    }
}