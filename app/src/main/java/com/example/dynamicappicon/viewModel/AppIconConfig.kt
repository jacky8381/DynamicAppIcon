package com.example.dynamicappicon.viewModel


enum class AppIconConfig(val value: String, val aliasName : String) {
    DEFAULT("Default", "com.example.dynamicappicon.MainActivity"),
    CAPTAIN("CaptainAmerica", "com.example.dynamicappicon.CaptainAmerica"),
    SUPERMAN("Superman", "com.example.dynamicappicon.Superman"),
    BATMAN("Batman", "com.example.dynamicappicon.Batman");

    companion object {
        private fun getConfigAppIconFromValue(value: String): AppIconConfig {
            return entries.find { it.value == value } ?: DEFAULT
        }

        fun getAliasFromValue(value: String): String {
            return when (getConfigAppIconFromValue(value)) {
                DEFAULT -> DEFAULT.aliasName
                CAPTAIN -> CAPTAIN.aliasName
                SUPERMAN -> SUPERMAN.aliasName
                BATMAN -> BATMAN.aliasName
            }
        }
    }
}