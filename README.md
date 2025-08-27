# Fit_Diet_Planner

## Overview
The **Fit_Diet_Planner** is an Object-Oriented Programming (OOP) project designed to help users schedule their diet and exercise plans in an organized and efficient way.  
The system supports **United Nations Sustainable Development Goal (SDG) 3: Good Health and Well-Being** by promoting healthier lifestyles and encouraging consistent diet and fitness tracking.

## Features
- Create and manage **daily diet plans** with food and exercise entries.  
- **Planned vs. completed entries** to monitor progress.  
- A **default library** of food and exercise with required nutrition and MET data, with support for adding **custom library items**.  
- **Quick plan generation** with various duplication options.  
- An **overview schedule** that showcases the average intake, calories burned, and net calories of the month.  
- Intuitive **JavaFX interface** powered by FXML.  
- **Object-Oriented Design** with abstraction, inheritance, and polymorphism.  
- Detailed **Javadoc documentation** provided in the `object` package.  

## Project Structure
The source code (`src`) is divided into three main Java packages along with FXML files:

### 1. `application` package
- Contains the **main class** to execute the system.  
- Manages **page navigation** between FXML scenes.  
- Provides **AppContext** (shared memory space for data across pages).  
- Includes **utility classes** with common static functions.  

### 2. `controller` package
- Contains **controllers** linked to corresponding FXML files.  
- Handles user input and interaction logic.  
- Updates the **UI dynamically** based on the underlying model.  

### 3. `object` package
- Core **OOP design focus** of the project.  
- Stores all **object classes** (e.g., `DailyPlan`, `Entry`, `Food`, `Sport`).  
- Demonstrates **abstraction, inheritance, overriding, and encapsulation**.  
- Fully documented with **Javadoc comments** for clarity and maintainability.  

### FXML files
- Define the **UI layout** and structure.  
- Each FXML file is paired with a controller for event handling.  

## Technology Stack
- **Java** (OOP principles)  
- **JavaFX & FXML** (UI and scene management)  
- **Javadoc** (documentation in `object` package)  

## Prerequisites
- **Java Development Kit (JDK) 11 or higher** installed.  
- **JavaFX SDK** installed and configured in your IDE.  
- IDE that supports JavaFX (e.g., IntelliJ IDEA, Eclipse, NetBeans).  

## How to Run
1. Clone this repository.  
2. Open the project in your IDE.  
3. Make sure the JavaFX library is linked in your project settings.  
4. Run the `Main` class located in the `application` package.  

## How to Run
1. Clone this repository.  
2. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).  
3. Run the `Main` class located in the `application` package.  

## Contribution to SDG 3
By providing a structured system to track and manage food intake and exercise routines, this project directly encourages healthier daily habits, thereby supporting **SDG 3: Good Health and Well-Being**.

---

