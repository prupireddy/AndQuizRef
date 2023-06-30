package edu.uchicago.gerber.capsquiz.model

import edu.uchicago.gerber.capsquiz.CapsQuizApplication
import edu.uchicago.gerber.capsquiz.R

object Constants {

    //because we define this as "object" this is now a singleton and all of its members are static
    const val PIPE = "|"
    const val COUNTRY_INDEX = 0
    const val CAPITAL_INDEX = 1
    const val REGION_INDEX = 2

    val COUNTRY_CAP_REGION_ARRAY = CapsQuizApplication.app.resources.getStringArray(R.array.countries_capitals)
}