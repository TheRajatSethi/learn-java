package rs


data class Rule(
    val id: Int,
    val description: String,
    val userAdjustable: Int,
    val appliedAutomatically: Int,
    val applicableForId: Int
) {}


