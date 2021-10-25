## Шаг 3. Feature-API

Создадим core-модуль feature-api. Интерфейс ниже - основной контракт API фичи. Все API фич должны
наследоваться от него. API фичей дополняются методами, возвращающими routes (
composable - экранов с учетом параметров навигации. Функция registerGraph(...) регистрирует граф
навигации фичи либо как отдельные экраны через navGraphBuilder.composable(...), либо как вложенный
граф через navGraphBuilder.navigation(..). NavGraphBuilder служит для связывания route c экранами
фичи. navController нужен для навигации; modifier - модификатор с проставленными paddings от Bottom
Nav Bar.

```
interface FeatureApi {

    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier = Modifier
    )
}
```

Каждая фича состоит из двух модулей: feature-name-api и feature-name-impl. api-модуль должен быть
максимально легковесным, так как про него могут знать другие фичи, чтобы навигироваться экраны
feature-name. impl-модуль содержит всю реализации фичи и про него знает только модуль app, который
поставлят реализацию другим фичам через DI.

Такой подход может вызвать ряд вопросов. Почему мы заставляем фичи знать про api друг друга? Зачем
делить на 2 модуля?

Во-первых мы сознательно решили отказаться от подхода "фичи изолированы, а вся навигация в app или
каком-то core модуле". Мы делаем большое приложение с потенциально большим количеством модулей.
Такой подход привел бы к тому, что был бы некий GOD-класс/объект/модуль, отвечающий за навигацию.
Это негативно сказалось бы на времени сборки при измененииях в этих местах, а также приводило бы к
merge-конфликтам при росте числа разработчиков.

Во-вторых, навигация в compose navigation на deeplink, каждая фича должна в своем api ответить на
вопрос, по какой ссылке открываются ее экраны. И могут быть ситуации, когда бы из фичи А можем
захотеть вызвать экран фичи Б и наоборот. В случае "
мономодульных" фич возникла бы ситуация циклических зависимостей.

В-третьих, едва ли это влияет ухуджение "переносимости" фич - фичи знают только про api только тех
фичей, на которые нужна навигация, а реализация изолирована.

Рассмотрим пример реализации фичи. home-api:

```
interface HomeFeatureApi: FeatureApi {

    fun homeRoute(): String
}
```

home-impl:

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

Регистрация фичи происходит в теле лямбды NavHost c помощью экстеншна NavGraphBuilder.register:

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

Тут можно заметить еще одну незнакомую сущность - DependencyProvider - это object, примитивный
service-locator, который иммитирует в нашем упрощенном примере целевой DI. Предполагается, что API
фичей будут доступны из DI графа.

Обратите внимание, данный подход не прелагает свою надстройку над сущестующим механизмом навигации,
разработчикам не придется изучать внутренний "велосипед"; это плюс один интерфейс, который помогает
разнести реализации фичей по модулям.