# Simple StartActivityForResult（Kotlin）
使用Callback or LiveData 简化StartActivityForResult调用

### 初始化

在Application OnCreate 方法中调用 `registerActivityLifecycleCallbacks(activityResultLifecycleCallbacks)`这行代码初始化

### 启动Activity
调用`startForResult(intent: Intent, onResult: ((ActivityResult<T>) -> Unit))`启动目标Activity，此方法启动Activity采用回调函数接受返回结果，
可多次重复接受，但要注意接受结果界面的生命周期问题

调用`startForResult(intent: Intent): LiveData<ActivityResult<T>>`启动目标Activity，此方法返回一个LiveData对象，可在接受数据界面订阅数据，
由于LiveData可感知界面生命周期，固此方法可能只接受最后一次发送的结果，如需多次接受，需要自定义LifecycleOwner


### 发送结果
目标Activity  中调用 `Activity.sendResult(result: ActivityResult<T>):Boolean`方法发送结果,返回值为发送是否成功
