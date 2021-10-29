Мы начали разрабатывать приложение Финуслуги осенью 2021 и воспользовались возможностью делать UI сразу на Jetpack Compose. Как и всегда, сразу встал вопрос выбора архитектуры многомодульности и механизма навигации. Решение должно быть, с одной стороны, достаточно лаконичным и понятным для новых разработчиков. С другой стороны, оно должно быть масштабируемым, чтобы с ростом числа и размера модулей время сборки не возрастало до раздражающего.
<cut/>

После изучения документации, [примеров с Сompose](https://github.com/android/compose-samples) от Google и поиска решений в сети было принято решение использовать [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation). Во-первых, оно развивается Google. Во-вторых, это достаточно гибкий инструмент, удовлетворяющий современным потребностям - работа с backstack (в том числе multi-backstack), простая интеграция с Bottom Nav Bar, анимации перехода и.т.д. В-третьих, на наш взгляд, интуитивно понятная и имеет много общего с Jetpack Navigation for Fragments.

Здесь мы на упрощенном примере хотим поделиться тем, как мы начали делать многомодульное приложение с Jetpack Compose Navigation.

Рассмотрим основные сущности этой библиотеки. Здесь будет пересказ документации, так что тем, кто уже имеет опыт с этим инструментом, можно переходить дальше.

**Destination** - экран (в нашем случае composable-функция), на который производится навигация.

**Route** - строка - ссылка до destination. В отличии с Jetpack Navigation for Fragments навигация происходит только через ссылки. Передаваемые аргументы прописываются в этой же строке по аналогии с web. Пример рассмотрим далее.

**NavController** - класс, основная сущность, через которую происходит навигация. В "корневой" composable создается один инстанс, который должен быть передан во все "дочерние" destinations.

**NavHost** - composable-функция, в которой производится связывание route c destination или route с вложенным графом (nested graph). Это замена описания графа в xml как в Jetpack Navigation for Fragments.

**NavArguments** - ???

**NavOptions** - ???

Этого достаточно для старта! Приступим к коду..

## Шаг 1. Создание проекта

Для работы с Compose нужно поставить Android Studio Arctic Fox. Создаем шаблонный проект "Empty Compose Activity".

Скорее всего IDE предложит вам обновить версии библиотек, это поначалу может привести к конфликтам версий при сборке. В [Github-репозитории](https://github.com/mmarashan/JetComposeNavMultimodule) примера вы можете посмотреть исходный код и версии библиотек, с которыми проходит сборка.

Двигаемся дальше..

## Шаг 2. Первая версия. Bottom Nav Bar.

Рассмотрим код, в котором создается Scaffold (Material design layout), в котором мы объявляем BottomBar - composable-функцию с отрисовкой Bottom Nav Bar и AppNavGraph - composable-функцию с отрисовкой экранов из графа навигации.

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

В целом, тут все так же как в примерах Google, для примера ничего нового добавлять не пришлось. Рассмотрим все по отдельности. BottomTabs - это enum class с контентом для Bottom Nav Bar. Обращаем внимание на параметр route - через него произойдет связь таба с composable-экраном:

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

Рассмотрим BottomBar. Наиболее интересна первая строка - благодаря ней происходит рекомпозиция при изменении в back stack. Далее - отрисовка BottomNavigation только если текущий destination связан с route, который описан в BottomTabs.

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
                    label = { Text(stringResource(tab.title)) },
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
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}
```

Теперь основной код, связанный с навигацией - объявление NavHost. Ему для инициализации нужен navController, созданный выше, и startDestination. Здесь происходит инициализация графа навигации - связывание route с экранами. К route "home" и "settings" объявляются composable-функции, которые будут вызываться при навигации.

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

Но что там с многомодульностью? Не будем же мы бесконечно прописывать каждый новый экран в один файл, раздувая его до бесконечности. Нам поможет унифицированный подход добавления фичи, который мы рассмотрим на следующем шаге.

## Шаг 3. Feature-API

Создадим core-модуль с названием feature-api. Интерфейс ниже - основной контракт API фичи. Все Api фич - тоже интерфейсы - должны наследоваться от него. Api фич дополняются методами, возвращающими routes до нужных экранов с учетом аргументов навигации. Функция registerGraph(...) регистрирует граф навигации фичи либо как отдельные экраны через navGraphBuilder.composable(...), либо как вложенный граф через navGraphBuilder.navigation(..). NavController нужен для навигации, вызываемой в фичах. Modifier содержит проставленные отступы от Bottom Nav Bar.

```
interface FeatureApi {

    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier = Modifier
    )
}
```

Каждая фича состоит из двух модулей: feature-name-api и feature-name-impl. api-модуль должен быть максимально легковесным, так как его могут импортировать другие фичи, чтобы навигироваться на экраны feature-name. impl-модуль содержит всю реализации фичи и про него знает только модуль app, который поставлят реализацию другим фичам через DI.

Для наглядности покажем на схеме иерархию модулей:

![Иерархия модулей](https://habrastorage.org/webt/je/h2/xh/jeh2xhqjsq2ycxsjkcxkmvyskim.png)

Почему мы разрешаем фичам знать про api друг друга? Зачем делим на 2 модуля?

Навигация в Jetpack Compose Navigation основана на ссылках. Каждая фича в своем Api отвечает на вопрос, по каким ссылкам открываются ее экраны. И могут быть ситуации, когда из фичи А производится навигация на экран фичи Б и наоборот. В случае "мономодульных" фич возникла бы ситуация циклических зависимостей.

Также мы сознательно решили отказаться от подхода "фичи изолированы, а вся навигация в app или каком-то core-navigation модуле", который встречали в постах на аналогичную тему. Мы делаем большое приложение с потенциально большим количеством модулей. Такой подход привел бы к тому, что был бы некий GOD-класс/объект/модуль, отвечающий за навигацию. Это могло негативно сказаться на времени сборки при измененииях в этом модуле, а также приводить к частым merge-конфликтам при росте числа разработчиков.

И наконец, мы не преследуем цель сохранить переносимость и изолированность фич. Наши фичи достаточно бизнес-специфичны чтобы однажды понадобиться где-то еще.

Рассмотрим пример реализации фичи home-api. Тут добавлен метод, возвращающий route до единственного экрана фичи:

```
interface HomeFeatureApi: FeatureApi {

