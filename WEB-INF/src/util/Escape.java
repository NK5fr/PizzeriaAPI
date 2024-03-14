package util;

public class Escape {

    
    public static String escape(String s){
        if (s == null) return s;
        String res = s;
        res = res.replaceAll("<", "&lt;");
        res = res.replaceAll(">", "&gt;");
        res = res.replaceAll("\"", "&quot;");
        return res;
    }
}
