package com.example.tanjodh_hayer_stressmeter.ui.home

object StressScoreUtil {
    fun getStressScoreForPosition(position: Int): Int {
        //map for image at pos X has a stress score of Y
        val stressScoreMap = mapOf(
            0 to 6,
            1 to 8,
            2 to 14,
            3 to 16,
            4 to 5,
            5 to 7,
            6 to 13,
            7 to 15,
            8 to 2,
            9 to 4,
            10 to 10,
            11 to 12,
            12 to 1,
            13 to 3,
            14 to 9,
            15 to 11,
        )

        // Return the stress score based on the provided position
        return stressScoreMap[position] ?: 0 // Default to 0 if the position is not found
    }
}
