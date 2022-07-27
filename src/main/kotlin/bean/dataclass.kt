import navipose.models.IScreen

sealed class Screens: IScreen {
    object ApkScreen : Screens()
    object LogScreen : Screens()
    object SettingScreen : Screens()
}