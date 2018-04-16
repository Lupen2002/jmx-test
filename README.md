# JMX TEST
Демострация возможности применения JMX, для управления потоками

Управленческие расширения Java (англ.  Java Management Extensions, JMX) — технология Java, предназначенная для контроля 
и управления приложениями, системными объектами, устройствами (например, принтерами) и компьютерными сетями. 
Данные ресурсы представляются MBean-объектами (англ. Managed Bean, управляемый Java Bean). 
\[[1](https://ru.wikipedia.org/wiki/Java_Management_Extensions)\]

## Getting Started

```bash
git clone https://github.com/Lupen2002/jmx-test.git
cd ./jmx-test
./gradlew start
```

Или собрать проект и запустить его:
```bash
git clone https://github.com/Lupen2002/jmx-test.git
cd ./jmx-test
./gradlew install
cd ./build/install/jmx-test
./bin/jmx-test
```

## Управление приложением

Для упралления приложением можно воспользоваться утилитами `jmc` или `jconsole` (входя в состав jdk).

## Архитектура

Приложение состоит из 2ух `MBean`.

`ThreadController` - асновной бин для управления приложением. Реализует 2 метода:
- `stop` - для остановки всех потоков, и самого приложения;
- `add` - для добавления нового потока.

`SimpleThread` - бин, который управляет потоком. Реализует единственный метод:
- `stop` - для остановки свогое потока.
