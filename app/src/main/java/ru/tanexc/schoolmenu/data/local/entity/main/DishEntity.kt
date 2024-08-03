package ru.tanexc.schoolmenu.data.local.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.tanexc.schoolmenu.core.util.toImageBitmap
import ru.tanexc.schoolmenu.data.local.entity.DataEntity
import ru.tanexc.schoolmenu.domain.model.Dish
import ru.tanexc.schoolmenu.domain.model.Harm

@Entity
data class DishEntity(
    @PrimaryKey
    val dishId: Int,
    val title: String,
    val image: ByteArray,
    val calories: Float,
    val protein: Float,
    val fats: Float,
    val carbon: Float,
    val weight: Float,
    val harm: Harm,
    val cost: Float
) : DataEntity {
    override fun asDomain(): Dish = Dish(
        dishId,
        title,
        image.toImageBitmap(),
        calories,
        protein,
        fats,
        carbon,
        weight,
        harm,
        cost
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DishEntity

        if (dishId != other.dishId) return false
        if (title != other.title) return false
        if (!image.contentEquals(other.image)) return false
        if (calories != other.calories) return false
        if (protein != other.protein) return false
        if (fats != other.fats) return false
        if (carbon != other.carbon) return false
        if (weight != other.weight) return false
        if (harm != other.harm) return false
        if (cost != other.cost) return false

        return true
    }

    override fun hashCode(): Int {
        var result = dishId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + calories.hashCode()
        result = 31 * result + protein.hashCode()
        result = 31 * result + fats.hashCode()
        result = 31 * result + carbon.hashCode()
        result = 31 * result + weight.hashCode()
        result = 31 * result + harm.hashCode()
        result = 31 * result + cost.hashCode()
        return result
    }
}
