package com.meeweel.translator.model.datasource.room

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.meeweel.translator.model.data.DataModel
import com.meeweel.translator.model.data.Meanings
import java.lang.reflect.Type

val gson: Gson = Gson()
val type: Type = object : TypeToken<List<Meanings>>() {}.type

fun convertDataModelToEntity(dataModel: DataModel) : Entity {
    return Entity(dataModel.text ?: "", if (dataModel.meanings != null) gson.toJson(dataModel.meanings)  else "")
}

fun convertEntityListToDataModelList(list: List<Entity>) : List<DataModel> {
    return list.map {
        DataModel(it.text,
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