    fun homeRoute(): String
}
```

Рассмотрим home-impl. В примере "регистрируется" только один экран, но с ростом модуля их потенциально станет много. При этом, добавление нового экрана проводит к изменениям только внутри одного изолированного модуля.

```
class HomeFeatureImpl : HomeFeatureApi {

    private val baseRoute = "home"

    override fun homeRoute() = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(baseRoute) {
            HomeScreen(modifier = modifier, navController = navController)
        }
    }
}
```

Регистрация фичи происходит в теле лямбды NavHost c помощью расширения NavGraphBuilder.register:

```
    fun NavGraphBuilder.register(
        featureApi: FeatureApi,
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        featureApi.registerGraph(
            navGraphBuilder = this,
            navController = navController,
            modifier = modifier
        )
    }

    ...

    NavHost(
        navController = navController,
        startDestination = DependencyProvider.homeFeature().homeRoute()
    ) {
        register(
            DependencyProvider.homeFeature(),
            navController = navController,
            modifier = modifier
        )
        ...  
    }
```

Тут можно заметить еще одну новую сущность - DependencyProvider - это object, примитивное подобие service-locator, который имитирует в нашем упрощенном примере целевой DI. Предполагается, что Api фичей будут доступны из DI графа.

Обратите внимание, данный подход не прелагает свою надстройку над библиотечным механизмом навигации, разработчикам не придется изучать внутренний "велосипед". Добавлен один интерфейс, который помогает разнести экраны фич по модулям и одно необязательное расширение для лаконичности.

## Шаг 4. Навигация внутри фичи

Теперь рассмотрим пример навигации из фичи в фичу. Для примера рассмотрим фичу onboarding, которая позволяет осуществить навигацию на экран фичи home.

```
class OnboardingFeatureImpl : OnboardingFeatureApi {

    private val route = "onboarding"

    override fun route() = route

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route) {
            OnboardingScreen(navController)
        }
    }
}
...

@Composable
internal fun OnboardingScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello world! You're in onboarding screen",
            modifier = Modifier.padding(36.dp)
        )
        SimpleButton(text = "To home") {
            val homeFeatureApi = DependencyProvider.homeFeature()
            navController.popBackStack()
            navController.navigate(homeFeatureApi.homeRoute())
        }
        ....
    }
}

@Composable
private fun SimpleButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = onClick
    ) {
        Text(text)
    }
}
```

Здесь OnboardingScreen - это экран фичи, который открывается по route = "onboarding". В обработчике нажатий кнопки с помощью navController текущий экран удаляется из back stack и через псевдо-DI DependencyProvider достается Api нужной фичи, которая сообщает route до ее экрана.

## Заключение

В этом посте не затронуто много интересных тем, которые полезно изучить перед работой с Compose Navigation: навигация с аргументами; nested graphs - вложенные графы с инкапсулированными экранами, не доступными из других графов; возможность связывать routes c external deep links. К счастью, в команде Compose сделали подробную и структурированную [документацию про навигацию](https://developer.android.com/jetpack/compose/navigation).

Здесь мы поделились нашим видением организации многомодульного проекта с Compose Navigation. Мы ожидаем, что на длинной дистанции такой "фреймворк" добавления фичи позитивно скажется на скорости вхождения в проект, скорости разработки и убережет от внезапных поломок кода.