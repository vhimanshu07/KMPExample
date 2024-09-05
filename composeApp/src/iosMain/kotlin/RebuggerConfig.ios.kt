/**
 * Created by Himanshu Verma on 30/07/24.
 **/
internal actual fun defaultLogger(tag: String, message: String) {
    println("$tag : $message")
}