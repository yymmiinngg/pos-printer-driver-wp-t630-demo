package yao.print.wpt630.demo;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import yao.print.api.BasePrinter;
import yao.print.api.Connection;
import yao.print.api.PrinterDriver;
import yao.print.api.content.Doucument;
import yao.print.api.content.Printable;
import yao.print.api.content.printable.CutPage;
import yao.print.api.content.printable.Table;
import yao.print.api.exception.PrinterException;
import yao.print.wpt630.demo.connection.BTconnection;

public class PrintTableDemo {

	public static final DateFormat date_time_format = new SimpleDateFormat(
			"yyyy-MM-dd  HH:mm:ss");

	public static void main(String[] args) throws InterruptedException,
			IOException {

		// 建立蓝牙连接（需要bluetooh设备）
		// 需要运行ListBluToothDevice，从列出的设备列表中取得蓝牙地址
		final StreamConnection c = (StreamConnection) Connector.open(
				"btspp://98D331B0723D:1", Connector.READ_WRITE);
		final Connection bTconnection = new BTconnection(c);

		// 加载驱动
		BasePrinter p = null;
		try {
			p = PrinterDriver.newInstanse("yao.print.wpt630.WTPrinter", "");
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 设置蓝牙连接
		p.setConnection(bTconnection);

		// 封装文档内容
		Doucument document = new Doucument();
		document.addContents(_orderItem());
		document.addContent(new CutPage());

		// 打印文档内容
		try {
			p.print(document);
		} catch (PrinterException e) {
			e.printStackTrace();
		}

		// 关闭连接
		try {
			bTconnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static List<Printable> _orderItem() {
		List<Printable> list = new ArrayList<>();
		String[] header = new String[] { "商品名称", "数量", "总价" };
		int[] cell_width = new int[] { 12, 10, 10 };
		Table table = new Table(header, cell_width, new boolean[] { false,
				true, true });
		table.addOneRow(new String[] { "老人香蕉", "35", "996.00" });
		table.addOneRow(new String[] { "靓妹木瓜", "10", "80.10" });
		table.addOneRow(new String[] { "热带火龙果", "20", "965.45" });
		table.addOneRow(Table.LINE_ROW);
		table.addOneRow(new String[] { "合计", "960", "188888" });
		list.add(table);
		return list;
	}

}
