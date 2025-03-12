package com.vadimpikha.utils

import com.vadimpikha.presentation.utils.floorToDecimals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class NumberFormatTest {

    @Test
    fun testFlooringWithPrecision() {
        assertEquals(1.0, 1.51.floorToDecimals(0))
        assertEquals(1.1, 1.1345.floorToDecimals(1))
        assertEquals(1.2, 1.2999.floorToDecimals(1))
        assertEquals(1.3, 1.3.floorToDecimals(1))
        assertEquals(1.4, 1.4.floorToDecimals(5))
        assertFails { 1.0.floorToDecimals(-1) }
    }

}