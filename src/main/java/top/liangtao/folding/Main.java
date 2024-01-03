package top.liangtao.folding;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

public class Main {
    // 一张纸打印多少页
    static final int NUMBER = 8;
    // 空白页页码
    static int BLANK_PAGE_NUMBER = 0;
    // 打印机页数
    static int PAGE_NUMBER = 0;
    // 双面打印
    static boolean DUPLEX_PRINT = false;

    public static void main(String[] args) {
        List<Integer> index = duplexPrintIndex(26);
        System.out.println("双面序列：" + StrUtil.join(",", index));
        if (DUPLEX_PRINT) return;
        List<Integer> z = new ArrayList<>();
        List<Integer> f = new ArrayList<>();
        for (int i = 0; i < index.size(); i = i + 4) {
            int r = (int) Math.floor((double) i / 4);
            ArrayList<Integer> d = ListUtil.toList(index.get(r * 4), index.get(r * 4 + 1), index.get(r * 4 + 2), index.get(r * 4 + 3));
            if (r % 2 == 0) {
                z.addAll(d);
            } else {
                f.addAll(d);
            }
        }
        System.out.println("正面序列：" + StrUtil.join(",", z));
        System.out.println("反面序列：" + StrUtil.join(",", f));
    }

    static List<Integer> duplexPrintIndex(int pageNumber) {
        PAGE_NUMBER = pageNumber;
        BLANK_PAGE_NUMBER = PAGE_NUMBER + 1;//空白页
        int len = (int) Math.ceil((float) pageNumber / NUMBER);//打印纸张数
        int rem = PAGE_NUMBER % NUMBER;//最后一张纸打印页数
        int num = len * NUMBER;//打印总页数
        List<Integer> index = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            int s = i * 4;
            ArrayList<Integer> d = ListUtil.toList(num - s, 1 + s, num - 2 - s, 3 + s, 2 + s, num - 1 - s, 4 + s, num - 3 - s);
            if (rem != 0) {
                switch (rem) {
                    case 1:
                    case 2:
                    case 3:
                        d = ListUtil.toList(j(num - 4 - s), 1 + s, num - 4 - 2 - s, 3 + s, 2 + s, num - 4 - 1 - s, 4 + s, num - 4 - 3 - s);
                        index.addAll(d);
                        break;
                    case 4:
                        d = ListUtil.toList(num - 4 - s, 1 + s, num - 4 - 2 - s, 3 + s, 2 + s, num - 4 - 1 - s, 4 + s, num - 4 - 3 - s);
                        if ((i + 1) == len) {
                            d.set(2, BLANK_PAGE_NUMBER);
                            d.set(3, BLANK_PAGE_NUMBER);
                            d.set(6, BLANK_PAGE_NUMBER);
                            d.set(7, BLANK_PAGE_NUMBER);
                        }
                        index.addAll(d);
                        break;
                    case 5:
                        if (i == 0) {
                            d.set(0, BLANK_PAGE_NUMBER);
                            d.set(0, BLANK_PAGE_NUMBER);
                            d.set(5, BLANK_PAGE_NUMBER);
                        }
                        index.addAll(d);
                        break;
                    case 6:
                        if (i == 0) {
                            d.set(0, BLANK_PAGE_NUMBER);
                            d.set(5, BLANK_PAGE_NUMBER);
                        }
                        index.addAll(d);
                        break;
                    case 7:
                        if (i == 0) {
                            d.set(0, BLANK_PAGE_NUMBER);
                        }
                        index.addAll(d);
                        break;
                }
            } else {
                index.addAll(d);
            }
        }
        return index;
    }

    static int j(int number) {
        return number > PAGE_NUMBER ? BLANK_PAGE_NUMBER : number;
    }
}
