package yao.print.wpt630.demo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import yao.print.api.BasePrinter;
import yao.print.api.Connection;
import yao.print.api.PrinterDriver;
import yao.print.api.content.Doucument;
import yao.print.api.content.Printable;
import yao.print.api.content.convertable.Font;
import yao.print.api.content.printable.Barcode;
import yao.print.api.content.printable.BlankRow;
import yao.print.api.content.printable.CutPage;
import yao.print.api.content.printable.Image;
import yao.print.api.content.printable.KeyValue;
import yao.print.api.content.printable.QRcode;
import yao.print.api.content.printable.Section;
import yao.print.api.content.printable.Table;
import yao.print.api.content.printable.Section.TextAlign;
import yao.print.api.exception.PrinterException;
import yao.print.wpt630.demo.connection.BTconnection;

public class PrintDemo {

	public static final DateFormat date_time_format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

	public static void main(String[] args) throws InterruptedException, IOException {

		// 建立蓝牙连接（需要bluetooh设备）
		// 需要运行ListBluToothDevice，从列出的设备列表中取得蓝牙地址
		final StreamConnection c = (StreamConnection) Connector.open("btspp://98D331B0723D:1", Connector.READ_WRITE);
		final Connection bTconnection = new BTconnection(c);

		// 加载驱动
		BasePrinter p = null;
		try {
			p = PrinterDriver.newInstanse("yao.print.wpt630.WTPrinter", "");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 设置蓝牙连接
		p.setConnection(bTconnection);

		// 封装文档内容
		Doucument document = new Doucument();
		document.addContents(_title());
		document.addContents(_order());
		document.addContents(_baseInfo());
		document.addContents(_orderItem());
		document.addContents(_receipt());
		document.addContents(_description());
		document.addContent(_image("/images/Apple-logo.jpg"));
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

	static Printable _image(String path) throws IOException {
		URL url = PrintDemo.class.getResource(path);
		File file = new File(URLDecoder.decode(url.getFile(), "utf-8"));
		BufferedImage bi = ImageIO.read(file);
		int width = bi.getWidth();
		int height = bi.getHeight();
		Image image = new Image(width, height, new int[(width) * (height)]);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = bi.getRGB(x, y);
				image.getPixels()[y * width + x] = pixel;
			}
		}
		image.scaleWidth(220);
		return image;
	}

	static List<Printable> _title() {
		List<Printable> list = new ArrayList<>();
		list.add(new Section("购物单", new Font(Font.FontSize.Normal, true, false), TextAlign.Center));
		list.add(new BlankRow());
		return list;
	}

	static List<Printable> _description() {
		List<Printable> list = new ArrayList<>();
		list.add(new QRcode("http://minminguoye.com", 100));
		list.add(new Section("明明果业是一家专门做高端水果的连锁零售企业，在北京市拥有实体店面313家，扫码加入会员将享受永久9折礼遇！", Font.DEFAULT));
		return list;
	}

	static List<Printable> _receipt() {
		List<Printable> list = new ArrayList<>();
		list.add(new KeyValue("已收:", "￥1200.00", true));
		list.add(new KeyValue("应收:", "￥1400.00", true));
		list.add(new KeyValue("优惠:", "￥200.00", true));
		list.add(new KeyValue("积分:", "120", true));
		return list;
	}

	static List<Printable> _orderItem() {
		List<Printable> list = new ArrayList<>();
		String[] header = new String[] { "商品名称", "数量", "总价" };
		int[] cell_width = new int[] { 12, 10, 10 };
		Table table = new Table(header, cell_width, new boolean[] { false, true, true });
		table.addOneRow(new String[] { "老人香蕉", "35", "996.00" });
		table.addOneRow(new String[] { "靓妹木瓜", "10", "80.10" });
		table.addOneRow(new String[] { "热带火龙果", "20", "965.45" });
		table.addOneRow(Table.LINE_ROW);
		table.addOneRow(new String[] { "合计", "960", "188888" });
		list.add(table);
		return list;
	}

	static List<Printable> _baseInfo() {
		List<Printable> base_info = new ArrayList<>();
		base_info.add(new KeyValue("客　户:", "鹿含(13543267865)"));
		base_info.add(new KeyValue("商　家:", "明明果业"));
		base_info.add(new KeyValue("店　面:", "北京朝阳慧忠里小区店"));
		base_info.add(new KeyValue("地　址:", "北京朝阳慧忠里小区北门13栋04号"));
		base_info.add(new KeyValue("售货员:", "林允儿"));
		return base_info;
	}

	static List<Printable> _order() {
		List<Printable> list = new ArrayList<>();
		list.add(new Barcode("D00000000016"));
		list.add(new KeyValue("订单号:", "D00000000016"));
		list.add(new KeyValue("日　期:", date_time_format.format(new Date())));
		return list;
	}

}
