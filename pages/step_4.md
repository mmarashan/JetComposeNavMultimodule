## Шаг 4. Навигация внутри фичи

Теперь рассмотрим пример навигации из фичи в фичу. Для примера рассмотрим фичу onboarding, которая
позволяет перейти в фичи home или settings.

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
            navController.popBackStack()
            navController.navigate(DependencyProvider.homeFeature().homeRoute())
        }

        SimpleButton(text = "To settings") {
            navController.popBackStack()
            navController.navigate(DependencyProvider.settingsFeature().settingsRoute())
        }
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

Здесь OnboardingScreen - это экран фичи, который открывается по route = "onboarding". На экране
текст и 2 кнопки с похожими обработчиками нажатий. В них с помощью navController текущий экран
удаляется из back stack и через псевдо-DI DependencyProvider достается Api нужной фичи, из которой
достаем route до ее экрана.