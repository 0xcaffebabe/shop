package wang.ismy.common.utils;

import static org.junit.Assert.*;

public class CodecUtilsTest {

    @org.junit.Test
    public void md5Hex() {

        System.out.println(CodecUtils.md5Hex("123456", "21328738"));
    }
}