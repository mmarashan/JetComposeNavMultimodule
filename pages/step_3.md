## Шаг 3. Feature-API

Создадим core-модуль с названием feature-api. Интерфейс ниже - основной контракт API фичи. Все Api
фич - тоже интерфейсы - должны наследоваться от него. Api фич дополняются методами, возвращающими
routes до нужных экранов с учетом аргументов навигации. Функция registerGraph(...) регистрирует граф
навигации фичи либо как отдельные экраны через navGraphBuilder.composable(...), либо как вложенный
граф через navGraphBuilder.navigation(..). NavController нужен для навигации, вызываемой в фичах.
Modifier содержит проставленные отступы от Bottom Nav Bar.

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
максимально легковесным, так как его могут импортировать другие фичи, чтобы навигироваться на экраны
feature-name. impl-модуль содержит всю реализации фичи и про него знает только модуль app, который
поставлят реализацию другим фичам через DI.

Такой подход может вызвать ряд вопросов. Почему мы заставляем фичи знать про api друг друга? Зачем
делить на 2 модуля?

Во-первых, мы сознательно решили отказаться от подхода "фичи изолированы, а вся навигация в app или
каком-то core модуле". Мы делаем большое приложение с потенциально большим количеством модулей.
Такой подход привел бы к тому, что был бы некий GOD-класс/объект/модуль, отвечающий за навигацию.
Это негативно сказалось бы на времени сборки при измененииях в этом коде, а также могло приводить к
частым merge-конфликтам при росте числа разработчиков.

Во-вторых, навигация в Jetpack Compose Navigation основана на ссылках. Каждая фича должна в своем
api ответить на вопрос, по каким ссылкам открываются ее экраны. И могут быть ситуации, когда из фичи
А производится навигация на экран фичи Б и наоборот. В случае "мономодульных" фич возникла бы
ситуация циклических зависимостей.

В-третьих, переносимость и полная изолированность фич не является нашим приоритетом. Наши фичи
достаточно бизнес-специфичны чтобы однажды понадобиться где-то еще.

Рассмотрим пример реализации фичи. home-api. Тут добавлен метод, возвращающий route до единственного
экрана фичи:

```
interface HomeFeatureApi: FeatureApi {

    fun homeRoute(): String
}
```

Рассмотрим home-impl. В примере "регистрируется" только один экран, но с ростом модуля их
потенциально станет много. При этом, добавление нового экрана проводит к изменениям только внутри
одного изолированного модуля (в случае вложенного графа навигации).

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

Тут можно заметить еще одну новую сущность - DependencyProvider - это object, примитивное подобие
service-locator, который имитирует в нашем упрощенном примере целевой DI. Предполагается, что Api
фичей будут доступны из DI графа.

Обратите внимание, данный подход не прелагает свою надстройку над библиотечным механизмом навигации,
разработчикам не придется изучать внутренний "велосипед". Добавлен один интерфейс, который помогает
разнести экраны фич по модулям и один необязательный extension для лаконичности.