package sandip.example.com.databinding.helper


class ConverterUtils {

    private val NAMES = arrayOf(
        "Thousand",
        "Million",
        "Billion",
        "Trillion"
    )


    fun amountFormat(value: Double, iteration: Int) : String {

        val d = value.toLong() / 100 / 10.0
        val isRound = d * 10 % 10 == 0.0//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return if (d < 1000)
        //this determines the class, i.e. 'k', 'm' etc
            (if (d > 99.9 || isRound || !isRound && d > 9.99)
            //this decides whether to trim the decimals
                d.toInt() * 10 / 10
            else
                d.toString() + "" // (int) d * 10 / 10 drops the decimal
                    ).toString() + " " + NAMES[iteration]
        else
            amountFormat(d, iteration + 1)
    }
}