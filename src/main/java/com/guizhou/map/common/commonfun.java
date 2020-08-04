package com.guizhou.map.common;
import java.io.*;
import java.util.List;
/**
 * @Program: fungimap
 * @ClassName: commonfun
 * @Author: 李腾旭
 * @Date: 2020-07-23 21:52
 * @Description: ${todo}
 * @Version: V1.0
 */
public class commonfun {
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

}
