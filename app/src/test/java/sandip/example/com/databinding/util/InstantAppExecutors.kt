package sandip.example.com.databinding.util

import sandip.example.com.databinding.utils.helperUtils.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(
    instant,
    instant
) {
    companion object {
        private val instant = Executor { it.run() }
    }
}