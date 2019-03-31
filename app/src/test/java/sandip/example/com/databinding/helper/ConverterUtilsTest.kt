package sandip.example.com.databinding.helper

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConverterUtilsTest {

    @Test
    fun amountFormatTest() {

        assertEquals(ConverterUtils().amountFormat(value = 2000.0, iteration = 0), "2 Thousand")
        assertEquals(ConverterUtils().amountFormat(value = 5000.0, iteration = 0), "5 Thousand")
        assertEquals(ConverterUtils().amountFormat(value = 10000.0, iteration = 0), "10 Thousand")
        assertEquals(ConverterUtils().amountFormat(value = 10800.0, iteration = 0), "10 Thousand")
        assertEquals(ConverterUtils().amountFormat(value = 100800.0, iteration = 0), "100 Thousand")
        assertEquals(ConverterUtils().amountFormat(value = 1000800.0, iteration = 0), "1 Million")
        assertEquals(ConverterUtils().amountFormat(value = 10000800.0, iteration = 0), "10 Million")
        assertEquals(ConverterUtils().amountFormat(value = 129000000.0, iteration = 0), "129 Million")
        assertEquals(ConverterUtils().amountFormat(value = 375396270.0, iteration = 0), "375 Million")
    }
}