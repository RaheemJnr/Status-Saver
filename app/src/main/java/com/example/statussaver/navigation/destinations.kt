import com.example.statussaver.R



sealed class MainScreen(
    val route: String?,
    val title: String?,
    val icon: Int?,
    val index: Int?
) {
    object Local :
        MainScreen(
            "Local", "Local", icon = R.drawable.bottom_bar_local_icon, index = 0
        )
    object Gap : MainScreen(null, null, null, null)
    object Online :
        MainScreen("Online", "Online", icon = R.drawable.bottom_bar_online_icon, index = 1)
}