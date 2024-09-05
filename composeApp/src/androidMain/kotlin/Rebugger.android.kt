/**
 * Created by Himanshu Verma on 30/07/24.
 **/
internal actual fun findComposableName(): String? {
    return Thread.currentThread().stackTrace[3].methodName
}