# KotlinGameEngine

采用Kotlin 结合 JavaFX 编写的游戏引擎


## 集成方式
1. 在build.gradle中引入仓库
    
     ```
    allprojects {
	        repositories {
		            ...
		            maven { url 'https://jitpack.io' }
	        }
    }
    ```
2. 在build.gradle中添加依赖
    
    ```
    dependencies {
        compile 'com.github.shaunxiao:kotlinGameEngine:v0.0.1'
    }
    ```

## 编码方式
1. 写一个类继承 Window 

    ```
    import javafx.scene.input.KeyEvent
    import org.itheima.kotlin.game.core.Window
    <br/>
    class MyWindow : Window() {
        override fun onCreate() {
            //窗体创建回调
        }
    
        override fun onDisplay() {
            //显示刷新回调
        }
    
        override fun onKeyPressed(event: KeyEvent) {
            // 按键响应回调
        }
    
        override fun onRefresh() {
            // 耗时操作回调
        }
    }
    ```

2. 启动代码
    
    ```
    import javafx.application.Application
    <br/>
    fun main(args: Array<String>) {
        Application.launch(MyWindow::class.java)
    }
    ```