package com.project.collabexpense.data.repository.mappers

import com.project.collabexpense.data.local.entities.MyDataEntity
import com.project.collabexpense.data.remote.dto.MyDataDto
import com.project.collabexpense.domain.model.MyData

fun MyDataDto.toEntity(): MyDataEntity {
    return MyDataEntity(
        id = this.id,
        name = this.name
    )
}

fun MyDataEntity.toDomain(): MyData {
    return MyData(
        id = this.id,
        name = this.name
    )
}