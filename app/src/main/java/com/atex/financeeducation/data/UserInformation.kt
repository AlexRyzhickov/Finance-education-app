package com.atex.financeeducation.data

data class UserInformation(
    var email: String = "",
    var username: String = "",
    var funds: Int = 0,
    var untouchable: Int = 0,
    var daily: Int = 0
)

fun UserInformation.getFundsPercentages(): String {
    return String.format("%.2f", (funds / getTotalAmount() * 100).toDouble())
}

fun UserInformation.getUntouchablePercentages(): String {
    return String.format("%.2f", (untouchable / getTotalAmount() * 100).toDouble())
}

fun UserInformation.getDailyPercentages(): String {
    return String.format("%.2f", (daily / getTotalAmount() * 100).toDouble())
}

private fun UserInformation.getTotalAmount(): Int {
    return funds + untouchable + daily
}