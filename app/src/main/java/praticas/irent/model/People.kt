package praticas.irent.model

import com.google.gson.annotations.SerializedName

class People(
    val name: String? = "",

    val heigh: String? = "",

    @SerializedName("hair_color")
    val hairColor: String? = "",

    val gender: String? = "",

    val films: List<String>? = emptyList()
)

class GetPeopleResult(
    val count: Int? = 0,
    val next: String? = "",
    val previous: String? = "",
    val results: List<People>
)