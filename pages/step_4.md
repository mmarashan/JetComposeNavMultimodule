## Шаг 1. Feature-API

Создаем модуль feature-api
```
interface FeatureApi {

    fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier = Modifier
    )
}
```
