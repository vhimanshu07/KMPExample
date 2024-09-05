import android.util.Log

/**
 * Created by Himanshu Verma on 30/07/24.
 **/
internal actual fun defaultLogger(tag: String, message: String) {
    Log.d(tag, message)
}