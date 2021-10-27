## Шаг 2. Первая версия. Bottom Nav Bar.

Рассмотрим код, в котором создается Scaffold (Material design layout), в котором мы объявляем
BottomBar - composable-функцию с отрисовкой Bottom Nav Bar; и AppNavGraph - composable-функцию с
отрисовкой экранов из графа навигации.

```
@Composable
fun AppContent() {
    ProvideWindowInsets {
        JetComposeNavMultimoduleTheme {
            val tabs = remember { BottomTabs.values() }
            val navController = rememberNavController()
            Scaffold(
                backgroundColor = backgroundWhite,
                bottomBar = { BottomBar(navController = navController, tabs) }
            ) { innerPaddingModifier ->
                AppNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            }
        }
    }
}
```

В целом, тут все так же как в примерах Google, для примера ничего нового добавлять не пришлось.
Рассмотрим все по отдельности. BottomTabs - это enum class с контентом для Bottom Nav Bar. Обращаем
внимание на параметр route - через него произойдет связь таба с composable-экраном:

```
enum class BottomTabs(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val route: String
) {
    HOME(R.string.home, R.drawable.ic_baseline_home, "home"),
    SETTINGS(R.string.settings, R.drawable.ic_baseline_settings, "settings")
}
```

Рассмотрим BottomBar. Наиболее интересная первая строка - благодаря ней происходит рекомпозиция при
изменении в back stack. Далее - отрисовка BottomNavigation только если текущий destination связан с
route, который описан в BottomTabs.

```
@Composable
fun BottomBar(navController: NavController, tabs: Array<BottomTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomTabs.HOME.route

    val routes = remember { BottomTabs.values().map { it.route } }
    if (currentRoute in routes) {
        BottomNavigation(
            Modifier.navigationBarsHeight(additional = 56.dp)
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    label = { Text(stringResource(tab.title).uppercase(Locale.getDefault())) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = LocalContentColor.current,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}
```

Теперь основной код, связанный с навигацией - объявление NavHost. Ему для инициализации нужен
navController, который передаем сверху, и startDestination. Здесь к route "home" и "settings"
соотносят composable-функции, которые будут вызываться при навигации.

```
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            Box(modifier = modifier) {
                Text("home")
            }
        }

        composable("settings") {
            Box(modifier = modifier) {
                Text("settings")
            }
        }
    }
}
```

На этом шаге максимально примитивно. Но что там с многомодульностью? Не будем же мы бесконечно
прописывать каждый новый экран в один файл, раздувая его до бесконечности. Нам поможет
унифицированный подход добавление фичи, который мы рассмотрим на следующем шаге.