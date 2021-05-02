package com.atex.financeeducation.data

data class UserInformation(
    var email: String = "",
    var username: String = "",
    var funds: Int = 0,
    var untouchable: Int = 0,
    var daily: Int = 0
)

fun UserInformation.getFundsPercentages(): String {
    return String.format("%.2f", (funds.toDouble() / getTotalAmount().toDouble() * 100)) + " %"
}

fun UserInformation.getUntouchablePercentages(): String {
    return String.format("%.2f", (untouchable.toDouble() / getTotalAmount() * 100)) + " %"
}

fun UserInformation.getDailyPercentages(): String {
    return String.format("%.2f", (daily.toDouble() / getTotalAmount() * 100)) + " %"
}

private fun UserInformation.getTotalAmount(): Int {
    val amount = funds + untouchable + daily
    return if (amount!=0) amount else 1
}