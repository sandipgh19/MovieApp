package sandip.example.com.databinding.repo

import sandip.example.com.databinding.data.AppDao
import sandip.example.com.databinding.remote.WebServices
import sandip.example.com.databinding.utils.helperUtils.AppExecutors
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val webservice: WebServices,
    private val executor: AppExecutors,
    private val dao: AppDao) {
}