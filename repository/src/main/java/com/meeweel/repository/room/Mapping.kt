package com.meeweel.repository.room

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meeweel.model.DataModel
import com.meeweel.model.Meanings
import java.lang.reflect.Type

val gson: Gson = Gson()
val type: Type = object : TypeToken<List<Meanings>>() {}.type

fun convertDataModelToEntity(dataModel: DataModel) : Entity {
    return Entity(dataModel.text ?: "", if (dataModel.meanings != null) gson.toJson(dataModel.meanings)  else "")
}

suspend fun convertEntityListToDataModelList(list: List<Entity>) : List<DataModel> {
    return list.map {
        DataModel(
            it.text,
            gson.fromJson<List<Meanings>>(it.meanings, type)
        )
    }
}

fun convertDataModelListToEntityList(list: List<DataModel>) : List<Entity> {
    return list.map {
        Entity(
            it.text ?: "",
            if (it.meanings != null) gson.toJson(it.meanings) else ""
        )
    }
}