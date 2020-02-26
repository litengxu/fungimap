package com.guizhou.map.others;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class spiders {

    public static void main(){
        try{
            Document document=Jsoup.connect("http://gsmis.bjfu.edu.cn/newyjsmis/PostGraduate/AddCourses.aspx?CourseTypeId=11&AutoId=208575,208576,208577,208578,208579,208561,208562,208834&Action=Add").header("cookie","ASP.NET_SessionId=zuln0z55pvyqjd45fpomgdfr").get();
            System.out.println(document.text());

        }catch (Exception e){

            System.out.println("出现错误");
        }

    }


}
