# Лабораторная работа 6: Коллекции и дженерики

Условие: [`assignments/lab06-generics-collections.md`](../assignments/lab06-generics-collections.md).

Из корня репозитория запустите:

```bash
./mvnw -pl lab06 test
```

Windows PowerShell: `.\\mvnw.cmd -pl lab06 test`.

Основной код размещайте в `src/main/java/edu/course/lab06/`, тесты — в `src/test/java/edu/course/lab06/`. В начале каждого Java-файла укажите `package edu.course.lab06;`.

Модуль независим от остальных лабораторных. Если нужны модели из предыдущей работы, перенесите и при необходимости адаптируйте только нужные классы в этот модуль. Начальная успешная сборка не является выполнением задания: создайте реализацию и собственные тесты по условию.

## Реализация

- `Box<T>` хранит значение любого заданного ссылочного типа и предоставляет `get`/`set`.
- `Stack<T>` реализует LIFO с помощью `ArrayList<T>`, запрещает `null`; `pop` и `peek` пустого стека выбрасывают `NoSuchElementException`. `push` имеет амортизированную сложность O(1), `pop` и `peek` — O(1).
- `DataSample` хранит атрибуты наблюдения: `id`, `label`, `source`, `featureCount`, `status`. Модель адаптирована из ЛР 5 без зависимости от других модулей.
- `SampleAnalytics.countByStatus` использует `EnumMap<SampleStatus, Integer>`, поскольку ключи — элементы перечисления статусов.
- `SampleAnalytics.uniqueSources` использует `LinkedHashSet<String>`: дубликаты удаляются, а порядок первого появления сохраняется.
- `SampleAnalytics.sortedByFeatureCountAndId` создаёт новый `ArrayList<DataSample>` и сортирует через `Comparator.comparingInt(...).thenComparing(...)`. Исходный список не изменяется.
- В реализации аналитики используются обычные циклы, без Stream API, raw types и ручных приведений типов.

Параметр типа `T` позволяет компилятору запретить помещение значения неподходящего типа, например `Integer` в `Box<String>`.
