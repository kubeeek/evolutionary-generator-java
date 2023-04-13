# Evolutional Generator

This project is a submission for [the final project](https://github.com/apohllo/obiektowe-lab/blob/master/proj1/Readme.md) of Java OOP class.

![](/screens/screen2.png)
![](/screens/screen1.png)

The task was to create an evolutionary simulator game where animals roam a world of grasslands and jungles, looking for food and reproducing. The world is divided into square tiles, with most being grasslands and some being jungles where plants grow faster. Each animal has coordinates, energy, direction, and genes that determine its behavior. They move, eat, and reproduce, and can die if they run out of energy. The genetic code is used to determine how the animal behaves, and it cycles every day. The goal is to see how different species evolve over time.

# Prerequisites
Project was written using Gradle, Java 17, JUnit 5 and JavaFX 17.

# Installation and Usage
## Using IntelliJ

1. Clone the repository to your local machine using the following command:

```bash
git clone git@github.com:kubeeek/evolutionary-generator-java.git
```
2. Open the project in IntelliJ IDEA.

3. Run the Main class to start the application.

4. Run the unit tests by right-clicking on the test folder in the project explorer and selecting "Run All Tests".

## Using Gradle
1. Clone the repository to your local machine using the following command:

```bash
git clone git@github.com:kubeeek/evolutionary-generator-java.git
```
2. Run the project using:
* On Windows: ```./gradlew.bat run```
* On Linux: ```./gradlew.sh run```
