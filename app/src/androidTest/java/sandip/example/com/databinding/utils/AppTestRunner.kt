package sandip.example.com.databinding.utils

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner
import sandip.example.com.databinding.TestApp

class AppTestRunner: AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}