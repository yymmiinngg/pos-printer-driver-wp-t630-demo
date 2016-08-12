# pos-printer-driver-wp-t630-demo
wp-t630打印机驱动演示，需要准备以下条件：

* 需要支持gradle的编译环境
* 需要蓝牙连接设备，如usb蓝牙适配器
* 需要wp-t630蓝牙热敏打印机

#Step1

准备代码

    #创建目录
    mkdir pos-print
    cd pos-print
    #依赖项目
    git clone git@github.com:yymmiinngg/pos-printer-driver-api.git
    git clone git@github.com:yymmiinngg/pos-printer-driver-wp-t630.git
    #当前项目
    git clone git@github.com:yymmiinngg/pos-printer-driver-wp-t630-demo.git

>[pos-printer-driver-api](https://github.com/yymmiinngg/pos-printer-driver-api)是驱动规范接口

>[pos-printer-driver-wp-t630](https://github.com/yymmiinngg/pos-printer-driver-wp-t630)是驱动实现

#Step2

1. 装好热敏纸，启动打印机，使其处于待机状态
2. 运行ListBluToothDevice类，直到输出类似如下列表内容：


    BlueCove version 2.1.1-SNAPSHOT on winsock
    Address: 98D331B0723D
       Name: WP-T630

    Address: DC2C26D00FAB
       Name: General Bluetooth HID Barcode Scanner

    Inquiry Completed
    BlueCove stack shutdown completed

>其中98D331B0723D是WP-T630的蓝牙地址。

#Step3

更改PrintDemo类中的关键代码

    // 建立蓝牙连接（需要bluetooh设备）
		// 需要运行ListBluToothDevice，从列出的设备列表中取得蓝牙地址
    final StreamConnection c = (StreamConnection) Connector.open("btspp://98D331B0723D:1", Connector.READ_WRITE);
    final Connection bTconnection = new BTconnection(c);

>把98D331B0723D改成你的打印机的地址。

#Step4

运行PrintDemo类。
