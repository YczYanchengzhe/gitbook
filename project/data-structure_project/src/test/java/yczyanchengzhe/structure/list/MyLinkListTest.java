package yczyanchengzhe.structure.list;

import org.junit.jupiter.api.Test;

/**
 * @ClassName: MyLinkLIstTest
 * @Description: TODO
 * @Create by: A
 * @Date: 2020/12/21 0:13
 */
class MyLinkListTest {

    @Test
    void testDemo() {
        MyLinkList<String> mockedList = new MyLinkList<>();
        mockedList.add("test0");
        mockedList.add("test1");
        mockedList.add("test2");
        mockedList.add("test3");
        mockedList.add("test4");

        String s0 = mockedList.get(0);
        String s1 = mockedList.get(1);
        String s2 = mockedList.get(2);
        String s3 = mockedList.get(3);
        String s4 = mockedList.get(4);
        String s5 = mockedList.get(5);
        String searchInfo = "s0  %s ,s1 %s ,s2 %s ,s3 %s ,s4 %s ,s5 %s \n";
        System.out.printf(searchInfo, s0, s1, s2, s3, s4, s5);
        //删除头
        mockedList.delete(0);
        s0 = mockedList.get(0);
        s1 = mockedList.get(1);
        s2 = mockedList.get(2);
        s3 = mockedList.get(3);
        s4 = mockedList.get(4);
        s5 = mockedList.get(5);
        System.out.printf(searchInfo, s0, s1, s2, s3, s4, s5);
        //删除不存在
        mockedList.delete(5);
        s0 = mockedList.get(0);
        s1 = mockedList.get(1);
        s2 = mockedList.get(2);
        s3 = mockedList.get(3);
        s4 = mockedList.get(4);
        s5 = mockedList.get(5);
        System.out.printf(searchInfo, s0, s1, s2, s3, s4, s5);
        //删除尾
        mockedList.delete(3);
        s0 = mockedList.get(0);
        s1 = mockedList.get(1);
        s2 = mockedList.get(2);
        s3 = mockedList.get(3);
        s4 = mockedList.get(4);
        s5 = mockedList.get(5);
        System.out.printf(searchInfo, s0, s1, s2, s3, s4, s5);
        //删除不存在
        mockedList.deleteByNode("a");
        s0 = mockedList.get(0);
        s1 = mockedList.get(1);
        s2 = mockedList.get(2);
        s3 = mockedList.get(3);
        s4 = mockedList.get(4);
        s5 = mockedList.get(5);
        System.out.printf(searchInfo, s0, s1, s2, s3, s4, s5);
        //删除 3
        mockedList.deleteByNode("test3");
        s0 = mockedList.get(0);
        s1 = mockedList.get(1);
        s2 = mockedList.get(2);
        s3 = mockedList.get(3);
        s4 = mockedList.get(4);
        s5 = mockedList.get(5);
        System.out.printf(searchInfo, s0, s1, s2, s3, s4, s5);
    }

}