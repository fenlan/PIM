# <p align="center">PIM</p>
<div align="center">

![](https://travis-ci.org/fenlan/PIM.svg?branch=master)
[![codebeat badge](https://codebeat.co/badges/9052f589-5937-43fe-a959-eb23469102e6)](https://codebeat.co/projects/github-com-fenlan-pim-master)
![size](https://github-size-badge.herokuapp.com/fenlan/PIM.svg)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</div>

## 简述
This assignment involves the creation of simple Personal Information Management system that can deal with 4 kinds of items: todo items, notes, appointments and contacts. Each of these kinds of items is described in more detail below. The assignment requires that you create a class for each item type, and that each class extends an abstract base class provided for you. In addition to creating the four classes, you need to create a manager class that supports some simple text-based commands for creating and managing items.

Each of your 4 item type classes will be derived from the following abstract class:
``` java
public abstract class PIMEntity {
    String Priority; // every kind of item has a priority

    // default constructor sets priority to "normal"
    PIMEntity() {
        Priority = "normal";
    }

    // priority can be established via this constructor.
    PIMEntity(String priority) {
        Priority =  priority;
    }

    // accessor method for getting the priority string
    public String getPriority() {
        return Priority;
    }
    // method that changes the priority string
    public void setPriority(String p) {
        Priority = p;
    }

    // Each PIMEntity needs to be able to set all state information
    // (fields) from a single text string.
    abstract public void fromString(String s);

    // This is actually already defined by the super class
    // Object, but redefined here as abstract to make sure
    // that derived classes actually implement it
    abstract public String toString();
}
```

## 截图
<div align=center>

<image src="https://github.com/fenlan/Mycode/blob/master/images/PIM.png"></image>

</div>

## 四个功能描述
- `PIMTodo` : Todo items must be PIMEntites defined in a class named PIMTodo. Each todo item must have a priority (a string), a date and a string that contains the actual text of the todo item.

- `PIMNote` : Note items must be PIMEntites defined in a class named PIMNote. Each note item must have a priority (a string), and a string that contains the actual text of the note.

- `PIMAppointment` : Appointment items must be PIMEntites defined in a class named PIMAppointment. Each appointment must have a priority (a string), a date and a description (a string).

- `PIMContact` : Contact items must be PIMEntites defined in a class named PIMContact. Each contact item must have a priority (a string), and strings for each of the following: first name, last name, email address.
> There is one additional requirement on the implementation of the 4 item classes listed above, the 2 classes that involve a date must share an interface that you define. You must formally create this interface and have both PIMAppointment and PIMTodo implement this interface.

## PIMManager
You must also create a class named PIMManager that includes a main and provides some way of creating and managing items (from the terminal). You must support the following commands (functionality):
- `List` : print a list of all PIM items
- `Create` : add a new item
- `Save` : save the entire list of items
- `Load` : read a list of items from redis

## 数据库
PIM Create 一个数据进程序List中，当进行Save操作时将List中数据存入Redis数据库，其中Redis数据库采用set的数据结构存取

## 运行说明
If you want to compile this program, you can run this command. This can include your local classpath and jedis-2.9.0.jar
```
javac -classpath lib/* src/*.java -d out/
```

If you want to run this program, please run this command. Please notise ":out", this means that you redefine classpath. Generally, if you don't redefine your classpath, it auto include out directory(default current directory), but you redefine it. So you must include it.
```
java -cp lib/*:out PIMManager
```