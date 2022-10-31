import com.example.statussaver.R


sealed class MainScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val index: Int
) {
    object WABusiness :
        MainScreen(route = "WhatsApp Business", title = "WaBusiness", icon = R.drawable.all_whatsapp_icon, index = 0)

    object Whatsapp : MainScreen(route = "WhatsApp", title = "Whatsapp", icon = R.drawable.all_whatsapp_icon, index = 1)

    object SavedFile : MainScreen(route = "Saved Files", title = "SavedFiles", icon = R.drawable.saved_file, index = 2)
}